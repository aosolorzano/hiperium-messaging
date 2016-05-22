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
package com.hiperium.messaging.common.bean;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import com.hiperium.commons.services.logger.HiperiumLogger;

/**
 * 
 * @author Andres Solorzano
 * @version 1.0
 */
@Startup
@Singleton
@LocalBean
@Lock(LockType.READ)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ConfigurationBean {
	
	/** The LOGGER property for logger messages. */
	private static final HiperiumLogger LOGGER = HiperiumLogger.getLogger(ConfigurationBean.class);
	
	/** The property ZK_HOST. */
	private static final String ZK_HOST = "apache.zookeeper.host";
	/** The property SERVER_PORT with value hiperium.server.port. */
	public static final String SERVER_PORT = "hiperium.server.port";
	/** The property SERVER_HOST with value hiperium.server.host. */
	public static final String SERVER_HOST = "hiperium.server.host";
	
	/** The USER_DEVICE_OPERATION_QUEUE property path jms/queue/userDeviceQueue. */
	public static final String USER_DEVICE_OPERATION_QUEUE = "jms/queue/userDeviceQueue";
	/** The HOME_DEVICE_OPERATION_QUEUE property path jms/queue/homeDeviceQueue. */
	public static final String HOME_DEVICE_OPERATION_QUEUE = "jms/queue/homeDeviceQueue";
	/** The CLOUD_DEVICE_TOPIC property path. */
	public static final String CLOUD_DEVICE_TOPIC = "jms/topic/deviceTopic";
	
	/** The property PROPERTIES. */
    public static final Properties PROPERTIES = new Properties();
    
    /** The property client. */
	private CuratorFramework client;
	
	/**
	 * Class initialization
	 */
	static {
		// Set up the namingContext for the JNDI lookup
		try {
			PROPERTIES.load(ConfigurationBean.class.getClassLoader().getResourceAsStream("common.properties"));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	/**
	 * Component initialization.
	 */
	@PostConstruct
	public void init() {
		LOGGER.debug("init() - START");
		// START CURATOR CLIENT
		this.client = CuratorFrameworkFactory.newClient(getPropertyValue(ZK_HOST), new ExponentialBackoffRetry(1000, 3));
		this.client.start();
		LOGGER.debug("init() - END");
	}
	
	/**
	 * Searches for the property with the specified key in this resource object.
	 * The method returns null if the property is not found.
	 * 
	 * @param key
	 * @return
	 */
	public static String getPropertyValue(String key) {
		return PROPERTIES.getProperty(key);
	}
	
	/**
	 * 
	 * @param injectionPoint
	 * @return the client
	 */
	@Produces
	public CuratorFramework getClient(InjectionPoint injectionPoint) {
		return this.client;
	}
	
	/**
	 * Produces the HiperiumLogger component for CDI injection.
	 * 
	 * @param injectionPoint
	 * @return
	 */
	@Produces
	public HiperiumLogger produceHiperiumLogger(InjectionPoint injectionPoint) {
		return HiperiumLogger.getLogger(injectionPoint.getMember().getDeclaringClass());
	}
	
	/**
	 * Close all opened resources.
	 */
	@PreDestroy
	public void destroy() {
		LOGGER.debug("destroy() - START");
		// DO NOT CLOSE CURATOR CLIENT HERE, IT MUST BE OPENED TO THE END.
		LOGGER.debug("destroy() - END");
	}
}
