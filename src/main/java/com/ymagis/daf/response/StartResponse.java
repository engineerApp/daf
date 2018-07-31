package com.ymagis.daf.response;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StartResponse {

	@JsonProperty("size")
	private int size;

	@JsonProperty("name")
	private String name;

	@JsonProperty("quizz_id")
	private int quizzId;

	public StartResponse() {

	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getSize() {
		return size;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setQuizzId(int quizzId) {
		this.quizzId = quizzId;
	}

	public int getQuizzId() {
		return quizzId;
	}

	@Override
	public String toString() {
		return "StartResponse{" + "size = '" + size + '\'' + ",name = '" + name + '\'' + ",quizz_id = '" + quizzId
				+ '\'' + "}";
	}
}
