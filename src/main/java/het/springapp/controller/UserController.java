package het.springapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import het.springapp.model.User;
import het.springapp.service.UserService;

@RestController
@RequestMapping(path="/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@RequestMapping(path = "/create", method = RequestMethod.POST, consumes="application/json", produces="application/json")
	ResponseEntity <String> createUser(@RequestBody User user, HttpServletRequest request) {
		String msg = "success";
		HttpStatus status = HttpStatus.CREATED;
		if (userService.findByUserName(user.getUserName()) != null) {
			msg = "Error - user name already exists";
			status = HttpStatus.CONFLICT;
		} else {
			userService.create(user);
		}
		
		return new ResponseEntity<String>(msg, status);				
	}
}
