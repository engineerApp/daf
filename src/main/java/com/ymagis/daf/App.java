package com.ymagis.daf;

import java.util.HashMap;
import java.util.HashSet;

import org.apache.commons.lang3.StringUtils;

import com.ymagis.daf.game.ApiGame;
import com.ymagis.daf.game.Game;
import com.ymagis.daf.response.StartResponse;
import com.ymagis.daf.response.TestResponse;

public class App {
	private Game game;
	private static int size = 0;
	private HashMap<String, Integer> isomorphResults = new HashMap<String, Integer>();

	App(Game iGame) {
		game = iGame;
	}

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		App app = new App(new ApiGame());
		app.printResult(startTime, app.startGame());
	}

	public void printResult(long startTime, String answer) {
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println("------------------------------------------");
		System.out.println("ElapsedTime : " + (float) elapsedTime / 1000 + " s");
		System.out.println("Answer : " + answer);
		System.out.println("Call count : " + game.getCallCount());
		System.out.println("------------------------------------------");
	}

	String startGame() {
		StartResponse startResponse = game.start();
		if (startResponse == null) {
			System.out.println("Can't call api check network or vpn");
			System.exit(0);
		}
		if (startResponse.getError() == null) {
			size = startResponse.getSize();
		} else {
			size = 100;
		}
		String ret = phase1();
		return phase2(ret);
	}

	private String phase1() {
		int count = 0;
		StringBuilder ret = new StringBuilder();
		String number = getFound1(count, ret);
		if (number != null)
			return number;
		return ret.toString();
	}

	private String getFound1(int count, StringBuilder ret) {
		String number;
		String dictionnaire = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for (int i = 0; i < dictionnaire.length(); i++) {
			number = StringUtils.repeat(dictionnaire.charAt(i), size);
			int good = checkTest(number);
			isomorphResults.put(number, good);
			if (good == size) {
				return number;
			}
			if (good > 0) {
				for (int j = 0; j < good; j++) {
					count++;
					ret.append(dictionnaire.charAt(i));
				}
			}
			if (count == size) {
				break;
			}
		}
		return null;
	}

	private int checkTest(String test) {
		TestResponse testResponse = game.test(test);
		if (testResponse == null || testResponse.getError() != null) {
			System.out.println("Error: testResponse = " + testResponse);
			System.exit(0);
		}
		int good = testResponse.getGood();
		// System.out.println(test + " : " + good);
		return good;
	}

	private Character[] stringToSet(String s) {
		HashSet<Character> characters = new HashSet<Character>();
		for (int i = 0; i < s.length(); i++) {
			characters.add(s.charAt(i));
		}
		return characters.toArray(new Character[0]);
	}

	private boolean isIsomorph(String string) {
		for (int i = 1; i < string.length(); i++) {
			if (string.charAt(0) != string.charAt(i)) {
				return false;
			}
		}
		return true;
	}

	private String phase2(String numberList) {
		String string = StringUtils.repeat(numberList.charAt(0), size);
		StringBuilder trouve = new StringBuilder(string);
		Character[] characters = stringToSet(numberList);
		for (int c = 0; c < size; ++c) {
			String p1 = getFound2(trouve, characters, c);
			if (p1 != null)
				return p1;
			numberList = removeOne(trouve.charAt(c), numberList);
			assert numberList != null;
			characters = stringToSet(numberList);
		}
		int noteFinal = checkTest(trouve.toString());
		if (noteFinal == size) {
			return trouve.toString();
		}
		return "not found best result is : " + trouve.toString();
	}

	private String getFound2(StringBuilder trouve, Character[] characters, int c) {
		StringBuilder p;
		p = new StringBuilder(StringUtils.repeat(characters[0], size));
		int noteIsomorph = isomorphResults.get(p.toString());
		trouve.setCharAt(c, characters[0]);
		for (Character character : characters) {
			p.setCharAt(c, character);
			int note;
			if (isIsomorph(p.toString())) {
				note = isomorphResults.get(p.toString());
			} else {
				note = checkTest(p.toString());
			}
			if (note == size) {
				return p.toString();
			}
			if ((noteIsomorph + 1) == note) {
				trouve.setCharAt(c, character);
				break;
			}
		}
		return null;
	}

	private String removeOne(char c, String numberList) {
		StringBuilder stringBuilder = new StringBuilder(numberList);
		for (int i = 0; i < numberList.length(); i++) {
			if (c == numberList.charAt(i)) {
				stringBuilder.deleteCharAt(i);
				return stringBuilder.toString();
			}
		}
		return null;
	}
}
