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
public final class RegistrySecutityPath {

	// ***************************************************************** //
	// ***************************************************************** //
	// ********************* SERVICE REGISTRY PATH ********************* //
	// ***************************************************************** //
	// ***************************************************************** //

	/** The property BASE_PATH with value /services/security. */
	public static final String BASE_PATH = "/services/security";

	// ***************************************************************** //
	// ***************************************************************** //
	// ********************* SERVICE REGISTRY NAME ********************* //
	// ***************************************************************** //
	// ***************************************************************** //

	/** The USER_AUTHENTICATION property name. */
	public static final String USER_AUTHENTICATION = "userAuthentication";

	/** The HOME_AUTH property name. */
	public static final String HOME_AUTHENTICATION = "homeAuthentication";

	/** The property HOME_SELECTION with path homeSelection. */
	public static final String HOME_SELECTION = "homeSelection";

	/**
	 * The property FIND_APP_USER_BY_ROLE_NAME with path findAppUserByRoleName.
	 */
	public static final String FIND_APP_USER_BY_ROLE_NAME = "findAppUserByRoleName";

	/** The FIND_HOME_USER_BY_ID property name. */
	public static final String FIND_HOME_USER_BY_ID = "findHomeByUserId";

	/** The UPDATE_PROFILE_FUNCTIONALITY property name. */
	public static final String UPDATE_PROFILE_FUNCTIONALITY = "updateProfileFunctionality";

	/** The CREATE_PROFILE property name. */
	public static final String CREATE_PROFILE = "createProfile";

	/** The UPDATE_PROFILE property name. */
	public static final String UPDATE_PROFILE = "updateProfile";

	/** The DELETE_PROFILE property name. */
	public static final String DELETE_PROFILE = "deleteProfile";

	/** The FIND_PROFILE_BY_HOME_ID property name. */
	public static final String FIND_PROFILE_BY_HOME_ID = "findProfileByHomeId";

	/** The GET_SESSION_AUDIT_VO property name. */
	public static final String GET_SESSION_AUDIT_VO = "getSessionAuditVO";

	/** The IS_USER_LOGGED_IN property name. */
	public static final String IS_USER_LOGGED_IN = "isUserLoggedIn";

	/** The END_SESSION property name. */
	public static final String LOGOUT = "logout";

	/** The property UPDATE_USER_PASSWORD with path updateUserPassword. */
	public static final String UPDATE_USER_PASSWORD = "updateUserPassword";

	/** The FIND_USER_BY_ID property path. */
	public static final String FIND_USER_BY_ID = "findUserById";

}
