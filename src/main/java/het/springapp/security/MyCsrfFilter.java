package het.springapp.security;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.filter.OncePerRequestFilter;

public class MyCsrfFilter extends OncePerRequestFilter {

	public static Log log = LogFactory.getLog(MyCsrfFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		CsrfToken token = csrfTokenRepository().generateToken(request);
		
		log.debug("Headers: ");
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			log.debug(headerName+":  "+request.getHeader(headerName));
		}

		
		response.setHeader("X-CSRF-HEADER", token.getHeaderName());
        response.setHeader("X-CSRF-PARAM", token.getParameterName());
        response.setHeader("x-csrf-token", token.getToken());
		
        csrfTokenRepository().saveToken(token, request, response);
        
        log.debug("user header: "+request.getHeader("Authorization"));
        log.debug("MyCsrfFilter current Http status: "+response.getStatus());
        log.debug("csrf token: "+token.getToken());
        log.debug("");
        
        filterChain.doFilter(request, response);
	}

	 private static CsrfTokenRepository csrfTokenRepository () {
	    	HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
	    	return repository;
	    }
}
