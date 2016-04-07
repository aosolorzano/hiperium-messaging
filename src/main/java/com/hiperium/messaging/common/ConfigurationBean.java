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
package com.hiperium.messaging.common;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import com.hiperium.messaging.logger.HiperiumLogger;

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
	
	/** The property ZK_HOST. */
	private static final String ZK_HOST = "apache.zookeeper.host";
	
	/** The CLOUD_DEVICE_QUEUE property path. */
	public static final String CLOUD_DEVICE_QUEUE = "jms/queue/deviceQueue";
	
	/** The LOGGER property for logger messages. */
	private static final HiperiumLogger LOGGER = HiperiumLogger.getLogger(ConfigurationBean.class);
	
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
	 * Component initialization.
	 */
	@PostConstruct
	public void init() {
		LOGGER.debug("init() - START");
		// START CURATOR CLIENT
		this.client = CuratorFrameworkFactory.newClient(getPropertyValue(ZK_HOST),
				new ExponentialBackoffRetry(1000, 3));
		this.client.start();
		LOGGER.debug("init() - END");
	}
	
	/**
	 * 
	 * @param injectionPoint
	 * @return the client
	 */
	public CuratorFramework getClient() {
		return this.client;
	}
}
