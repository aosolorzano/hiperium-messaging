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
package com.hiperium.messaging.bo.device.mdb;

import javax.annotation.PostConstruct;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.hiperium.commons.client.dto.DeviceDTO;
import com.hiperium.commons.services.logger.HiperiumLogger;
import com.hiperium.messaging.bo.device.DeviceBO;
import com.hiperium.messaging.bo.generic.GenericBusinessObject;
import com.hiperium.messaging.common.bean.ConfigurationBean;
import com.hiperium.messaging.service.converter.DeviceConverter;

/**
 * This class represents a Message Driven Bean that gets a message from the cloud server for
 * any Internet user smart home administration.
 * 
 * @author Andres Solorzano
 * 
 */
@MessageDriven(name = "UserDeviceOperationMDB", activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination",     propertyValue = ConfigurationBean.USER_DEVICE_OPERATION_QUEUE),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class UserDeviceOperationMDB extends GenericBusinessObject implements MessageListener {

	/** The LOGGER property for logger messages. */
	private static final HiperiumLogger LOGGER = HiperiumLogger.getLogger(UserDeviceOperationMDB.class);
	
    /** The property deviceBO. */
    @EJB
    private DeviceBO deviceBO;
    
	/** The converter property. */
	private DeviceConverter converter;
	
	/**
	 * Initializes component.
	 */
	@PostConstruct
	public void init() {
		this.converter = new DeviceConverter();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onMessage(Message message) {
		LOGGER.debug("onMessage - START");
		try {
			TextMessage tm = (TextMessage) message;
			String jsonMessage = tm.getText();
			LOGGER.debug("Message: " + jsonMessage);
			DeviceDTO deviceDTO = this.converter.fromJsonToDeviceDTO(jsonMessage);
			// The token ID is the session identifier obtained from Hiperium Home at start up time.
			if(deviceDTO != null && super.getSessionManagerBO().isUserLoggedIn(deviceDTO.getTokenId())) {
				this.deviceBO.userOperation(deviceDTO, deviceDTO.getTokenId());
			}
		} catch (Exception e) {
			LOGGER.error("onMessage - ERROR: " + e.getMessage());
		}
		LOGGER.debug("onMessage - END");
	}
}
