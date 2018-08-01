package com.ymagis.daf;

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
	}

	public void testLocal() {
		testExpectedLocal("1234567890");
		testExpectedLocal("0000000");
		testExpectedLocal("1277777890");
		testExpectedLocal("127890");
		testExpectedLocal("0");
		testExpectedLocal("890");
	}

	public void testRequest() {
		TestRequest testRequest = new TestRequest();
		testRequest.setToken("tokendaf");
		assertEquals(testRequest.getToken(), "tokendaf");
		testRequest.setResult("12345");
		assertEquals(testRequest.getResult(), "12345");
		StartRequest startRequest = new StartRequest();
		startRequest.setToken("tokendaf");
		assertEquals(testRequest.getToken(), "tokendaf");
	}

	public void testResponse() {
		String name = "tokendaf";
		String error = "error";
		int size = 8;
		int quizzId = 8;
		int wrong_place = 8;
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

		testResponse.setWrongPlace(wrong_place);
		assertEquals(testResponse.getWrongPlace(), wrong_place);

	}

}
