/**
 * Product  : Hiperium Project
 * Architect: Andres Solorzano.
 * Created  : 08-05-2009 - 23:30:00
 * 
 * The contents of this file are copyrighted by Andres Solorzano 
 * and it is protected by the license: "GPL V3." You can find a copy of this 
 * license at: http://www.hiperium.com/about/licence.html
 * 
 * Copyright 2014 Andres Solorzano. All rights reserved.
 * 
 */
package com.hiperium.messaging.common;

/**
 * This enumeration specifies the session register's result.
 * 
 * @author Andres Solorzano
 */
public enum EnumAuthenticationResult {

	/** The LOGIN_SUCCESS element witch value is loginSuccessful. */
	LOGIN_SUCCESS("loginSuccessful"),

	/** The ACCOUNT_LOCKED element witch value is userAccountLocked. */
	LOCKED_ACCOUNT("userAccountLocked"),

	/** The INCORRECT_ACCESS element witch value is incorrectIncomeAccess. */
	INCORRECT_ACCESS("incorrectIncomeAccess");

	/** Property value. */
	private final String value;

	/**
	 * The Enumeration constructor.
	 * 
	 * @param valor
	 *            Value for the element of the Enumeration
	 */
	private EnumAuthenticationResult(String value) {
		this.value = value;
	}

	/**
	 * Return the value associate to the element of the enumeration.
	 * 
	 * @return the value associate with the enumeration
	 */
	public String value() {
		return this.value;
	}
}
