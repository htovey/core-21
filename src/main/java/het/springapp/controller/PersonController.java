package het.springapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import het.springapp.biz.model.Biz;
import het.springapp.model.Note;
import het.springapp.model.Person;
import het.springapp.model.User;
import het.springapp.service.PersonService;
import het.springapp.service.UserService;
import het.springapp.types.RoleType;

@RestController
@RequestMapping(path="/person")
public class PersonController {
	@Autowired
	PersonService personService;
	@Autowired
	UserService userService;

	public final Log log = LogFactory.getLog(PersonController.class);
	
	@RequestMapping(path = "/create", method = RequestMethod.POST, produces="application/json", consumes="application/json")
    ResponseEntity<String> createPerson(@RequestBody Person person, HttpServletRequest request) {
		String msg = "success";
		HttpStatus status = HttpStatus.CREATED;
		log.info("attempting to create "+person.getfName());
		User user = userService.findByUserName(person.getUserName());
		if (user == null) {
			msg = "Error - no user exists for user name.";
			status = HttpStatus.NOT_ACCEPTABLE;
		} else {
			person.setUserName(user.getUserName());
			personService.create(person);
		}
		return new ResponseEntity<String>(msg, status);
		
	}	

	@RequestMapping(path = "/update", method = RequestMethod.POST, produces="application/json", consumes="application/json")
	public @ResponseBody String updatePerson(@RequestBody Person person, HttpServletRequest request) {
		log.info("%%%%%%%%%%%     attempting to update person: "+person.getlName());
		personService.update(person);
		log.info("%%%%%%%%%%%%  DONE UPDATING %%%%%%%%%%%%");
		return "success";
	}
	
	@RequestMapping(value = "/persons", method = RequestMethod.GET, produces="application/json")
    public List<Map<String, String>> getPersonList(@RequestParam int bizId, @RequestParam ArrayList<String> roleTypes, HttpServletRequest request) throws JSONException {
		log.info("Person List request for bizId "+bizId);
		
		List<User> userList = new ArrayList<User>();
		
		for(String role:  roleTypes) {
			List<User> roleUserList = userService.findUsersByBizIdRoleType(bizId, role);
			for (User user: roleUserList) {
				userList.add(user);
			}
		}
		
		List<Person> personListFromDb = userList.stream()
				.map(user -> personService.findByUserName(user.getUserName()))
				.collect(Collectors.toList());			
		
		List<Map<String,String>> personList = new ArrayList<Map<String,String>>();
		
		for (Person person : personListFromDb) {
			Map<String, String> personMap = new HashMap<String, String>();
			personMap.put("id", String.valueOf(person.getId()));
			personMap.put("fName", person.getfName());
			personMap.put("lName", person.getlName());
			personMap.put("userName", person.getUserName());
			personMap.put("saveDate", person.getSaveDate().toString());
			personList.add(personMap);
		}
		
		return personList;		
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces="application/json")
	public @ResponseBody String deletePerson(@RequestParam String userName) {
		Person person = personService.findByUserName(userName);
		personService.delete(person);
		return "success";
	}
	
	@RequestMapping(method = RequestMethod.GET, produces="application/json")
	ResponseEntity<String> getPerson(@RequestParam String userName) throws JSONException {
		log.info("GET request for user: "+userName);
		String msg = "success";
		HttpStatus status = HttpStatus.CREATED;
		Person person = personService.findByUserName(userName);
		if (person == null) {
			msg = "Error - person not found";
			status = HttpStatus.NOT_ACCEPTABLE;
		} else {
			JSONObject jsonPerson = new JSONObject();	
			jsonPerson.put("userName", userName);
			jsonPerson.put("fName", person.getfName());
			jsonPerson.put("lName", person.getlName());
			jsonPerson.put("id", person.getId());
			msg = jsonPerson.toString();
		}
		
		return new ResponseEntity<String>(msg, status);	

	}
}
