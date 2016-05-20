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

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.hiperium.commons.services.logger.HiperiumLogger;

/**
 *
 * @author Andres Solorzano
 */
public class CorsFilter implements Filter {

	/** The LOGGER property for logger messages. */
	private static final HiperiumLogger LOGGER = HiperiumLogger.getLogger(CorsFilter.class);
	
    /*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init()
	 */
    @Override
	public void init(FilterConfig arg0) throws ServletException {
		// Auto-generated method stub
	}
    
    /*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter()
	 */
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		LOGGER.debug("filter - BEGIN");
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.addHeader("Access-Control-Max-Age", "151200");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type, X-Requested-With, Authorization, Accept");
        filterChain.doFilter(servletRequest, servletResponse);
        LOGGER.debug("filter - END");
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		// Auto-generated method stub
	}

}
