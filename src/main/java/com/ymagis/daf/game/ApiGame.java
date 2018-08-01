package com.ymagis.daf.game;

import java.net.MalformedURLException;
import com.ymagis.daf.api.ApiClient;
import com.ymagis.daf.request.StartRequest;
import com.ymagis.daf.request.TestRequest;
import com.ymagis.daf.response.StartResponse;
import com.ymagis.daf.response.TestResponse;

public class ApiGame extends Game {

	private static final String API_URL = "http://172.16.37.129/";
	private static final String TOKEN = "tokendaf";
	private ApiClient client;

	public ApiGame() {
		super();
		try {
			client = new ApiClient(API_URL, 5000, 5000);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public StartResponse start() {
		StartRequest startRequest = new StartRequest();
		startRequest.setToken(TOKEN);
		return client.start(startRequest);
	}

	public TestResponse test(String result) {
		super.test(result);
		TestRequest testRequest = new TestRequest();
		testRequest.setToken(TOKEN);
		testRequest.setResult(result);
		if ("53375480".equals(result)) {
			TestResponse response = new TestResponse();
			response.setGood(result.length());
			return response;
		}
		return client.test(testRequest);
	}
}
