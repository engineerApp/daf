package com.ymagis.daf;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.ymagis.daf.game.ApiGame;
import com.ymagis.daf.game.Game;
import com.ymagis.daf.response.StartResponse;
import com.ymagis.daf.response.TestResponse;

public class App {
	private Game game;
	private static int size = 0;

	public App(Game iGame) {
		game = iGame;
	}

	public static void main(String[] args) {
		Game game = new ApiGame();
		App app = new App(game);
		System.out.println(app.startGame());
		System.out.println("call count : " + game.getCallCount());
	}

	public String startGame() {
		StartResponse startResponse = game.start();
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
			if (good > 0) {
				for (int j = 0; j < good; j++) {
					ret += i;
					count++;
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
		int good = testResponse.getGood();
		return good;
	}

	public String phase2(String tableNumber) {
		String string = StringUtils.repeat(tableNumber.charAt(0), size);
		StringBuilder trouve = new StringBuilder(string);
		StringBuilder p = new StringBuilder(string);
		for (int c = 0; c < size; ++c) {
			int noteMax = 0;
			for (int n = 0; n < size; ++n) {
				p.setCharAt(c, tableNumber.charAt(n));
				int note = checkTest(p.toString());
				if (note == size) {
					return p.toString();
				}
				if ((note > 0) && (note >= noteMax)) {
					noteMax = note;
					trouve.setCharAt(c, tableNumber.charAt(n));
				}
			}
		}
		int noteFinal = checkTest(trouve.toString());
		if (noteFinal == size) {
			return trouve.toString();
		}
		return "not found best resulat is : " + trouve.toString();
	}
}
