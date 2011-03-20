package com.catgen.exception;

public class InvalidPasswordException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidPasswordException(){
		super("Password Invalid");
	}
}
