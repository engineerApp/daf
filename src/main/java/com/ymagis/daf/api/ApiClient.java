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

/**
 * @author daf token post api/start api/test
 */
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

	public StartResponse start(StartRequest startRequest) {
		ClientResponse res = null;

		try {
			String input = mapper.writeValueAsString(startRequest);
			res = client.resource(baseUrl).path(START).type("application/json").accept(MediaType.APPLICATION_JSON_TYPE)
					.post(ClientResponse.class, input);

			// .queryParams(startRequest.getParameters())

			if (res.getStatus() == 200) {
				StartResponse response = mapper.readValue(res.getEntity(String.class), StartResponse.class);
				return response;
			} else {
				throw new Exception("Track receiver plugin failed, code error : " + res.getStatus());
			}
		} catch (Exception e) {
			Logger.getLogger(ApiClient.class.getName()).log(Level.SEVERE, e.getMessage(), e);
			return new StartResponse();
		} finally {
			if (res != null) {
				try {
					res.close();
				} catch (Exception ignored) {
				}
			}
		}
	}

	public TestResponse test(TestRequest testRequest) {
		ClientResponse res = null;
		try {
			String input = mapper.writeValueAsString(testRequest);
			res = client.resource(baseUrl).path(TEST).type("application/json").accept(MediaType.APPLICATION_JSON_TYPE)
					.post(ClientResponse.class, input);

			if (res.getStatus() == 200) {
				TestResponse response = mapper.readValue(res.getEntity(String.class), TestResponse.class);
				return response;
			} else {
				throw new Exception("Track receiver plugin failed, code error : " + res.getStatus());
			}
		} catch (Exception e) {
			Logger.getLogger(ApiClient.class.getName()).log(Level.SEVERE, e.getMessage(), e);
			return new TestResponse();
		} finally {
			if (res != null) {
				try {
					res.close();
				} catch (Exception ignored) {
				}
			}
		}
	}
}
