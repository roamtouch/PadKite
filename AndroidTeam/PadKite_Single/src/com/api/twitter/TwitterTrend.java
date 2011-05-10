package com.api.twitter;

import java.util.HashMap;
import java.util.Map;

public class TwitterTrend {

	Map<Object, String> mp = new HashMap<Object, String>();

	private String name;
	private String url;
	private String query;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String name) {
		this.query = query;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}