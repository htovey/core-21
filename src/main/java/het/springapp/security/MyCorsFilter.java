/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package het.springapp.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.OncePerRequestFilter;;

/**
 *
 * @author heather
 */

@ComponentScan
public class MyCorsFilter extends CorsFilter {
	
	private CorsConfigurationSource configSource;
	
	public MyCorsFilter(CorsConfigurationSource configSource) {
		super(configSource);
		this.configSource = configSource;
	}

	private Log log = LogFactory.getLog(MyCorsFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
     
    	log.debug("CORS filter processing for request type "+request.getMethod());
    	CorsConfiguration corsConfig = configSource.getCorsConfiguration(request);
    	log.debug("allowed origins: ");
    	for (String origin1 : corsConfig.getAllowedOrigins()) {
    		log.debug(origin1);
    	}
    	
    	log.debug("request origin: "+request.getHeader("Origin"));
    	String origin = corsConfig.checkOrigin(request.getHeader("Origin"));
    	
        if (origin == null) {
        	throw new IllegalStateException("No Matching Origin Found");
        }
        
    	response.setHeader("Access-Control-Allow-Credentials", "true");
    	response.setHeader("Access-Control-Allow-Origin", origin);
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Origin, Accept, authorization, Authorization, Connection, Content-Type, Accept, X-Requested-With, remember-me, x-csrf-token");
        response.setHeader("Access-Control-Expose-Headers", "Origin, Accept, authorization, Authorization, TokenError");
        response.setHeader("Access-Control-Max-Age", "12000");
        
        log.debug("response headers set: ");
        
        for (String name : response.getHeaderNames()) {
        	log.debug("NAME: "+name+", VALUE: "+response.getHeader(name));
        }
        
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
        	log.debug("OPTIONS method ****");
            log.debug("OPTIONS STATUS: "+response.getStatus());
        } else {
            filterChain.doFilter(request, response);
        }
	         
    }   
    
}
