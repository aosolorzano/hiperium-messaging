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
package com.hiperium.messaging.bo.device;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

import com.hiperium.commons.client.dto.DeviceDTO;

/**
 * This interface declares the methods needed for the device service.
 * 
 * @author Andres Solorzano
 * 
 */
@Local
public interface DeviceBO {

	/**
	 * 
	 * @param deviceDTO
	 * @param tokenID
	 * @throws Exception
	 */
	void homeOperation(@NotNull DeviceDTO deviceDTO, @NotNull String tokenID) throws Exception;
}
