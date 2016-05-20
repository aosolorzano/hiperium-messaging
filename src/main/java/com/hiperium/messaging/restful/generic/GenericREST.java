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
package com.hiperium.messaging.restful.generic;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.hiperium.commons.services.exception.PropertyValidationException;

/**
 * This class implements all needed methods for all RESTful Web Services.
 * 
 * @author Andres Solorzano
 * 
 */
public class GenericREST<T> {
	
	/** The property servletRequest. */
	@Context
	private HttpServletRequest servletRequest;
	
	/** The property uriInfo. */
	@Context
	private UriInfo uriInfo;

	/** The validator property. */
	@Inject
	private Validator validator;
	
	/**
	 * Default Constructor.
	 */
	public GenericREST() {
		super();
	}

	/**
	 * Gets the HTTP token identifier.
	 * 
	 * @return the HTTP token identifier.
	 */
	public String getTokenId() {
		return this.servletRequest.getHeader("Authorization");
	}
	
	/**
	 * Response OK to the client.
	 * 
	 * @return
	 */
	public Response ok() {
		return Response.status(Response.Status.OK).build();
	}
	
	/**
	 * Response OK to the client.
	 * 
	 * @return
	 */
	public Response ok(String jsonObject) {
		return Response.status(Response.Status.OK)
				.entity(jsonObject)
				.type(MediaType.APPLICATION_JSON)
				.build();
	}
	
	/**
	 * Response to the client that contains the created object in a JSON format.
	 * 
	 * @param jsonObject
	 *            the created object
	 * @return the client that contains the created object in a JSON format.
	 */
	public Response created(String jsonObject) {
		return Response.status(Response.Status.CREATED)
				.entity(jsonObject)
				.type(MediaType.APPLICATION_JSON)
				.build();
	}

	/**
	 * Response to the client that contains the updated object in a JSON format.
	 * 
	 * @param jsonObject
	 *            the updated object
	 * @return the client that contains the updated object in a JSON format.
	 */
	public Response updated(String jsonObject) {
		return Response.status(Response.Status.OK)
				.entity(jsonObject)
				.type(MediaType.APPLICATION_JSON)
				.build();
	}
	
	/**
	 * Validate the object fields using bean validation.
	 * 
	 * @param object
	 *            the object to validate
	 * @throws PropertyValidationException
	 *             field validation messages
	 */
	public void validateObjectProperties(T object) throws PropertyValidationException {
		Set<ConstraintViolation<T>> violations = this.validator.validate(object);
		if (!violations.isEmpty()) {
			throw new PropertyValidationException(new HashSet<ConstraintViolation<?>>(violations));
		}
	}
	
	/**
	 * Gets the servletRequest property.
	 * 
	 * @return the servletRequest.
	 */
	public HttpServletRequest getServletRequest() {
		return servletRequest;
	}
}
