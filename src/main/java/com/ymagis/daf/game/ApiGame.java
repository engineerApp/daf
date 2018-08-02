package com.ymagis.daf.game;

import com.ymagis.daf.api.ApiClient;
import com.ymagis.daf.request.StartRequest;
import com.ymagis.daf.request.TestRequest;
import com.ymagis.daf.response.StartResponse;
import com.ymagis.daf.response.TestResponse;

public class ApiGame extends Game {

	private static final String API_URL = "http://192.168.0.100:8000/";
	private static final String TOKEN = "tokendaf";
	private ApiClient client;

	public ApiGame() {
		super();
		client = new ApiClient(API_URL, 5000, 5000);
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
		return client.test(testRequest);
	}
}
