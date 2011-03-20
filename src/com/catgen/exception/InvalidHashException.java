package com.catgen.exception;

import com.catgen.Error;

public class InvalidHashException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidHashException(){
		super(Error.HASH_NOT_VALID_EXCEPTION);
	}
}
