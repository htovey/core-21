package het.springapp.security;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import het.springapp.security.RestAuthenticationFailure;


/**
 *
 * @author heather
 */

@EnableWebSecurity
public class SecurityAdapter extends WebSecurityConfigurerAdapter{

	@Value("${security.enable-csrf}")
	private boolean csrfEnabled;
	
	@Value("${allowed.origins}")
	private String allowedOrigins;
	
	@Autowired
	private DataSource dataSource; 
	
	@Autowired
	private RestAuthenticationFailure restAuthenticationEntryFailure;
	
	@Autowired
	private UserDetailsService coreUserDetailsService;
	
	@Autowired
	private JwtTokenFilter jwtTokenFilter;
	
	@Bean("passwordEncoder")
	public BCryptPasswordEncoder getPasswordEncoder() {
	      return new BCryptPasswordEncoder();
	}
	
	@Bean 
	MyCorsFilter myCorsFilter() {
		return new MyCorsFilter(corsConfigurationSource());
	}
	
	@Bean 
	MyCsrfFilter myCsrfFilter() {
		return new MyCsrfFilter();
	}
	
	@Bean
	public DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(coreUserDetailsService);
		authProvider.setPasswordEncoder(getPasswordEncoder());
		return authProvider;
	}
	
//	  @Bean
//	  public PersistentTokenRepository tokenRepository() {
//	    JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl = new JdbcTokenRepositoryImpl();
//	    jdbcTokenRepositoryImpl.setDataSource(dataSource);
//	    return jdbcTokenRepositoryImpl;
//	  }
   
	 @Bean
	    CorsConfigurationSource corsConfigurationSource() {
	        CorsConfiguration configuration = new CorsConfiguration();
	        configuration.setAllowedOrigins(Arrays.asList(StringUtils.tokenizeToStringArray(allowedOrigins, ",")));
	        configuration.setAllowedMethods(Arrays.asList("GET","POST", "OPTIONS"));
	        configuration.setAllowedHeaders(Arrays.asList("Origin, Accept, authorization, Content-Type, Accept, X-Requested-With, remember-me, x-csrf-token"));
	        configuration.addAllowedHeader("authorization");
	        configuration.addAllowedHeader("content-type");
	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", configuration);
	        return source;
	    }
    	
		
	@Override
    protected void configure(HttpSecurity http) throws Exception {    
	    	
//		http = http.addFilterAt(myCorsFilter(), CorsFilter.class);	
//		
//		http = http.httpBasic().and().csrf().disable();
//		
//		http = http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//				.and();
//		
//		http = http
//				.exceptionHandling()
//				.authenticationEntryPoint(restAuthenticationEntryFailure)
//				.and();
//		
//		http.authorizeRequests().antMatchers("/reset**", "/requestReset").permitAll()
//		.and()
//		.authorizeRequests().anyRequest().authenticated();
		http
		.httpBasic()
		.and()
		.csrf().disable()
		.headers().disable()
		.logout().disable()
		.addFilterAt(myCorsFilter(), CorsFilter.class)	
		.addFilterBefore(myCsrfFilter(), SecurityContextPersistenceFilter.class)
		.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryFailure)
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.authorizeRequests().antMatchers("/login", "/reset**", "/requestReset").permitAll()
		.and()
		.authorizeRequests().anyRequest().authenticated();
		
		http.addFilterBefore(jwtTokenFilter, BasicAuthenticationFilter.class);
    }
		
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.authenticationProvider(authProvider());
    }    
	
}
