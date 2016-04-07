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
package com.hiperium.messaging.bo.device.impl;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.validation.constraints.NotNull;

import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

import com.hiperium.messaging.bo.device.DeviceBO;
import com.hiperium.messaging.common.ConfigurationBean;
import com.hiperium.messaging.dto.DeviceDTO;
import com.hiperium.messaging.logger.HiperiumLogger;
import com.hiperium.messaging.restful.RegistryControlPath;
import com.hiperium.messaging.restful.control.DeviceService;
import com.hiperium.messaging.restful.dto.ServiceDetailsDTO;

/**
 * 
 * @author Andres Solorzano
 *
 */
@Startup
@Singleton
@Lock(LockType.READ)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class DeviceBOImpl implements DeviceBO {

	/** The LOGGER property for logger messages. */
	private static final HiperiumLogger LOGGER = HiperiumLogger.getLogger(DeviceBOImpl.class);
	
	/** The property deviceService. */
	private DeviceService deviceService;
	
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
		this.deviceService = DeviceService.getInstance();
		this.serializer = new JsonInstanceSerializer<ServiceDetailsDTO>(ServiceDetailsDTO.class); // Payload Serializer
		this.serviceDiscovery = ServiceDiscoveryBuilder.builder(ServiceDetailsDTO.class)
				.client(this.configurationBean.getClient())
				.basePath(RegistryControlPath.BASE_PATH)
				.serializer(this.serializer)
				.build();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void homeOperation(@NotNull DeviceDTO deviceDTO, @NotNull String tokenID) throws Exception {
		LOGGER.debug("homeOperation - START");
		// The token ID is the session identifier obtained from Hiperium Home at start up time.
		this.deviceService.homeOperation(this.getServiceURI(RegistryControlPath.DEVICE_HOME_OPERATION), deviceDTO, tokenID); 
		LOGGER.debug("homeOperation - END");
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
