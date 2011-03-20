package com.catgen.exception;

import com.catgen.Error;

public class VendorCredentialInvalidOrExpiredException extends Exception {
	private static final long serialVersionUID = 1L;

	public VendorCredentialInvalidOrExpiredException(){
		super(Error.VENDOR_CREDENTIAL_INVALID_OR_EXPIRED);
	}
}
