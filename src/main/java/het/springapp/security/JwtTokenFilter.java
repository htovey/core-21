package het.springapp.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import het.springapp.dao.UserDao;
import het.springapp.dao.impl.UserDaoImpl;
import het.springapp.model.User;
import het.springapp.service.impl.UserDetailsServiceImpl;
import het.springapp.service.UserService;


@Component
public class JwtTokenFilter extends OncePerRequestFilter {
		
	@Autowired
    private JwtTokenUtil jwtTokenUtil;
	
//	@Autowired
//    private UserService userService;
	
	@Autowired
	private UserDetailsService coreUserDetailsService;
	
    public JwtTokenFilter() {}

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {
        // Get authorization header and validate
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isEmpty(header) || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // Get jwt token and validate
        final String token = header.split(" ")[1].trim();
        
        try {
        	jwtTokenUtil.validate(token);
        } catch (SignatureException ex) {
        	logger.error("Invalid JWT signature - {}  ---"+  ex.getMessage());
        	chain.doFilter(request, response);
        	return;
        } catch (MalformedJwtException ex) {
        	logger.error("Invalid JWT token - {} ---" +  ex.getMessage());
        	chain.doFilter(request, response);
        	return;
	    } catch (ExpiredJwtException ex) {
	        logger.error("Expired JWT token - {} ---" + ex.getMessage());
	        response.setHeader("TokenError", "Token Expired");
	        chain.doFilter(request, response);
        	return;
	    } catch (UnsupportedJwtException ex) {
	        logger.error("Unsupported JWT token - {} ---" + ex.getMessage());
	        chain.doFilter(request, response);
        	return;
	    } catch (IllegalArgumentException ex) {
	    	logger.error("JWT claims string is empty - {} ---" + ex.getMessage());
	    	chain.doFilter(request, response);
        	return;
	    }

        /**
         * test code for token errors
         */
//        if (!token.isEmpty()) {
//        	response.setHeader("TokenError", "Token Expired");
//        	chain.doFilter(request,response);
//        	return;
//        }
        
        
        // Get user identity and set it on the spring security context

        UserDetails userDetails = coreUserDetailsService.loadUserByUsername(jwtTokenUtil.getUsername(token));
        
        UsernamePasswordAuthenticationToken
            authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null,
                userDetails == null ?
                    List.of() : userDetails.getAuthorities()
            );

        authentication.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

}
