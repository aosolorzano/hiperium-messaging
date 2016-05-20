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
package com.hiperium.messaging.restful.module;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.jms.Topic;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.hiperium.commons.client.dto.DeviceDTO;
import com.hiperium.commons.services.exception.PropertyValidationException;
import com.hiperium.commons.services.logger.HiperiumLogger;
import com.hiperium.commons.services.restful.path.MessagingRestfulPath;
import com.hiperium.messaging.common.bean.ConfigurationBean;
import com.hiperium.messaging.restful.generic.GenericREST;


/**
 * 
 * @author Andres Solorzano
 * 
 */
@Path(MessagingRestfulPath.DEVICE_MESSAGES)
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class DeviceMessageResource extends GenericREST<DeviceDTO> {

	/** The property log. */
    @Inject
    private HiperiumLogger log;

    /** The property context. */
    @Inject
    private JMSContext context;
    
    /** The property queue. */
    @Resource(lookup = ConfigurationBean.CLOUD_DEVICE_QUEUE)
    private Queue queue;
    
    /** The property topic. */
    @Resource(lookup = ConfigurationBean.CLOUD_DEVICE_TOPIC)
    private Topic topic;
    
    /**
     * 
     * @param deviceDTO
     * @return
     * @throws WebApplicationException
     * @throws PropertyValidationException
     */
	@POST
	@Path(MessagingRestfulPath.HOME_MESSAGE)
	public Response homeMessage(String deviceDTO) throws WebApplicationException, PropertyValidationException {
		this.log.debug("homeMessage - BEGIN: " + deviceDTO);
		final Destination destination = this.queue;
		this.context.createProducer().send(destination, deviceDTO);
		this.log.debug("homeMessage - END");
		return Response.ok().build();
	}
	
	/**
	 * 
	 * @param deviceDTO
	 * @return
	 * @throws WebApplicationException
	 * @throws PropertyValidationException
	 */
	@POST
	@Path(MessagingRestfulPath.USER_MESSAGE)
	public Response userMessage(String deviceDTO) throws WebApplicationException, PropertyValidationException {
		this.log.debug("userMessage - BEGIN: " + deviceDTO);
		final Destination destination = this.topic;
		this.context.createProducer().send(destination, deviceDTO);
		this.log.debug("userMessage - END");
		return Response.ok().build();
	}
}
