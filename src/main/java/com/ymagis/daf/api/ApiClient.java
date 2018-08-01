package com.ymagis.daf.api;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.client.urlconnection.HttpURLConnectionFactory;
import com.sun.jersey.client.urlconnection.URLConnectionClientHandler;
import com.ymagis.daf.request.StartRequest;
import com.ymagis.daf.request.TestRequest;
import com.ymagis.daf.response.StartResponse;
import com.ymagis.daf.response.TestResponse;

import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApiClient {

	private final String baseUrl;
	private final ObjectMapper mapper;
	private final Client client;

	private static final String START = "api/start";
	private static final String TEST = "api/test";

	public ApiClient(String baseUrl, int connectionTimeout, int readTimeout) throws MalformedURLException {
		this.baseUrl = baseUrl;
		this.mapper = new ObjectMapper();
		client = getClient(connectionTimeout, readTimeout);
	}

	private Client getClient(final int connectionTimeout, final int readTimeout) {
		Client c;
		c = new Client(new URLConnectionClientHandler(new HttpURLConnectionFactory() {
			public HttpURLConnection getHttpURLConnection(URL url) throws IOException {
				return (HttpURLConnection) url.openConnection();
			}
		}));
		c.setConnectTimeout(connectionTimeout);
		c.setReadTimeout(readTimeout);
		return c;
	}

	public ClientResponse getClientResponse(String path, String input) {
		ClientResponse res = null;
		res = client.resource(baseUrl).path(path).type("application/json").accept(MediaType.APPLICATION_JSON_TYPE)
				.post(ClientResponse.class, input);
		return res;
	}

	public StartResponse start(StartRequest startRequest) {
		ClientResponse startClientResponse = null;
		try {
			String startInput = mapper.writeValueAsString(startRequest);
			startClientResponse = getClientResponse(START, startInput);
			if (startClientResponse.getStatus() == 200) {
				String result = startClientResponse.getEntity(String.class);
				StartResponse startResponse = mapper.readValue(result, StartResponse.class);
				return startResponse;
			} else {
				throw new Exception("Start failed, code error : " + startClientResponse.getStatus());
			}
		} catch (Exception e) {
			Logger.getLogger(ApiClient.class.getName()).log(Level.SEVERE, e.getMessage(), e);
			return new StartResponse();
		} finally {
			if (startClientResponse != null) {
				try {
					startClientResponse.close();
				} catch (Exception ignored) {
				}
			}
		}
	}

	public TestResponse test(TestRequest testRequest) {
		ClientResponse testClientResponse = null;
		try {
			String testInput = mapper.writeValueAsString(testRequest);
			testClientResponse = getClientResponse(TEST, testInput);
			if (testClientResponse.getStatus() == 200) {
				String responseString = testClientResponse.getEntity(String.class);
				TestResponse testResponse = mapper.readValue(responseString, TestResponse.class);
				return testResponse;
			} else {
				throw new Exception("Test failed, code error : " + testClientResponse.getStatus());
			}
		} catch (Exception e) {
			Logger.getLogger(ApiClient.class.getName()).log(Level.SEVERE, e.getMessage(), e);
			return new TestResponse();
		} finally {
			if (testClientResponse != null) {
				try {
					testClientResponse.close();
				} catch (Exception ignored) {
				}
			}
		}
	}
}
