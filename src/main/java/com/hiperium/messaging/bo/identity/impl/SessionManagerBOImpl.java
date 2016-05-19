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

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

import com.hiperium.common.services.exception.InformationException;
import com.hiperium.common.services.restful.dto.ServiceDetailsDTO;
import com.hiperium.common.services.restful.identity.IdentityRegistryPath;
import com.hiperium.common.services.restful.identity.IdentityService;
import com.hiperium.messaging.bo.identity.SessionManagerBO;

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

    /** The property identityService. */
	private IdentityService identityService;
	
	/** The property curatorClient. */
	@Inject
	private CuratorFramework curatorClient;
	
	/** The property serviceDiscovery. */
	private ServiceDiscovery<ServiceDetailsDTO> serviceDiscovery;
	/** The property serializer. */
	private JsonInstanceSerializer<ServiceDetailsDTO> serializer;
	
	/**
	 * Component initialization.
	 */
	@PostConstruct
	public void init() {
		this.identityService = IdentityService.getInstance();
		this.serializer = new JsonInstanceSerializer<ServiceDetailsDTO>(ServiceDetailsDTO.class); // Payload Serializer
		this.serviceDiscovery = ServiceDiscoveryBuilder.builder(ServiceDetailsDTO.class)
				.client(this.curatorClient)
				.basePath(IdentityRegistryPath.BASE_PATH)
				.serializer(this.serializer)
				.build();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isUserLoggedIn(String userToken) throws InformationException {
		return this.identityService.isUserLoggedIn(this.getServiceURI(IdentityRegistryPath.IS_USER_LOGGED_IN), userToken);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isHomeLoggedIn(String homeToken) throws InformationException {
		return this.identityService.isHomeLoggedIn(this.getServiceURI(IdentityRegistryPath.IS_HOME_LOGGED_IN), homeToken);
	}
	
	/**
	 * 
	 * @param serviceRegistryPath
	 * @return
	 * @throws Exception
	 */
	private String getServiceURI(String serviceRegistryPath) throws InformationException {
		Collection<ServiceInstance<ServiceDetailsDTO>> services;
		try {
			services = this.serviceDiscovery.queryForInstances(serviceRegistryPath);
			if(services == null || services.isEmpty()) {
	        	throw new Exception("No results found for querying services called: " + serviceRegistryPath);
	        } else {
	        	for(final ServiceInstance<ServiceDetailsDTO> service : services) {
	        		return service.buildUriSpec();
	            }
	        }
		} catch (Exception e) {
			throw new InformationException(e.getMessage());
		}
		return null;
	}

}
