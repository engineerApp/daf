package com.ymagis.daf.game;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.ymagis.daf.response.StartResponse;
import com.ymagis.daf.response.TestResponse;

public class LocalGame extends Game {

	private String rightAnswer;

	public LocalGame(String right) {
		super();
		rightAnswer = right;
	}

	public StartResponse start() {
		StartResponse startResponse = new StartResponse();
		startResponse.setSize(rightAnswer.length());
		return startResponse;
	}

	public TestResponse test(String result) {
		super.test(result);
		return check(result);
	}

	public TestResponse check(String result) {
		TestResponse response = new TestResponse();
		char[] resultArray = result.toCharArray();
		char[] rightAnswerArray = rightAnswer.toCharArray();
		int size = resultArray.length;
		int good = 0;
		int wrong_place = 0;
		Set<Integer> alreadyPassed = new HashSet<Integer>();
		for (int i = 0; i < size; i++) {
			if (!alreadyPassed.contains(Integer.parseInt(resultArray[i] + ""))) {
				wrong_place += StringUtils.countMatches(rightAnswer, resultArray[i]);
				alreadyPassed.add(Integer.parseInt(resultArray[i] + ""));
			}
			if (resultArray[i] == rightAnswerArray[i]) {
				good++;
			}
		}
		wrong_place = wrong_place - good;
		response.setGood(good);
		response.setWrongPlace(wrong_place);
		return response;
	}
}
