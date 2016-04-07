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
 * This class enumerates the accepted application channel types for the
 * system.
 * 
 * @author Andres Solorzano
 */
public enum EnumAccessChannel {

	/** Resource MOBILE for mobile. */
	MOBILE("mobile"),
	
	/** Resource HOME for home. */
	HOME("home"),

	/** Resource PLATFORM for platform. */
	PLATFORM("platform");

	/** Property value. */
	private final String value;

	/**
	 * The Enumeration constructor.
	 * 
	 * @param valor
	 *            Value for the element of the Enumeration
	 */
	private EnumAccessChannel(String value) {
		this.value = value;
	}

	/**
	 * Return the value associate to the element of the enumeration.
	 * 
	 * @return the value associate with the enumeration
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * Decodes an user agent to set up an Access Channel.
	 * 
	 * @param userAgent
	 *            the user agent
	 * @return the decoded channel
	 */
	public static EnumAccessChannel decodeDesktopOrMobile(String userAgent) {
		EnumAccessChannel accessChannel = EnumAccessChannel.HOME;
		if (userAgent.contains("iPad") 
				|| userAgent.contains("iPhone")
				|| userAgent.contains("iPod") 
				|| userAgent.contains("Android")) {
			accessChannel = EnumAccessChannel.MOBILE;
		}
		return accessChannel;
	}
}
