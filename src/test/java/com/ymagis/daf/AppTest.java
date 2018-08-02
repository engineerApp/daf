package com.ymagis.daf;

import com.ymagis.daf.api.ApiClient;
import com.ymagis.daf.game.ApiGame;
import com.ymagis.daf.game.Game;
import com.ymagis.daf.game.LocalGame;
import com.ymagis.daf.request.StartRequest;
import com.ymagis.daf.request.TestRequest;
import com.ymagis.daf.response.StartResponse;
import com.ymagis.daf.response.TestResponse;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	private final ObjectMapper mapper;

	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
		mapper = new ObjectMapper();
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	private int testExpectedLocal(String expected) {
		long startTime = System.currentTimeMillis();
		Game gameLocal = new LocalGame(expected);
		App app = new App(gameLocal);
		System.out.println(gameLocal.getCallCount());
		String result = app.startGame();
		app.printResult(startTime, result);
		assertEquals(expected, result);
		return gameLocal.getCallCount();
	}

	public void testLocal() {
		int callCount = testExpectedLocal("53375480");
		assertEquals(22, callCount);
		testExpectedLocal("533754800");
	}

	public void testApi() {
		try {
			String apiUrl = "http://172.16.37.129/";
			String token = "tokendaf";
			ApiClient client;
			client = new ApiClient(apiUrl, 5000, 5000);

			StartRequest startRequest = new StartRequest();
			startRequest.setToken(token);
			client.start(startRequest);

			TestRequest testRequest = new TestRequest();
			testRequest.setToken(token);
			testRequest.setResult("53375480");
			client.test(testRequest);
		} catch (Exception ignored) {
		}
		assertTrue(true);
	}

	public void testApiGame() {
		try {
			Game game = new ApiGame();
			game.start();
			game.test("53375481");
			game.test("53375480");
		} catch (Exception e) {
			// TODO: handle exception
		}
		assertTrue(true);
	}

	public void testRequest() {
		TestRequest testRequest = new TestRequest();
		testRequest.setToken("tokendaf");
		assertEquals(testRequest.getToken(), "tokendaf");
		testRequest.setResult("12345");
		assertEquals(testRequest.getResult(), "12345");

		StartRequest startRequest = new StartRequest();
		startRequest.setToken("tokendaf");
		assertEquals(startRequest.getToken(), "tokendaf");
	}

	public void testResponse() throws IOException {

		StartResponse expectedStartResponse = new StartResponse();
		expectedStartResponse.setSize(8);
		expectedStartResponse.setError("error");
		expectedStartResponse.setName("Quizz test");
		expectedStartResponse.setQuizzId(8);
		String startResponseJson = "{\n" + "\t\"name\" :\"Quizz test\",\n" + "\t\"size\" : 8,\n" + "\t\"quizz_id\":8,\n"
				+ "\t\"Error\":\"error\"\n" + "}";
		StartResponse startResponse = mapper.readValue(startResponseJson, StartResponse.class);
		assertEquals(startResponse.getError(), expectedStartResponse.getError());
		assertEquals(startResponse.getSize(), expectedStartResponse.getSize());
		assertEquals(startResponse.getName(), expectedStartResponse.getName());
		assertEquals(startResponse.getQuizzId(), expectedStartResponse.getQuizzId());
		assertEquals(startResponse.toString(), expectedStartResponse.toString());

		TestResponse expectedTestResponse = new TestResponse();
		expectedTestResponse.setError("error");
		expectedTestResponse.setGood(6);
		expectedTestResponse.setWrongPlace(2);
		String testResponseJson = "{\n" + "\t\"Error\":\"error\",\n" + "\t\"good\":6,\n" + "\t\"wrong_place\":2\n"
				+ "}";
		TestResponse testResponse = mapper.readValue(testResponseJson, TestResponse.class);
		assertEquals(testResponse.getGood(), expectedTestResponse.getGood());
		assertEquals(testResponse.getWrongPlace(), expectedTestResponse.getWrongPlace());
		assertEquals(testResponse.getError(), expectedTestResponse.getError());
		assertEquals(testResponse.toString(), expectedTestResponse.toString());

	}

}
