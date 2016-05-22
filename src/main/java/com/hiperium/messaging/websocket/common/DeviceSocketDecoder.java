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

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import org.apache.commons.lang.StringUtils;

import com.hiperium.commons.client.dto.DeviceDTO;
import com.hiperium.messaging.service.converter.DeviceConverter;

/**
 * @author Andres Solorzano
 *
 */
public class DeviceSocketDecoder implements Decoder.Text<DeviceDTO>{

	/** */
	private DeviceConverter jsonConverter;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(EndpointConfig config) {
		this.jsonConverter = new DeviceConverter();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DeviceDTO decode(String json) throws DecodeException {
		return this.jsonConverter.fromJsonToDeviceDTO(json);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean willDecode(String json) {
		return (StringUtils.isNotBlank(json));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void destroy() {
		// Nothing to do.
	}
}
