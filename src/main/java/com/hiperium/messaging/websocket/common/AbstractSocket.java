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
package com.hiperium.messaging.websocket.common;

import javax.websocket.EndpointConfig;
import javax.websocket.Session;

/**
 * @author Andres Solorzano
 *
 */
public abstract class AbstractSocket {

	/** The USER_OPERATION_URI property. */
	public static final String USER_OPERATION_URI = "/api/message/user/home/{homeId}";
	
	/** The HOME_OPERATION_URI property. */
	public static final String HOME_OPERATION_URI = "/api/message/device/home/{homeId}";

	/**
	 * 
	 * @param session
	 * @param config
	 * @param homeId
	 */
	public abstract void onOpen(Session session, EndpointConfig config,
			Long homeId);

	/**
	 * 
	 * @param session
	 * @param homeId
	 */
	public abstract void onClose(Session session, Long homeId);

}
