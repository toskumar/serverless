package com.traits.serverless.fn;

import static com.traits.serverless.App.DB.PROFILE_KEY_CID;
import static com.traits.serverless.App.DB.PROFILE_KEY_UID;
import static com.traits.serverless.App.HTTP.PATH_PARAM1;
import static com.traits.serverless.App.HTTP.PATH_PARAMETERS;
import static com.traits.serverless.App.HTTP.QUERY_STRING_PARAMETERS;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.traits.serverless.ApiGatewayResponse;
import com.traits.serverless.Response;
import com.traits.serverless.db.Profile;
import com.traits.serverless.db.ProfileDBMapper;

public class ReadEventHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	private static final Logger LOG = LogManager.getLogger(ReadEventHandler.class);

	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
		// get the 'pathParameters' from input
		Map<?, ?> pathParameters = (Map<?, ?>) input.get(PATH_PARAMETERS);
		String path = (String) pathParameters.get(PATH_PARAM1);
		LOG.info(path + " - Path selected for routing to the next handler");

		switch (path) {
		case "profiles":
			return this.handleReadProfileRequest(input, context);
		default:
			return ApiGatewayResponse.builder().setStatusCode(200)
					.setObjectBody(new Response("Requested http path not found")).build();
		}
	}

	public ApiGatewayResponse handleReadProfileRequest(Map<String, Object> input, Context context) {
		Map<?, ?> queryParameters = (Map<?, ?>) input.get(QUERY_STRING_PARAMETERS);

		if (queryParameters == null) {
			return this.handleListProfileRequest(input, context);
		}

		LOG.info("Read profiles handler invoked ");

		Profile profile = null;

		try {
			Map<?, ?> queryParams = (Map<?, ?>) input.get("queryStringParameters");

			String cid = (String) queryParams.get(PROFILE_KEY_CID);
			String uid = (String) queryParams.get(PROFILE_KEY_UID);

			LOG.info("Query param cid = " + cid);
			LOG.info("Query param uid = " + uid);
			
			profile = ProfileDBMapper.getInstance().read(cid, uid);

			LOG.info("Profile " + profile);

			if (profile != null) {
				return ApiGatewayResponse.builder().setStatusCode(200).setObjectBody(profile).build();
			} else {
				return ApiGatewayResponse.builder().setStatusCode(200)
						.setObjectBody(new Response("Profile id '" + cid + "-" + uid + "' does not exists")).build();
			}

		} catch (Exception e) {
			LOG.error(e.getMessage(), input);
			Response error = new Response(e.getMessage());
			return ApiGatewayResponse.builder().setStatusCode(200).setObjectBody(error).build();
		}
	}

	public ApiGatewayResponse handleListProfileRequest(Map<String, Object> input, Context context) {
		LOG.info("List profiles handler invoked ");

		List<Profile> profiles = null;
		try {
			ProfileDBMapper profileDbMapper = ProfileDBMapper.getInstance();
			profiles = profileDbMapper.list();

			if (profiles != null) {
				return ApiGatewayResponse.builder().setStatusCode(200).setObjectBody(profiles).build();
			} else {
				return ApiGatewayResponse.builder().setStatusCode(200).setObjectBody(new Response("No profiles exists"))
						.build();
			}

		} catch (Exception e) {
			LOG.error(e.getMessage(), input);
			Response error = new Response(e.getMessage());
			return ApiGatewayResponse.builder().setStatusCode(200).setObjectBody(error).build();
		}

	}
}
