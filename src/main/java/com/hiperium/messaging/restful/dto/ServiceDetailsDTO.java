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
package com.hiperium.messaging.restful.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This is a POJO class that will be used to transmit the values of a details of
 * restful services.
 * 
 * @author Andres Solorzano
 * 
 */
@XmlRootElement(name = "serviceDetailsDTO")
public class ServiceDetailsDTO implements Serializable {

	/**
	 * The property serialVersionUID.
	 */
	private static final long serialVersionUID = 7152058373939920108L;

	/** The property description. */
	@NotNull
	private String description;

	/** The property version. */
	@NotNull
	private String version;

	/**
	 * Default constructor.
	 */
	public ServiceDetailsDTO() {
		super();
	}

	/**
	 * Overloaded constructor.
	 * 
	 * @param version
	 * @param description
	 */
	public ServiceDetailsDTO(String version, String description) {
		this.version = version;
		this.description = description;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ServiceDetailsDTO [description=" + description + ", version=" + version + "]";
	}

}
