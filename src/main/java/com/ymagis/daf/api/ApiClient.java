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

	public <Res, Req> Res sendResponse(Req req, Class<Res> classNameRes, String path) {
		ClientResponse startClientResponse = null;
		try {
			String startInput = mapper.writeValueAsString(req);
			startClientResponse = getClientResponse(path, startInput);
			if (startClientResponse.getStatus() == 200) {
				String result = startClientResponse.getEntity(String.class);
				Res startResponse = (Res) mapper.readValue(result, classNameRes);
				return startResponse;
			} else {
				throw new Exception("Start failed, code error : " + startClientResponse.getStatus());
			}
		} catch (Exception e) {
			//Logger.getLogger(ApiClient.class.getName()).log(Level.SEVERE, e.getMessage(), e);
			return null;
		} finally {
			if (startClientResponse != null) {
				try {
					startClientResponse.close();
				} catch (Exception ignored) {
				}
			}
		}
	}

	public StartResponse start(StartRequest startRequest) {
		return sendResponse(startRequest, StartResponse.class, START);
	}

	public TestResponse test(TestRequest testRequest) {
		return sendResponse(testRequest, TestResponse.class, TEST);
	}
}
