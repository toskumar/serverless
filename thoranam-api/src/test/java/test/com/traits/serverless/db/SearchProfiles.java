package test.com.traits.serverless.db;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.traits.serverless.db.Profile;

public class SearchProfiles extends BaseTable {
	private static final Logger LOG = LogManager.getLogger(SearchProfiles.class);

	@Before
	public void setUp() {
		super.init();
		System.out.println("===========");
	}

	@After
	public void tearDown() {
		super.close();
	}

	@Test
	public void test01GetProfiles() {
		Profile p1 = this.dbMapper.load(Profile.class, "vaniyar", "anand@gmail.com");
		Profile p2 = this.dbMapper.load(Profile.class, "vaniyar", "babu@gmail.com");
		Profile p3 = this.dbMapper.load(Profile.class, "vaniyar", "chinna@gmail.com");

		LOG.info(p1);
		LOG.info(p2);
		LOG.info(p3);

		assertNotNull(p1);
		assertNotNull(p2);
		assertNotNull(p3);
	}

	@Test
	public void test02QueryProfile() {
		
		Table table = this.client.getTable(PROFILE_TABLE);
	     
	    Map<String, String> nameMap = new HashMap<String, String>();
	    nameMap.put("#cid", "cid");
	    nameMap.put("#uid", "uid");
	    nameMap.put("#createdBy", "createdBy");

	    Map<String, Object> valueMap = new HashMap<String, Object>();
	    valueMap.put(":cid", "vaniyar");
	    valueMap.put(":uid", "anand@gmail.com");
	    valueMap.put(":createdBy", "test-admin");
	        
		QuerySpec querySpec1 = new QuerySpec().withKeyConditionExpression("#cid = :cid and #uid = :uid")
				.withFilterExpression("#createdBy = :createdBy")
				.withNameMap(nameMap).withValueMap(valueMap);

		this.displayItems(table.query(querySpec1));

	}
	
	@Test
	public void test03ScanProfile() {
		
		Table table = this.client.getTable(PROFILE_TABLE);
		
	    Map<String, String> nameMap = new HashMap<String, String>();
	    nameMap.put("#cid", "cid"); 

	    Map<String, Object> valueMap = new HashMap<String, Object>();
	    valueMap.put(":minAge", 18);
	    valueMap.put(":maxAge", 25);
		
        
        ScanSpec scanSpec = new ScanSpec().withProjectionExpression("#cid, uid, createdOn, createdBy, person, preference")
                .withFilterExpression("person.age between :minAge and :maxAge")
                .withNameMap(nameMap)
                .withValueMap(valueMap);


	    this.displayItems(table.scan(scanSpec));
	}
	
	public void displayItems(ItemCollection<?> items) {
		Iterator<?> iterator = items.iterator();
        while (iterator.hasNext()) {
            Item item = (Item) iterator.next();
            System.out.println(item);
        }
	}
	
}
