package com.aurora.rti.util;

import org.springframework.http.HttpHeaders;

public class GetHeaders {
	private HttpHeaders headers;
	
	public GetHeaders(){
		headers = new HttpHeaders();
		headers.set("Access-Control-Allow-Origin", "*");
		headers.set("Access-Control-Allow-Methods", "GET,PUT,POST");
		headers.set("Access-Control-Max-Age", "3600");
		headers.set("Access-Control-Allow-Headers", "x-requested-with");
	}
	
	public HttpHeaders getHeaders() {
		return headers;
	}
}
