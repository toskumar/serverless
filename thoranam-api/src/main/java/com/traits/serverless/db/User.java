package com.traits.serverless.db;

import java.util.Date;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "User")
public class User {

	@DynamoDBHashKey(attributeName = "id") //user id
	private String id;

	@DynamoDBAttribute(attributeName = "secret") //password
	private String secret;

	@DynamoDBAttribute(attributeName = "sso") //password
	private String sso;

	@DynamoDBAttribute(attributeName = "lastLogin")
	private Date lastLogin;
	
}
