package het.springapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import het.springapp.model.Role;
import het.springapp.model.User;
import het.springapp.service.RoleService;
import het.springapp.service.UserService;

@RestController
@RequestMapping(path="/user")
public class UserController {

	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;
	
	@RequestMapping(path = "/create", method = RequestMethod.POST, consumes="application/json", produces="application/json")
	ResponseEntity<String> createUser(@RequestBody User user, HttpServletRequest request) {
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
	
	@RequestMapping(path = "/update", method = RequestMethod.POST, consumes="application/json", produces="application/json")
	ResponseEntity<String> updateUser(@RequestBody User user, HttpServletRequest request) {
		String msg = "success";
		HttpStatus status = HttpStatus.OK;
		try {
			userService.update(user);
		} catch(HibernateException ex) {
			msg = "ERROR: "+ex.getCause();
			status = HttpStatus.BAD_REQUEST;
		}
		
		return new ResponseEntity<String>(msg, status);				
	}
	
	@RequestMapping(method = RequestMethod.GET, consumes="application/json", produces="application/json")
	ResponseEntity<String> getUser(@RequestParam String userName) throws JSONException {
		String msg = "success";
		HttpStatus status = HttpStatus.CREATED;
		User user = userService.findByUserName(userName);
		if (user == null) {
			msg = "Error - user not found";
			status = HttpStatus.NOT_ACCEPTABLE;
		} else {
			JSONObject jsonUser = new JSONObject();	
			Role userRole = roleService.findRoleById(user.getRoleId());
			jsonUser.put("userName", userName);
			jsonUser.put("roleType", userRole.getType());
			jsonUser.put("roleId", userRole.getId());
			jsonUser.put("roleName", userRole.getName());
			jsonUser.put("userId", user.getId());
			jsonUser.put("bizId", user.getBizId());
			msg = jsonUser.toString();
		}
		
		return new ResponseEntity<String>(msg, status);	
	}
}
