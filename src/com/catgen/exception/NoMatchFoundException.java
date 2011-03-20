package com.catgen.exception;

public class NoMatchFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoMatchFoundException(String str){
		super(str);
	}
}
