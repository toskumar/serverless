package com.traits.serverless;

public class App {

	public static class HTTP {
		public static final String QUERY_STRING_PARAMETERS = "queryStringParameters";
		public static final String PATH_PARAMETERS = "pathParameters";
		public static final String PATH_PARAM1 = "path";
	}

	public static class DB {
		public static final String PROFILE_KEY_CID = "cid";
		public static final String PROFILE_KEY_UID = "uid";
		
		public static final String KEY_VERSION = "version";
		public static final String UPDATED_BY = "api_user";
	}

}
