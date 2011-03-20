package com.catgen.exception;

import com.catgen.Error;

public class NMMSClosedForHashException extends Exception {
	private static final long serialVersionUID = 1L;

	public NMMSClosedForHashException(){
		super(Error.NMMS_CLOSED_FOR_HASH_INTERCHANGE);
	}
}
