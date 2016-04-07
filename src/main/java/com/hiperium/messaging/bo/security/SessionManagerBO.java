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
package com.hiperium.messaging.bo.security;

import javax.ejb.Local;

/**
 * This interface declare the methods needed to zone operations.
 * 
 * @author Andres Solorzano
 * 
 */
@Local
public interface SessionManagerBO {

	/**
	 * 
	 * @param userToken
	 * @return
	 * @throws Exception
	 */
	boolean isUserLoggedIn(String userToken) throws Exception;
	
}
