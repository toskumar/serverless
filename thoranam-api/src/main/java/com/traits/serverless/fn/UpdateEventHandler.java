package com.traits.serverless.fn;

import static com.traits.serverless.App.HTTP.PATH_PARAM1;
import static com.traits.serverless.App.HTTP.PATH_PARAMETERS;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.traits.serverless.ApiGatewayResponse;
import com.traits.serverless.Response;
import com.traits.serverless.db.Profile;
import com.traits.serverless.db.ProfileDBMapper;

public class UpdateEventHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	private static final Logger LOG = LogManager.getLogger(UpdateEventHandler.class);

	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
		// get the 'pathParameters' from input
		Map<?, ?> pathParameters = (Map<?, ?>) input.get(PATH_PARAMETERS);
		String path = (String) pathParameters.get(PATH_PARAM1);
		LOG.info(path + " - Path selected for routing to the next handler");

		switch (path) {
		case "profiles":
			return this.handleProfileRequest(input, context);
		default:
			return ApiGatewayResponse.builder().setStatusCode(404)
					.setObjectBody(new Response("Requested http path not found")).build();
		}
	}

	public ApiGatewayResponse handleProfileRequest(Map<String, Object> input, Context context) {
		LOG.info("Update profile handler ");

		Profile profile = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			profile = mapper.readValue((String) input.get("body"), Profile.class);
		} catch (Exception e) {
			LOG.error(e.getMessage(), input);
			Response error = new Response("Error processing profile information ");
			return ApiGatewayResponse.builder().setStatusCode(200).setObjectBody(error).build();
		}

		try {
			ProfileDBMapper profileDbMapper = ProfileDBMapper.getInstance();
			profileDbMapper.update(profile);
			LOG.info("Profile updated");
			return ApiGatewayResponse.builder().setStatusCode(201).setObjectBody(profile).build();
		} catch (Exception e) {
			LOG.error(e.getMessage(), input);
			Response error = new Response("Error updating profile information ");
			return ApiGatewayResponse.builder().setStatusCode(200).setObjectBody(error).build();
		}
	}
}
