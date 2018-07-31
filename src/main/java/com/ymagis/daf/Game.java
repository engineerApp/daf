package com.ymagis.daf;

import java.net.MalformedURLException;

import com.ymagis.daf.api.ApiClient;
import com.ymagis.daf.request.StartRequest;
import com.ymagis.daf.request.TestRequest;
import com.ymagis.daf.response.StartResponse;
import com.ymagis.daf.response.TestResponse;

public class Game {

	private static final String API_URL = "http://172.16.37.129/";
	private static final String TOKEN = "tokendaf";
	private ApiClient client;

	public Game() {
		try {
			client = new ApiClient(API_URL, 30000, 30000);
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
		TestRequest testRequest = new TestRequest();
		testRequest.setToken(TOKEN);
		testRequest.setResult(result);
		return client.test(testRequest);
	}
}
