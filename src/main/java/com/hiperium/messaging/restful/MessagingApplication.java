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
package com.hiperium.messaging.restful;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.CloseableUtils;

import com.hiperium.commons.client.registry.ServiceRegister;
import com.hiperium.commons.client.registry.path.MessagingRegistryPath;
import com.hiperium.commons.services.logger.HiperiumLogger;
import com.hiperium.commons.services.restful.path.MessagingRestfulPath;
import com.hiperium.messaging.common.bean.ConfigurationBean;


/**
 * This class represents the JAX-RS Activation Implementation for JAX-RS
 * applications.
 * 
 * @author Andres Solorzano
 *
 */
@ApplicationPath(MessagingRestfulPath.MESSAGING_PATH)
public class MessagingApplication extends Application {
	
	/** The LOGGER property for logger messages. */
	private static final HiperiumLogger LOGGER = HiperiumLogger.getLogger(MessagingApplication.class);

	/** The property client. */
	@Inject
	private CuratorFramework client;

	/** The property servers. */
	private List<ServiceRegister> registers;
	
	/** The property serviceHost. */
	private String serviceHost; 
	/** The property servicePort. */
	private Integer servicePort;
	
    /**
	 * Register all web services in apache zookeeper service registry.
	 */
	@PostConstruct
	public void init() {
		LOGGER.info("init() - START");
		this.registers = new ArrayList<ServiceRegister>();
		this.serviceHost = ConfigurationBean.getPropertyValue(ConfigurationBean.SERVER_HOST);
		this.servicePort = Integer.valueOf(ConfigurationBean.getPropertyValue(ConfigurationBean.SERVER_PORT));
		this.registerService(MessagingRestfulPath.DEVICE_MESSAGES.concat(MessagingRestfulPath.HOME_MESSAGE), MessagingRegistryPath.HOME_MESSAGE, "1.0", "");
		this.registerService(MessagingRestfulPath.DEVICE_MESSAGES.concat(MessagingRestfulPath.USER_MESSAGE), MessagingRegistryPath.USER_MESSAGE, "1.0", "");
		LOGGER.info("init() - END");
	}
	
	/**
	 * 
	 * @param servicePath
	 * @param serviceName
	 * @param serviceVersion
	 * @param serviceDetails
	 */
	public void registerService(String servicePath, String serviceName, String serviceVersion, String serviceDetails) {
		String serviceURI = this.getUri(servicePath);
		ServiceRegister server;
		try {
			server = new ServiceRegister(this.client, this.servicePort, serviceURI, serviceName, MessagingRegistryPath.BASE_PATH, serviceDetails, serviceVersion);
			server.start();
			this.registers.add(server);
			LOGGER.info("Service added to the Registry: " + serviceURI);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * Close all opened resource in this component.
	 */
	@PreDestroy
	public void destroy() {
		for(ServiceRegister register : this.registers) {
			CloseableUtils.closeQuietly(register);
		}
		this.registers.clear();
		this.client.close();
	}
	/**
	 * 
	 * @param servicePath
	 * @return
	 */
	private String getUri(final String servicePath) {
		return String.format("{scheme}://%s:{port}%s%s%s", 
				this.serviceHost,
				MessagingRestfulPath.MESSAGING_CONTEXT_ROOT, 
				MessagingRestfulPath.MESSAGING_PATH, 
				servicePath);
	}
	
}
