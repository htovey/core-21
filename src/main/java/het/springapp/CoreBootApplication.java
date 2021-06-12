package het.springapp;

import javax.persistence.EntityManagerFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import het.springapp.controller.CoreController;

@SpringBootApplication
public class CoreBootApplication {

	final Log log = LogFactory.getLog(CoreController.class);
	
	public static void main(String[] args) {
		
		SpringApplication.run(CoreBootApplication.class, args);
	}

	
}
