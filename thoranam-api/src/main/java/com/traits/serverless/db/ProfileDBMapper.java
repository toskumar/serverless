package com.traits.serverless.db;

import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.SaveBehavior;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

public class ProfileDBMapper {

	private static final Logger LOG = LogManager.getLogger(ProfileDBMapper.class);

	private String PROFILE_TABLE_NAME = "Profile";

	private DynamoDBAdapter adapter;
	private DynamoDBMapper mapper;

	private static ProfileDBMapper profileMapper;

	public static ProfileDBMapper getInstance() {
		if (profileMapper == null) {
			synchronized (ProfileDBMapper.class) {
				if (profileMapper == null) {
					profileMapper = new ProfileDBMapper();
				}
			}
		}
		return profileMapper;
	}

	private ProfileDBMapper() {
		DynamoDBMapperConfig mapperConfig = DynamoDBMapperConfig.builder()
				.withTableNameOverride(new DynamoDBMapperConfig.TableNameOverride(PROFILE_TABLE_NAME)).build();

		this.adapter = DynamoDBAdapter.getInstance();
		this.mapper = this.adapter.createDbMapper(mapperConfig);
	}

	public Profile read(String cid, String uid) throws Exception {
		Profile profile = null;

		try {
			HashMap<String, AttributeValue> av = new HashMap<String, AttributeValue>();
			av.put(":v1", new AttributeValue().withS(cid));
			av.put(":v2", new AttributeValue().withS(uid));

			DynamoDBQueryExpression<Profile> queryExp = new DynamoDBQueryExpression<Profile>()
					.withKeyConditionExpression("cid = :v1 and uid = :v2").withExpressionAttributeValues(av);

			PaginatedQueryList<Profile> result = this.mapper.query(Profile.class, queryExp);
			if (result.size() > 0) {
				profile = result.get(0);
				LOG.info("profile - get(): profile - " + profile.toString());
			} else {
				LOG.info("profile - get(): profile - Not Found.");
			}
		} catch (Exception e) {
			LOG.error("profile get error" + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return profile;
	}

	// TODO - DynamoDB full database scan operation is expensive so remove this
	// method.
	public List<Profile> list() throws Exception {
		List<Profile> results = null;

		try {
			DynamoDBScanExpression scanExp = new DynamoDBScanExpression();
			results = this.mapper.scan(Profile.class, scanExp);
			for (Profile p : results) {
				LOG.info("Profile - list(): " + p.toString());
			}
		} catch (Exception e) {
			LOG.error("profile list error " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return results;
	}

	public void create(Profile profile) throws Exception {
		try {
			LOG.info("profile - save(): " + profile.toString());
			this.mapper.save(profile);
		} catch (Exception e) {
			LOG.error("profile save eror " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	public void update(Profile profile) throws Exception {
		try {
			LOG.info("profile - save(): " + profile.toString());
			this.mapper.save(profile, DynamoDBMapperConfig.builder().withSaveBehavior(SaveBehavior.UPDATE_SKIP_NULL_ATTRIBUTES).build());
		} catch (Exception e) {
			LOG.error("profile save eror " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	public Boolean delete(String cid, String uid) throws Exception {
		Profile profile = null;

		try {
			// get profile if exists
			profile = read(cid, uid);
			if (profile != null) {
				LOG.info("profile - delete(): " + profile.toString());
				this.mapper.delete(profile);
			} else {
				LOG.info("profile - delete(): profile - does not exist.");
				return false;
			}
		} catch (Exception e) {
			LOG.error("profile delete eror " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return true;
	}
}