package com.ymagis.daf;

import java.net.ConnectException;
import java.net.MalformedURLException;

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

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
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
	public void testExpectedLocal(String expected) {
		Game gameLocal = new LocalGame(expected);
		App app = new App(gameLocal);
		assertEquals(expected, app.startGame());
		assertEquals(74, gameLocal.getCallCount());
	}

	public void testLocal() {
		testExpectedLocal("53375480");
	}

	public void testApi() {
		try {
			String apiUrl = "http://172.16.37.129/";
			String token = "tokendaf";
			ApiClient client = new ApiClient(apiUrl, 5000, 5000);

			StartRequest startRequest = new StartRequest();
			startRequest.setToken(token);
			StartResponse startResponse = client.start(startRequest);

			TestRequest testRequest = new TestRequest();
			testRequest.setToken(token);
			testRequest.setResult("53375480");
			TestResponse testResponse = client.test(testRequest);
		} catch (Exception e) {
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

	public void testResponse() {
		String name = "tokendaf";
		String error = "error";
		int size = 8;
		int quizzId = 8;
		int wrong_place = 8;
		int good = 8;
		StartResponse startResponse = new StartResponse();
		startResponse.setName(name);
		assertEquals(startResponse.getName(), name);

		startResponse.setSize(size);
		assertEquals(startResponse.getSize(), size);

		startResponse.setQuizzId(quizzId);
		assertEquals(startResponse.getQuizzId(), quizzId);

		startResponse.setError(error);
		assertEquals(startResponse.getError(), error);

		String expectedToString = "StartResponse{" + "size = '" + size + '\'' + ",name = '" + name + '\''
				+ ",quizz_id = '" + quizzId + '\'' + "}";
		assertEquals(expectedToString, startResponse.toString());

		TestResponse testResponse = new TestResponse();
		testResponse.setError(error);
		assertEquals(testResponse.getError(), error);

		testResponse.setGood(good);
		assertEquals(testResponse.getGood(), good);

		testResponse.setWrongPlace(wrong_place);
		assertEquals(testResponse.getWrongPlace(), wrong_place);
		expectedToString = "Response{" + "good = '" + good + '\'' + ",wrong_place = '" + wrong_place + '\'' + "}";
		assertEquals(expectedToString, testResponse.toString());

	}

}
