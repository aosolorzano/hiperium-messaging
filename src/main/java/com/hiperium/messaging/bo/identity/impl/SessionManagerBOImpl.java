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
package com.hiperium.messaging.bo.identity.impl;

import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.hiperium.commons.client.exception.InformationException;
import com.hiperium.messaging.bo.identity.SessionManagerBO;
import com.hiperium.messaging.common.service.IdentityServiceManager;

/**
 * This is a bypass bean that is used between Web components and EJB components
 * but only for some methods that the Web components can see.
 *
 * @author Andres Solorzano
 * @version 1.0
 */
@Startup
@Singleton
@Lock(LockType.READ)
@DependsOn("ConfigurationBean")
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class SessionManagerBOImpl implements SessionManagerBO {

    /** The property identityServiceManager. */
	@EJB
	private IdentityServiceManager identityServiceManager;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isUserLoggedIn(String userToken) throws InformationException {
		return this.identityServiceManager.isUserLoggedIn("", userToken);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isHomeLoggedIn(String homeToken) throws InformationException {
		return this.identityServiceManager.isHomeLoggedIn("", homeToken);
	}

}
