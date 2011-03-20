package com.catgen.exception;

public class NoVendorFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoVendorFoundException(String err){
		super(err);
	}
}
