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
package com.hiperium.messaging.bo.device.impl;

import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import com.hiperium.commons.client.dto.DeviceDTO;
import com.hiperium.commons.services.logger.HiperiumLogger;
import com.hiperium.messaging.bo.device.DeviceBO;
import com.hiperium.messaging.common.service.DeviceServiceManager;

/**
 * 
 * @author Andres Solorzano
 *
 */
@Startup
@Singleton
@Lock(LockType.READ)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class DeviceBOImpl implements DeviceBO {

	/** The property deviceServiceManager. */
	@EJB
	private DeviceServiceManager deviceServiceManager;
	
	/** The property log. */
	@Inject
	private HiperiumLogger log;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void userOperation(@NotNull DeviceDTO deviceDTO, @NotNull String tokenID) throws Exception {
		this.log.debug("userOperation - START");
		this.deviceServiceManager.userOperation("", deviceDTO, tokenID); 
		this.log.debug("userOperation - END");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void homeOperation(@NotNull DeviceDTO deviceDTO, @NotNull String tokenID) throws Exception {
		this.log.debug("homeOperation - START");
		// The token ID is the session identifier obtained from Hiperium Home at start up time.
		this.deviceServiceManager.homeOperation("", deviceDTO, tokenID); 
		this.log.debug("homeOperation - END");
	}
}
