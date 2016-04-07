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
package com.hiperium.messaging.bo.generic;

import javax.ejb.EJB;

import com.hiperium.messaging.bo.security.SessionManagerBO;

/**
 * This class is an generalization of business objects.
 *
 * @author Andres Solorzano
 *
 */
public class GenericBusinessObject {

    /** The property sessionManager. */
    @EJB
    private SessionManagerBO sessionManager;

    /**
     *
     */
    public GenericBusinessObject() {
        // Nothing to do.
    }

    /**
     *
     * @return
     */
    public SessionManagerBO getSessionManager() {
        return sessionManager;
    }
}
