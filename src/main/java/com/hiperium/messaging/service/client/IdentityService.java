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
package com.hiperium.messaging.service.client;

import javax.validation.constraints.NotNull;

import com.hiperium.commons.client.exception.InformationException;
import com.hiperium.commons.client.http.HttpClient;
import com.hiperium.commons.services.logger.HiperiumLogger;

/**
 * This service class send calls to the REST Service to operate with different
 * actions originated from the system.
 * 
 * @author Andres Solorzano
 * 
 */
public final class IdentityService extends HttpClient {

	/** The LOGGER property for logger messages. */
	private static final HiperiumLogger LOGGER = HiperiumLogger.getLogger(IdentityService.class);
    
	/** The property service. */
	private static IdentityService service = null;
	
	/**
	 * Class constructor.
	 */
	private IdentityService() {
		super();
	}

	/**
	 * Return the singleton instance.
	 * 
	 * @return
	 */
	public static IdentityService getInstance() {
		if(service == null) {
			service = new IdentityService();
		}
		return service;
	}
	
	/**
	 * 
	 * @param serviceURI
	 * @param userToken
	 * @return
	 */
	public boolean isUserLoggedIn(@NotNull String serviceURI, @NotNull String userToken) {
		LOGGER.debug("isUserLoggedIn - START");
		try {
			super.getFromService(serviceURI, "application/json", userToken);
			return true;
		} catch (InformationException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return false;
	}
	
	/**
	 * 
	 * @param serviceURI
	 * @param homeToken
	 * @return
	 */
	public boolean isHomeLoggedIn(@NotNull String serviceURI, @NotNull String homeToken) {
		LOGGER.debug("isHomeLoggedIn - START");
		try {
			super.getFromService(serviceURI, "application/json", homeToken);
			return true;
		} catch (InformationException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return false;
	}
}
