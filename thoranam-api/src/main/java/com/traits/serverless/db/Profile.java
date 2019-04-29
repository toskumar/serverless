package com.traits.serverless.db;

import java.util.Date;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Data
@DynamoDBTable(tableName = "Profile")
public class Profile {

	@DynamoDBHashKey(attributeName = "cid") //community id 
	private String cid;

	@DynamoDBRangeKey(attributeName = "uid") //user id
	private String uid;
	
	@DynamoDBAttribute(attributeName = "createdOn")
	private Date createdOn;

	@DynamoDBAttribute(attributeName = "createdBy")
	private String createdBy;

	@DynamoDBAttribute(attributeName = "person")
	private Person person;

	@DynamoDBAttribute(attributeName = "social")
	private Social social;

	@DynamoDBAttribute(attributeName = "education")
	private Education education;

	@DynamoDBAttribute(attributeName = "physical")
	private Physical physical;

	@DynamoDBAttribute(attributeName = "family")
	private Family family;

	@DynamoDBAttribute(attributeName = "address")
	private Address address;
	
	@DynamoDBAttribute(attributeName = "preference")
	private Preference preference;
	

	@Data
	@DynamoDBDocument
	public static class Person {
		@DynamoDBAttribute(attributeName = "shortName")
		private String shortName;

		@DynamoDBAttribute(attributeName = "fullName")
		private String fullName;
		
		@DynamoDBAttribute(attributeName = "dob")
		private Date dob;
		
		@DynamoDBAttribute(attributeName = "age")
		private short age;

		@DynamoDBAttribute(attributeName = "gender")
		private String gender;

		@DynamoDBAttribute(attributeName = "maritalStatus") // (U)nMarried, (W)idower, (D)ivisored
		private String maritalStatus;

		@DynamoDBAttribute(attributeName = "email")
		private String email;

		@DynamoDBAttribute(attributeName = "mobile")
		private String mobile;
		
	}

	@Data
	@DynamoDBDocument
	public static class Social {

		@DynamoDBAttribute(attributeName = "religion")
		private String religion;

		@DynamoDBAttribute(attributeName = "caste")
		private String caste;

		@DynamoDBAttribute(attributeName = "gothram")
		private String gothram;

		@DynamoDBAttribute(attributeName = "star")
		private String star;

		@DynamoDBAttribute(attributeName = "moonSign")
		private String moonSign;

		@DynamoDBAttribute(attributeName = "manglik")
		private Boolean manglik;

		@DynamoDBAttribute(attributeName = "placeOfBirth")
		private String placeOfBirth;

		@DynamoDBAttribute(attributeName = "timeOfBirth")
		private String timeOfBirth; // hh:mm:ss

	}

	@Data
	@DynamoDBDocument
	public static class Education {

		@DynamoDBAttribute(attributeName = "title")
		private String title; // MSC

		@DynamoDBAttribute(attributeName = "description")
		private String description; // Mathematics

		@DynamoDBAttribute(attributeName = "year")
		private String year; // Passed Out Year
	}

	@Data
	@DynamoDBDocument
	public static class Occupation {
	
		@DynamoDBAttribute(attributeName = "name")
		private String name;
		
		@DynamoDBAttribute(attributeName = "category")
		private String category; 
		
		@DynamoDBAttribute(attributeName = "income")
		private String income;
		
	}
	
	@Data
	@DynamoDBDocument
	public static class Physical {
		@DynamoDBAttribute(attributeName = "challenged")
		private Boolean challenged;

		@DynamoDBAttribute(attributeName = "height")
		private int height;

		@DynamoDBAttribute(attributeName = "weight")
		private int weight;

	}

	@Data
	@DynamoDBDocument
	public static class Family {

		@DynamoDBAttribute(attributeName = "father")
		private String father;

		@DynamoDBAttribute(attributeName = "fatherstatus")
		private Boolean fatherStatus;

		@DynamoDBAttribute(attributeName = "mother")
		private String mother;

		@DynamoDBAttribute(attributeName = "motherstatus")
		private String motherStatus;

		@DynamoDBAttribute(attributeName = "brothers")
		private int brothers;

		@DynamoDBAttribute(attributeName = "sisters")
		private int sisters;

	}

	@Data
	@DynamoDBDocument
	public static class Address {

		@DynamoDBAttribute(attributeName = "door")
		private String door;
		
		@DynamoDBAttribute(attributeName = "town")
		private String town;

		@DynamoDBAttribute(attributeName = "city")
		private String city;

		@DynamoDBAttribute(attributeName = "state")
		private String state;

		@DynamoDBAttribute(attributeName = "country")
		private String country;

		@DynamoDBAttribute(attributeName = "pincode")
		private String pincode;
	}

	@Data
	@DynamoDBDocument
	public static class Preference {

		@DynamoDBAttribute(attributeName = "minAge")
		private int minAge;

		@DynamoDBAttribute(attributeName = "maxAge")
		private int maxAge;

		@DynamoDBAttribute(attributeName = "minHeight")
		private int minHeight;

		@DynamoDBAttribute(attributeName = "maxHeight")
		private int maxHeight;

		@DynamoDBAttribute(attributeName = "education")
		private String education;

		@DynamoDBAttribute(attributeName = "matchHoroscope")
		private Boolean matchHoroscope;

		@DynamoDBAttribute(attributeName = "matchReligion")
		private Boolean matchReligion;

		@DynamoDBAttribute(attributeName = "matchCaste")
		private Boolean matchCaste;

	}

	@Override
	public String toString() {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return "{Profile : {cid:" + this.cid + ", uid:" + this.uid + ", createdOn: "
					+ this.createdOn + ", createdBy:" + this.createdBy + "}}";
		}
	}

}