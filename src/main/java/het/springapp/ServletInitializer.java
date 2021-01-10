package het.springapp;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.servlet.DispatcherServlet;

public class ServletInitializer extends SpringBootServletInitializer {
	
	private Log log = LogFactory.getLog(ServletInitializer.class);
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		
		ServletRegistration.Dynamic registration = servletContext.addServlet("rootDispatcher", DispatcherServlet.class);
		registration.setLoadOnStartup(1);
		
		log.info("setting servlet to load on startup**");
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CoreBootApplication.class);
	}

}
