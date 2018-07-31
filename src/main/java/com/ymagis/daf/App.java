package com.ymagis.daf;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.ymagis.daf.response.StartResponse;
import com.ymagis.daf.response.TestResponse;

public class App {
	private Game game;
	private static boolean testLocal = true;
	private static boolean isStart = true;
	private static int callCount = 0;
	private static int size = 0;

	public final static String RIGHT = "12345";

	public App() {
		game = new Game();
	}

	public static void main(String[] args) {
		App app = new App();
		size = RIGHT.length();
		if (!testLocal && !isStart) {
			StartResponse startResponse = app.game.start();
			System.out.println(startResponse);
			size = startResponse.getSize();
		}
		String ret = app.phase1();
		Set<Integer> alreadyPlaced = new HashSet<Integer>();
		app.phase2(0, alreadyPlaced, ret);
		System.out.println(ret);
	}

	public String phase1() {
		String number = "";
		int count = 0;
		String ret = "";
		for (int i = 0; i < 10; i++) {
			number = StringUtils.repeat(i + "", 5);
			TestResponse testResponse = getTestResponse(number);
			if (testResponse.getGood() > 0) {
				for (int j = 0; j < testResponse.getGood(); j++) {
					ret += i;
					count++;
				}
			}
			if (count == size) {
				break;
			}
		}
		System.out.println("Phase 1 " + ret);
		return ret;
	}

	private String intToString(int[] number) {
		StringBuffer string = new StringBuffer();
		for (int i : number) {
			string.append(i + "");
		}
		return string.toString();
	}

	private int checkTest(String test) {
		TestResponse testResponse = getTestResponse(test);
		int good = testResponse.getGood();
		if (good == size) {
			System.out.println("good answer: found");
			System.out.println(test);
			System.out.println("call count : " + callCount);
			System.exit(0);
		}
		return good;
	}

	private TestResponse getTestResponse(String s) {
		callCount++;
		TestResponse testResponse = null;
		if (testLocal) {
			testResponse = check(s, RIGHT);
		} else {
			testResponse = game.test(s);
		}
		System.out.println(testResponse);
		System.out.println(s);
		return testResponse;
	}

	public void phase2(int idx, Set<Integer> alreadyPlaced, String tableNumber) {
		checkTest(tableNumber);
		String string = StringUtils.repeat(tableNumber.charAt(0), size);
		StringBuilder trouve = new StringBuilder(string);
		StringBuilder p = new StringBuilder(string);
		p.setCharAt(4, 'x');
		for (int c = 0; c < size; ++c) {
			int noteMax = 0;
			for (int n = 0; n < size; ++n) {
				p.setCharAt(c, tableNumber.charAt(n));
				int note = checkTest(p.toString());
				if ((note > 0) && (note >= noteMax)) {
					noteMax = note;
					trouve.setCharAt(c, tableNumber.charAt(n));
				}
			}
		}
		checkTest(p.toString());
	}

	public TestResponse check(String result, String rightAnswer) {
		TestResponse response = new TestResponse();
		char[] resultArray = result.toCharArray();
		char[] rightAnswerArray = rightAnswer.toCharArray();
		int size = resultArray.length;
		int good = 0;
		int wrong_place = 0;
		for (int i = 0; i < size; i++) {
			if (resultArray[i] == rightAnswerArray[i]) {
				good++;
			}
		}
		response.setGood(good);
		response.setWrongPlace(wrong_place);
		return response;
	}
}
