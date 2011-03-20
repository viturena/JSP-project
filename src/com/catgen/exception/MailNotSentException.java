package com.catgen.exception;

public class MailNotSentException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MailNotSentException(String e){
		super(e);
	}
}
