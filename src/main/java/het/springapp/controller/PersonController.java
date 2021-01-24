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
import het.springapp.model.User;
import het.springapp.service.PersonService;
import het.springapp.service.UserService;

@RestController
public class PersonController {
	@Autowired
	PersonService personService;
	@Autowired
	UserService userService;

	public final Log log = LogFactory.getLog(PersonController.class);
	
	@RequestMapping(path = "/person", method = RequestMethod.POST, produces="application/json", consumes="application/json")
    public @ResponseBody String person(@RequestBody Person person, HttpServletRequest request) {
		
		if (person.getId().equals("")) {
			//new person
			log.info("attempting to create "+person.getfName());
			User user = userService.findByUserName(person.getUserName());
			person.setUserName(user.getUserName());
			personService.create(person);
			return "success";
		} else {
			log.info("%%%%%%%%%%%     attempting to update person: "+person.getUserName());
			personService.update(person);
			log.info("%%%%%%%%%%%%  DONE UPDATING %%%%%%%%%%%%");
			return "success";
		}
				
	}	
}
