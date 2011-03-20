package com.catgen.exception;

import com.catgen.Error;

public class SpreadsheetNotPublishedException extends Exception {
	private static final long serialVersionUID = 1L;

	public SpreadsheetNotPublishedException(){
		super(Error.SPREADSHEET_NOT_PUBLISHED_EXCEPTION);
	}
}
