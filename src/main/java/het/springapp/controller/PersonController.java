package het.springapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import het.springapp.model.Note;
import het.springapp.model.Person;
import het.springapp.service.PersonService;

@RestController
public class PersonController {
	@Autowired
	PersonService personService;

	public final Log log = LogFactory.getLog(CoreController.class);
	
	@RequestMapping(value = "/person", method = RequestMethod.POST, produces="text/html", consumes="application/json")
    public @ResponseBody String person(@RequestBody Person person, HttpServletRequest request) {
		
		if (person.getId().equals("")) {
			//new person
			log.info("attempting to create "+person.getfName());
			personService.create(person, person.getUserName());
			return "success";
		} else {
			log.info("%%%%%%%%%%%     attempting to update person: "+person.getUserName());
			personService.update(person);
			log.info("%%%%%%%%%%%%  DONE UPDATING %%%%%%%%%%%%");
			return "success";
		}
				
	}
	
	
}
