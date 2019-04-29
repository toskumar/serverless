package com.traits.serverless.db;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;

public class DynamoDBAdapter {

	private AmazonDynamoDB client;

	private DynamoDBMapper mapper;

	private static DynamoDBAdapter adapter;

	private DynamoDBAdapter() {
		this.client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.AP_SOUTH_1).build();
	}

	public static DynamoDBAdapter getInstance() {
		if (adapter == null)
			adapter = new DynamoDBAdapter();

		return adapter;
	}

	public AmazonDynamoDBClient getClient() {
		return this.getClient();
	}

	public DynamoDBMapper createDbMapper(DynamoDBMapperConfig mapperConfig) {
		if (this.client != null)
			this.mapper = new DynamoDBMapper(this.client, mapperConfig);

		return this.mapper;
	}
}
