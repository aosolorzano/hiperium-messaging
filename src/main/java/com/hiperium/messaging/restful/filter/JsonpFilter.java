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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import com.hiperium.common.services.exception.InformationException;
import com.hiperium.common.services.logger.HiperiumLogger;

/**
 * 
 * @author Andres Solorzano
 *
 */
public class JsonpFilter implements Filter {
    
	/** The LOGGER property for logger messages. */
	private static final HiperiumLogger LOGGER = HiperiumLogger.getLogger(JsonpFilter.class);
	
	/** The callback method to use . */
    private static final String CALLBACK_METHOD = "jsonpcallback";
    /** This is a simple safe pattern check for the callback method. */
    public static final Pattern SAFE_PRN = Pattern.compile("[a-zA-Z0-9_\\.]+");
    /** The property CONTENT_TYPE. */
    public static final String CONTENT_TYPE = "application/javascript";
	
    /*
     * (non-Javadoc)
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig config) throws ServletException {
        //Nothing needed
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, 
    		FilterChain chain) throws IOException, ServletException {
    	LOGGER.debug("doFilter - BEGIN");

        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        
        //extract the callback method from the request query parameters
        String callback = getCallbackMethod(httpRequest);
        
        try {
        	if (isJSONPRequest(callback)) {
            	//Need to check if the callback method is safe
                if (!SAFE_PRN.matcher(callback).matches()) {
    				InformationException exception = new InformationException(
    						"JSONP Callback method '" + CALLBACK_METHOD + "' parameter not valid function");
                    throw new ServletException(exception);
                }
                
                //Will stream updated response
                final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                
                //Create a custom response wrapper to adding in the padding
                final HttpServletResponse httpResponse = (HttpServletResponse) response;
                HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(httpResponse) {
                    @Override
                    public ServletOutputStream getOutputStream() throws IOException {
                        return new ServletOutputStream() {
                            @Override
                            public void write(int b) throws IOException {
                                byteStream.write(b);
                            }
    						@Override
    						public boolean isReady() {
                                return false;
    						}
    						@Override
    						public void setWriteListener(WriteListener arg0) {
    							// Do nothing.
    						}
                        };
                    }
                    @Override
                    public PrintWriter getWriter() throws IOException {
                        return new PrintWriter(byteStream);
                    }                
                };

                //Process the rest of the filter chain, including the JAX-RS request
                chain.doFilter(request, responseWrapper);
                
                //Override response content and encoding
                response.setContentType(CONTENT_TYPE);
                response.setCharacterEncoding("UTF-8");
                
                //Write the padded updates to the output stream.
                response.getOutputStream().write((callback + "(").getBytes());
                response.getOutputStream().write(byteStream.toByteArray());
                response.getOutputStream().write(");".getBytes());
            } else {
            	//Request is not a JSONP request move on
                chain.doFilter(request, response);
            }
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
        LOGGER.debug("doFilter - END");
    }

    /**
     * 
     * @param httpRequest
     * @return
     */
    private String getCallbackMethod(HttpServletRequest httpRequest) {
        return httpRequest.getParameter(CALLBACK_METHOD);
    }

    /**
     * 
     * @param callbackMethod
     * @return
     */
    private boolean isJSONPRequest(String callbackMethod) {
        //A simple check to see if the query parameter has been set.
        return (callbackMethod != null && callbackMethod.length() > 0);
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
        //Nothing to do
    }
}