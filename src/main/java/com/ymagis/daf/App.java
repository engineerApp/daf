package com.ymagis.daf;

import java.util.HashSet;

import org.apache.commons.lang3.StringUtils;

import com.ymagis.daf.game.Game;
import com.ymagis.daf.game.LocalGame;
import com.ymagis.daf.response.StartResponse;
import com.ymagis.daf.response.TestResponse;

public class App {
	private Game game;
	private static int size = 0;

	public App(Game iGame) {
		game = iGame;
	}

	public static void main(String[] args) {
		Game game = new LocalGame("1549542788878738738767727272600000");
		App app = new App(game);
		System.out.println(app.startGame());
		System.out.println("call count : " + game.getCallCount());
	}

	public String startGame() {
		StartResponse startResponse = game.start();
		if (startResponse == null) {
			System.out.println("Can't call api");
			System.exit(0);
		}
		if (startResponse.getError() == null) {
			size = startResponse.getSize();
		} else {
			size = 8;
		}
		String ret = phase1();
		return phase2(ret);
	}

	public String phase1() {
		String number = "";
		int count = 0;
		String ret = "";
		for (int i = 0; i < 10; i++) {
			number = StringUtils.repeat(i + "", size);
			int good = checkTest(number);
			if (good == size) {
				return number;
			}
			if (good > 0) {
				for (int j = 0; j < good; j++) {
					count++;
					ret += i;
				}
			}
			if (count == size) {
				break;
			}
		}
		System.out.println("Phase 1: " + ret);
		return ret;
	}

	private int checkTest(String test) {
		TestResponse testResponse = game.test(test);
		if (testResponse == null || testResponse.getError() != null) {
			System.out.println("Error: " + testResponse.getError());
			System.exit(0);
		}
		int good = testResponse.getGood();
		return good;
	}

	public Character[] stringToSet(String s) {
		HashSet<Character> characters = new HashSet<Character>();
		for (int i = 0; i < s.length(); i++) {
			characters.add(s.charAt(i));
		}
		return characters.toArray(new Character[characters.size()]);
	}

	private boolean isIsomorph(String string) {
		String s = "";
		for (int i = 0; i < 10; i++) {
			s = StringUtils.repeat(i + "", size);
			if (s.equals(string)) {
				return true;
			}
		}
		return false;
	}

	public String phase2(String numberList) {
		String string = StringUtils.repeat(numberList.charAt(0), size);
		StringBuilder trouve = new StringBuilder(string);
		StringBuilder p;
		Character[] characters = stringToSet(numberList);
		for (int c = 0; c < size; ++c) {
			System.out.println("----------------case " + c + "----------------");
			p = new StringBuilder(string);
			int noteMax = 0;
			for (int n = 0; n < characters.length; ++n) {
				p.setCharAt(c, characters[n]);
				if (isIsomorph(p.toString())) {
					continue;
				}
				System.out.println(c + " " + n + " " + p.toString());
				int note = checkTest(p.toString());
				if (note == size) {
					return p.toString();
				}
				if ((note > 0) && (note >= noteMax)) {
					noteMax = note;
					trouve.setCharAt(c, characters[n]);
					System.out.println(c + " " + n + " trouve : " + trouve + " noteMax : " + noteMax);
				}
			}
			numberList = removeOne(trouve.charAt(c), numberList);
			characters = stringToSet(numberList);
		}
		int noteFinal = checkTest(trouve.toString());
		if (noteFinal == size) {
			return trouve.toString();
		}
		return "not found best result is : " + trouve.toString();
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
