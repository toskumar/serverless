package test.com.traits.serverless.db;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

public class BaseTable {

	protected AmazonDynamoDB dynamoDB;

	protected DynamoDB client;

	protected DynamoDBMapper dbMapper;
	
	protected static final String PROFILE_TABLE = "Profile";

	protected void init() {
		this.dynamoDB = AmazonDynamoDBClientBuilder.standard()
				.withEndpointConfiguration(
						new AwsClientBuilder.EndpointConfiguration("http://localhost:8001", Regions.AP_SOUTH_1.name()))
				.build();
		this.dbMapper = new DynamoDBMapper(dynamoDB);
		this.client = new DynamoDB(dynamoDB);

	}
	
	protected void close() {
		this.client.shutdown();
	}
}
