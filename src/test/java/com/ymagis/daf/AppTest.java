package com.ymagis.daf;

import com.ymagis.daf.game.Game;
import com.ymagis.daf.game.LocalGame;

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
	public void testExpected(String expected) {
		Game gameLocal = new LocalGame(expected);
		App app = new App(gameLocal);
		assertEquals(expected, app.startGame());
	}

	public void test2App() {
		testExpected("1234567890");
		testExpected("0000000");
		testExpected("1277777890");
		testExpected("127890");
		testExpected("0");
		testExpected("890");
	}
}
