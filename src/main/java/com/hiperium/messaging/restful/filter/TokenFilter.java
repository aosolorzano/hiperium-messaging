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
package com.hiperium.messaging.restful.filter;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.hiperium.common.services.exception.InformationException;
import com.hiperium.common.services.logger.HiperiumLogger;
import com.hiperium.common.services.restful.messaging.MessagingRestfulPath;
import com.hiperium.messaging.bo.identity.SessionManagerBO;


/**
 * @author Andres Solorzano
 * @version 1.0
 */
public class TokenFilter implements Filter {

	/** The LOGGER property for logger messages. */
	private static final HiperiumLogger LOGGER = HiperiumLogger.getLogger(TokenFilter.class);
	
	/** The property sessionManagerBO. */
	@EJB
	private SessionManagerBO sessionManagerBO;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// Nothing to do.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) 
			throws IOException, ServletException {
		LOGGER.debug("doFilter - BEGIN");
		// Validates that the request is a valid HTTP request
		if (!(request instanceof HttpServletRequest)) {
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
			return;
        }
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String path = httpRequest.getRequestURI();
		LOGGER.debug("Accessing To: ".concat(path));
		
		// Get Token ID and validates it against session map.
		String tokenId = httpRequest.getHeader("Authorization");
		if(StringUtils.isBlank(tokenId)) {
			HttpServletResponse res = (HttpServletResponse) response;
			res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		try {
			if(path.endsWith(MessagingRestfulPath.USER_MESSAGE)) {
				if(!this.sessionManagerBO.isUserLoggedIn(tokenId)) {
					HttpServletResponse res = (HttpServletResponse) response;
					res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
					return;
				}
			} else if(path.endsWith(MessagingRestfulPath.HOME_MESSAGE)) {
				if(!this.sessionManagerBO.isHomeLoggedIn(tokenId)) {
					HttpServletResponse res = (HttpServletResponse) response;
					res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
					return;
				}
			}
		} catch (InformationException e) {
			throw new ServletException(e.getMessage());
		}
		
		//Process the rest of the filter chain
		filterChain.doFilter(request, response);
		LOGGER.debug("doFilter - END");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		// Do nothing here.
	}
}
