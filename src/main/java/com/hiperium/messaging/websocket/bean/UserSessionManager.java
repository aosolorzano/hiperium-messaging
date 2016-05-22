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
package com.hiperium.messaging.websocket.bean;

import java.io.IOException;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.websocket.EncodeException;
import javax.websocket.Session;

import com.hiperium.commons.client.dto.DeviceDTO;
import com.hiperium.commons.services.logger.HiperiumLogger;

/**
 * This class manages the home devices sessions against socket API. In the case
 * of Web Sessions, there must be an Internet user connected to a specific home,
 * that is why any home must manage various sessions; the Web Application
 * session and the home appliance session.
 * 
 * @author Andres Solorzano
 *
 */
@Startup
@Singleton
@LocalBean
@Lock(LockType.READ)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class UserSessionManager {
	
	/** The property logger. */
	@Inject
	private HiperiumLogger logger;
	
	/** The httpSessionMap property. */
	private HashMap<Long, HashMap<String, Session>> socketSessionMap;
	
	/**
	 * Class constructor.
	 */
	@PostConstruct
	public void init() {
		this.logger.debug("init - BEGIN");
		// WebSocket sessions may have many users connected from the same Home. 
		this.socketSessionMap = new HashMap<Long, HashMap<String, Session>>();
		this.logger.debug("init - END");
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void addSocketSession(@NotNull @Min(value = 1L) Long homeId, @NotNull Session session) {
		if(session != null) {
			HashMap<String, Session> socketSession = null;
			if(this.socketSessionMap.containsKey(homeId)) {
				socketSession = this.socketSessionMap.get(homeId);
				socketSession.put(session.getId(), session);
			} else {
				socketSession = new HashMap<String, Session>();
				socketSession.put(session.getId(), session);
				this.socketSessionMap.put(homeId, socketSession);
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void sendToUsersByHomeId(@NotNull Session actualSession, @NotNull DeviceDTO deviceDTO) {
		if(deviceDTO.getHomeId() != null) {
			HashMap<String, Session> socketSession = this.socketSessionMap.get(deviceDTO.getHomeId());
			if(socketSession != null) {
				// Send activation message to all connected users to a specific Home
				for(Session session : socketSession.values()) {
					if(session != null && session.getId() != actualSession.getId()) {
						try {
							session.getBasicRemote().sendObject(deviceDTO);
						} catch (IOException e) {
							this.logger.error(e.getMessage(), e);
						} catch (EncodeException e) {
							this.logger.error(e.getMessage(), e);
						}
					}
				}
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void removeSocketSession(@NotNull @Min(value = 1L) Long homeId, @NotNull Session session) {
		if(this.socketSessionMap.containsKey(homeId)) {
			HashMap<String, Session> socketSession = this.socketSessionMap.get(homeId);
			socketSession.remove(session.getId());
		}
	}
}
