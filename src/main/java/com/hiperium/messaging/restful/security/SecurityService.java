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
package com.hiperium.messaging.restful.security;

import javax.validation.constraints.NotNull;

import com.hiperium.messaging.exception.InformationException;
import com.hiperium.messaging.http.HttpClient;
import com.hiperium.messaging.logger.HiperiumLogger;

/**
 * This service class send calls to the REST Service to operate with different
 * actions originated from the system.
 * 
 * @author Andres Solorzano
 * 
 */
public final class SecurityService extends HttpClient {

	/** The LOGGER property for logger messages. */
	private static final HiperiumLogger LOGGER = HiperiumLogger.getLogger(SecurityService.class);
    
	/** The property service. */
	private static SecurityService service = null;
	
	/**
	 * Class constructor.
	 */
	private SecurityService() {
		super();
	}

	/**
	 * Return the singleton instance.
	 * 
	 * @return
	 */
	public static SecurityService getInstance() {
		if(service == null) {
			service = new SecurityService();
		}
		return service;
	}
	
	/**
	 * 
	 * @param userToken
	 * @param appToken
	 * @return
	 */
	public boolean isUserLoggedIn(@NotNull String serviceURI, @NotNull String userToken) {
		LOGGER.debug("isUserLoggedIn - START");
		String response;
		try {
			response = super.getFromService(serviceURI, "text/plain", userToken);
			if("OK".equalsIgnoreCase(response)) {
				return true;
			}
		} catch (InformationException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return false;
	}
}
