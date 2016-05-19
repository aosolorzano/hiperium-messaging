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
package com.hiperium.messaging.restful.exception;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.hiperium.common.services.exception.PropertyValidationException;
import com.hiperium.common.services.logger.HiperiumLogger;


/**
 * This class represents an implementation of managing system exceptions
 * occurring while executing some business action.
 * 
 * @author Andres Solorzano
 * 
 */
@Provider
public class PropertyValidationExceptionMapper implements ExceptionMapper<PropertyValidationException> {

	/** The property log. */
    @Inject
    protected HiperiumLogger log;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Response toResponse(PropertyValidationException ex) {
		this.log.debug("toResponse - BEGIN");
		Map<String, String> responseObj = new HashMap<String, String>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        this.log.debug("toResponse - END");
		return Response.status(Status.BAD_REQUEST)
				.entity(responseObj)
				.build();
	}
}
