package com.catgen.exception;

public class NotASuperAdminException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public NotASuperAdminException(String e){
		super(e);
	}
}
