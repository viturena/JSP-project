package com.catgen.factories;

import com.google.gdata.client.http.AuthSubUtil;

public class GoogleFactory {
	public static void main(String args[]){
		String nextUrl = "http://localhost:8080/NetMarket/";
		String scope = "https://docs.google.com/feeds/";
		boolean secure = false;  // set secure=true to request secure AuthSub tokens
		boolean session = true;
		String authSubUrl = AuthSubUtil.getRequestUrl(nextUrl, scope, secure, session);
		System.out.println(authSubUrl);
	}
}
