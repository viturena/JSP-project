package com.catgen.exception;

public class SessionInactiveException extends Exception {
	private static final long serialVersionUID = 1L;

	public SessionInactiveException(){
		super("Session Inactive. Please login again.");
	}
}
