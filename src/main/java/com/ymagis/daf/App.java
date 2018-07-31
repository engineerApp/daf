package com.ymagis.daf;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
		int[] ret = app.phase1();
		Set<Integer> alreadyPlaced = new HashSet<Integer>();
		int[] test = new int[size];
		app.phase2(0, alreadyPlaced, test, ret);
		System.out.println(Arrays.toString(ret));
		System.out.println(Arrays.toString(test));
	}

	public int[] phase1() {
		String number = "";
		int count = 0;
		int[] ret = new int[size];
		for (int i = 0; i < 10; i++) {
			number = "";
			for (int j = 0; j < size; j++) {
				number += i;
			}
			TestResponse testResponse = getTestResponse(number);
			if (testResponse.getGood() > 0) {
				for (int j = 0; j < testResponse.getGood(); j++) {
					ret[count] = i;
					count++;
				}
			}
			if (count == size) {
				break;
			}
		}
		System.out.println("Phase 1 " + Arrays.toString(ret));
		return ret;
	}

	private String intToString(int[] number) {
		StringBuffer string = new StringBuffer();
		for (int i : number) {
			string.append(i + "");
		}
		return string.toString();
	}

	private int checkTest(int[] test) {
		TestResponse testResponse = getTestResponse(intToString(test));
		int good = testResponse.getGood();
		if (good == size) {
			System.out.println("good answer: found");
			System.out.println(Arrays.toString(test));
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

	public void phase2(int idx, Set<Integer> alreadyPlaced, int[] test, int[] tableNumber) {
		checkTest(tableNumber);
		int[] trouve = new int[size];
		Arrays.fill(trouve, tableNumber[0]);
		int[] p = new int[size];
		Arrays.fill(p, tableNumber[0]);
		for (int c = 0; c < size; ++c) {
			int noteMax = 0;
			for (int n = 0; n < size; ++n) {
				p[c] = tableNumber[n];
				int note = checkTest(p);
				if ((note > 0) && (note >= noteMax)) {
					noteMax = note;
					trouve[c] = tableNumber[n];
				}
			}
		}
		checkTest(p);
	}

	public TestResponse check(String result, String rightAnswer) {
		// si
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
