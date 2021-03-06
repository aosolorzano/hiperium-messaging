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
package com.hiperium.messaging.websocket.control;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.hiperium.commons.client.dto.DeviceDTO;
import com.hiperium.commons.services.logger.HiperiumLogger;
import com.hiperium.messaging.common.bean.ConfigurationBean;
import com.hiperium.messaging.service.converter.DeviceConverter;
import com.hiperium.messaging.websocket.bean.UserSessionManager;
import com.hiperium.messaging.websocket.common.AbstractSocket;
import com.hiperium.messaging.websocket.common.DeviceSocketDecoder;
import com.hiperium.messaging.websocket.common.DeviceSocketEncoder;

/**
 * @author Andres Solorzano
 */
@ServerEndpoint(value = AbstractSocket.USER_OPERATION_URI, 
	decoders = {DeviceSocketDecoder.class},
	encoders = {DeviceSocketEncoder.class})
public class UserDeviceOperation extends AbstractSocket {

	/** The property log. */
    @Inject
    private HiperiumLogger log;
    
    /** The property context. */
    @Inject
    private JMSContext context;
    
    /** The property queue. */
    private Queue queue;
    
    /** The property topic. */
    private Topic topic;
    
    /** The property userSessionManager. */
    private UserSessionManager userSessionManager;
    
    /** The property converter. */
    private DeviceConverter converter;
    
    /** The property destinationTopic. */
    private Destination destinationTopic;
    
    /** The property destinationQueue. */
    private Destination destinationQueue;
	
    /**
     * Initializes the component.
     */
    @PostConstruct
    public void init() {
    	this.log.debug("init() - START");
    	if(this.context == null) {
    		this.log.error("JMS CONTEXT IS NULL");
    	}
    	this.converter = new DeviceConverter();
    	InitialContext context = null;
		try {
			context = new InitialContext();
			this.userSessionManager = (UserSessionManager) context.lookup(
					"java:global/hiperium-messaging/UserSessionManager!com.hiperium.messaging.websocket.bean.UserSessionManager");
			this.topic = (Topic) context.lookup("java:/".concat(ConfigurationBean.CLOUD_DEVICE_TOPIC));
			if(this.topic == null) {
	    		this.log.error("TOPIC RESOURCE IS NULL");
	    	} else {
	    		this.destinationTopic = this.topic;
	    	}
			this.queue = (Queue) context.lookup("java:/".concat(ConfigurationBean.USER_DEVICE_OPERATION_QUEUE));
			if(this.queue == null) {
	    		this.log.error("QUEUE RESOURCE IS NULL");
	    	} else {
	    		this.destinationQueue = this.queue;
	    	}
		} catch (NamingException e) {
			this.log.error(e.getMessage(), e);
		} finally {
			closeContext(context);
		}
    	this.log.debug("init() - END");
    }
    
	/**
	 * {@inheritDoc}
	 */
	@OnOpen
	public void onOpen(Session session, EndpointConfig config, @PathParam("homeId") Long homeId) {
		this.log.debug("onOpen() - START");
		this.userSessionManager.addSocketSession(homeId, session);
		this.log.debug("onOpen() - END");
	}

	/**
	 * {@inheritDoc}
	 */
	@OnMessage
	public void onMessage(Session session, DeviceDTO deviceDTO) {
		this.log.debug("onMessage - BEGIN: " + deviceDTO);
		try {
			this.context.createProducer().send(this.destinationTopic, this.converter.toJSON(deviceDTO));
			this.context.createProducer().send(this.destinationQueue, this.converter.toJSON(deviceDTO));
			this.userSessionManager.sendToUsersByHomeId(session, deviceDTO);
		} catch (Exception e) {
			this.log.error(e.getMessage(), e);
		}
		this.log.debug("onMessage - END");
	}

	/**
	 * {@inheritDoc}
	 */
	@OnClose
	public void onClose(Session session, @PathParam("homeId") Long homeId) {
		this.log.debug("onClose() - BEGIN: " + homeId);
		this.userSessionManager.removeSocketSession(homeId, session);
		this.log.debug("onClose() - END");
	}
	
	/**
	 * 
	 * @param context
	 */
	private void closeContext(Context context) {
		try {
			if(context != null) {
				context.close();
			}
		} catch (NamingException e) {
			this.log.error(e.getMessage(), e);
		}
	}
}
