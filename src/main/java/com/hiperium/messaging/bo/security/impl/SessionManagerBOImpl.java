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
package com.hiperium.messaging.bo.security.impl;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

import com.hiperium.messaging.bo.security.SessionManagerBO;
import com.hiperium.messaging.common.ConfigurationBean;
import com.hiperium.messaging.restful.RegistrySecutityPath;
import com.hiperium.messaging.restful.dto.ServiceDetailsDTO;
import com.hiperium.messaging.restful.security.SecurityService;

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
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class SessionManagerBOImpl implements SessionManagerBO {

    /** The property securityService. */
	private SecurityService securityService;
	
	/** The property configurationBean. */
	@EJB
	private ConfigurationBean configurationBean;
	
	/** The property serviceDiscovery. */
	private ServiceDiscovery<ServiceDetailsDTO> serviceDiscovery;
	/** The property serializer. */
	private JsonInstanceSerializer<ServiceDetailsDTO> serializer;
	
	/**
	 * Component initialization.
	 */
	@PostConstruct
	public void init() {
		this.securityService = SecurityService.getInstance();
		this.serializer = new JsonInstanceSerializer<ServiceDetailsDTO>(ServiceDetailsDTO.class); // Payload Serializer
		this.serviceDiscovery = ServiceDiscoveryBuilder.builder(ServiceDetailsDTO.class)
				.client(this.configurationBean.getClient())
				.basePath(RegistrySecutityPath.BASE_PATH)
				.serializer(this.serializer)
				.build();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isUserLoggedIn(String userToken) throws Exception {
		return this.securityService.isUserLoggedIn(this.getServiceURI(RegistrySecutityPath.IS_USER_LOGGED_IN), userToken);
	}
	
	/**
	  * 
	  * @param serviceRegistryPath
	  * @return
	  * @throws Exception
	  */
	private String getServiceURI(String serviceRegistryPath) throws Exception {
		final Collection<ServiceInstance<ServiceDetailsDTO>> services = this.serviceDiscovery.queryForInstances(serviceRegistryPath);
		if(services == null || services.isEmpty()) {
       	throw new Exception("No results found for querying services called: " + serviceRegistryPath);
       } else {
       	for(final ServiceInstance<ServiceDetailsDTO> service : services) {
       		return service.buildUriSpec();
           }
       }
		return null;
	}

}
