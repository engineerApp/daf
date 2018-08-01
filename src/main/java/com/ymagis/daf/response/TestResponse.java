package com.ymagis.daf.response;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TestResponse {
	@JsonProperty("good")
	private int good;

	@JsonProperty("wrong_place")
	private int wrongPlace;

	@JsonProperty("Error")
	private String error;

	public TestResponse() {

	}

	public void setGood(int good) {
		this.good = good;
	}

	public int getGood() {
		return good;
	}

	public void setWrongPlace(int wrongPlace) {
		this.wrongPlace = wrongPlace;
	}

	public int getWrongPlace() {
		return wrongPlace;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "Response{" + "good = '" + good + '\'' + ",wrong_place = '" + wrongPlace + '\'' + "}";
	}
}
