package com.catgen.exception;

import com.catgen.Error;

public class DuplicateCatalogCodeException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateCatalogCodeException(){
		super(Error.DUPLICATE_CATALOG_CODE);
	}
}
