package com.catgen.exception;

public class RegistrationException extends Exception {
	private static final long serialVersionUID = 1L;

	public RegistrationException(){
		super("Registration Failed");
	}
}
