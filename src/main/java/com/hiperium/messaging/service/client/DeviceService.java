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

import com.hiperium.commons.client.dto.DeviceDTO;
import com.hiperium.commons.client.exception.InformationException;
import com.hiperium.commons.client.http.HttpClient;
import com.hiperium.commons.services.logger.HiperiumLogger;
import com.hiperium.messaging.service.converter.DeviceConverter;

/**
 * This service class send calls to the REST Service to operate with different
 * actions originated from the system.
 * 
 * @author Andres Solorzano
 * 
 */
public final class DeviceService extends HttpClient {
	
	/** The LOGGER property for logger messages. */
	private static final HiperiumLogger LOGGER = HiperiumLogger.getLogger(DeviceService.class);
	
	/** The property converter. */
	private DeviceConverter converter;
	
	/** The property service. */
	private static DeviceService service = null;
	
	/**
	 * Class constructor.
	 */
	private DeviceService() {
		// Nothing to do.
	}
	
	/**
	 * Return the singleton instance.
	 * 
	 * @return
	 */
	public static DeviceService getInstance() {
		if(service == null) {
			service = new DeviceService();
		}
		return service;
	}
	
	/**
	 * 
	 * @param deviceDTO
	 */
	public void homeOperation(@NotNull String serviceURI, @NotNull DeviceDTO deviceDTO, @NotNull String tokenID) {
		LOGGER.debug("homeOperation - START");
		try {
			super.putToService(serviceURI, this.converter.toJSON(deviceDTO), "application/json", "text/plain", tokenID);
		} catch (InformationException e) {
			LOGGER.error(e.getMessage(), e);
		}
		LOGGER.debug("homeOperation - END");
	}
}
