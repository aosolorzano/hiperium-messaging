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

/**
 * @author Andres Solorzano
 *
 */
public final class RegistryControlPath {

	// ***************************************************************** //
	// ***************************************************************** //
	// ********************* SERVICE REGISTRY PATH ********************* //
	// ***************************************************************** //
	// ***************************************************************** //

	/** The property BASE_PATH with value /services/control. */
	public static final String BASE_PATH = "/services/control";

	// ***************************************************************** //
	// ***************************************************************** //
	// ********************* SERVICE REGISTRY NAME ********************* //
	// ***************************************************************** //
	// ***************************************************************** //

	/** The FIND_DEVICE_BY_HOME_ID property path. */
	public static final String FIND_DEVICE_BY_HOME_ID = "findDeviceByHomeId";

	/** The FIND_DEVICE_BY_ZONE_ID property path. */
	public static final String FIND_DEVICE_BY_ZONE_ID = "findDeviceByZoneId";

	/** The FIND_DEVICE_BY_CLASS property path. */
	public static final String FIND_DEVICE_BY_CLASS = "findDeviceByClass";

	/** The DEVICE_HOME_OPERATION property path. */
	public static final String DEVICE_HOME_OPERATION = "deviceHomeOperation";

	/** The CREATE_TASK property name. */
	public static final String CREATE_TASK = "createTask";

	/** The UPDATE_TASK property name. */
	public static final String UPDATE_TASK = "updateTask";

	/** The DELETE_TASK property name. */
	public static final String DELETE_TASK = "deleteTask";

	/** The FIND_TASK_BY_HOME_ID property path. */
	public static final String FIND_TASK_BY_HOME_ID = "findTaskByHomeId";

	/** The FIND_ZONE_BY_PROFILE_ID property path. */
	public static final String FIND_ZONE_BY_PROFILE_ID = "findZoneByProfileId";

	/** The FIND_ZONE_BY_ID property path. */
	public static final String FIND_ZONE_BY_ID = "findZoneById";

	/** The UPDATE_ZONE property name. */
	public static final String UPDATE_ZONE = "updateZone";

}
