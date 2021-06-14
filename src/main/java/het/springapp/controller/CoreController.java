package het.springapp.controller;

import het.springapp.model.Note;
import het.springapp.model.Person;
import het.springapp.model.Role;
import het.springapp.model.User;
import het.springapp.security.JwtTokenUtil;
import het.springapp.service.NoteService;
import het.springapp.service.PersonService;
import het.springapp.service.RoleService;
import het.springapp.service.UserService;
import het.springapp.types.RoleType;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CoreController {

	private NoteService noteService;
	private PersonService personService;
	private UserService userService;
	private RoleService roleService;
	private JwtTokenUtil jwtTokenUtil;

	public final Log log = LogFactory.getLog(CoreController.class);
	@Autowired
	public CoreController(
			NoteService noteService, 
			PersonService personService, 
			RoleService roleService,
			UserService userService,
			JwtTokenUtil jwtTokenUtil
			) {
		this.noteService = noteService;
		this.personService = personService;
		this.roleService = roleService;
		this.userService = userService;
		this.jwtTokenUtil = jwtTokenUtil;
	}
	
	@GetMapping(value = "/requestReset", produces="application/json")
    public ResponseEntity <String> requestPasswordReset(
    		@RequestParam String userName, 
    		HttpServletRequest request) throws JSONException {
		HttpStatus status = HttpStatus.OK;
		String respString = "";
		User user = userService.findByUserName(userName);
		
		if (user == null) {
			respString =  "error";
			status = HttpStatus.FORBIDDEN;
		} else {
			JSONObject jsonUser = new JSONObject();
			jsonUser.put("userName", userName);
			jsonUser.put("roleId", user.getRoleId());
			jsonUser.put("userId", user.getId());
			jsonUser.put("bizId", user.getBizId());
			respString = jsonUser.toString();
		}
		
		return new ResponseEntity<String>(respString, status);
	}
	
	@PostMapping(value = "/reset", produces="application/json")
	public ResponseEntity <String> resetPassword(@RequestBody User user, HttpServletRequest request) {
		String respStatus = "success";
		HttpStatus status = HttpStatus.OK;
		if (StringUtils.isEmpty(user.getUserName())) {
			respStatus = "error - no username in request.";
			status = HttpStatus.BAD_REQUEST;
		} else {
			userService.resetPassword(user);
		}
		
		return new ResponseEntity<String>(respStatus, status);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET, produces="application/json")
    public ResponseEntity <String> login(HttpServletRequest request) throws JSONException {
		String userName = getUserName(request);
		log.info("Login request for user "+userName);
		String respStatus = "success";
		MultiValueMap<String, String> headers = new HttpHeaders();
		HttpStatus status = HttpStatus.OK;
		JSONObject json = new JSONObject();
		
		if (userName == null) {
			respStatus =  "error - user not found.";
			status = HttpStatus.FORBIDDEN;
		} else {
			User user = userService.findByUserName(userName);
			Person person = personService.findByUserName(user.getUserName());
 			Role role = roleService.findRoleById(user.getRoleId());
 			headers.add(HttpHeaders.AUTHORIZATION,
                    jwtTokenUtil.generateAccessToken(user));
			JSONObject jsonUser = new JSONObject();
			JSONObject jsonPerson = new JSONObject();
			
			if (user != null) {
				jsonUser.put("userName", userName);
				jsonUser.put("roleType", role.getType());
				jsonUser.put("roleName", role.getName());
				jsonUser.put("roleId", user.getRoleId());
				jsonUser.put("userId", user.getId());
				jsonUser.put("bizId", user.getBizId());
				jsonUser.put("adminId", user.getAdminId());
				json.put("user", jsonUser);
			}
			
			if (person != null) {
				jsonPerson.put("id", person.getId());
				jsonPerson.put("fName", person.getfName());
				jsonPerson.put("lName", person.getlName());
				jsonPerson.put("userId", person.getUserName());
				json.put("person", jsonPerson);
			}
		}
		
		json.put("responseStatus", respStatus);
		
		return new ResponseEntity<String>(json.toString(), headers, status);		
	}
	
	@RequestMapping(value = "/notes", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody List<Map<String, String>>notes(HttpServletRequest request) {
		String userId = getUserName(request);
		log.info("getting notes for "+userId);
		List<Note> noteListFromDb = noteService.findNotesByPerson(userId);
		
		List<Map<String,String>> noteList = new ArrayList<Map<String,String>>();
		
		for (Note note : noteListFromDb) {
			Map<String, String> noteMap = new HashMap<String, String>();
			noteMap.put("noteId", String.valueOf(note.getNoteId()));
			noteMap.put("category", note.getCategory());
			noteMap.put("noteText", note.getNoteText());
			noteMap.put("lastUpdated", note.getSaveDate().toString());
			noteList.add(noteMap);
		}
		
		return noteList;
	}
	
	@RequestMapping(value = "/note", method = RequestMethod.POST, produces="text/html", consumes="application/json")
    public @ResponseBody String note(@RequestBody Note note, HttpServletRequest request) {
		String userId = getUserName(request);
		
		if (note.getNoteId() == null) {
			//new note
			log.info("attempting to create "+note.getCategory()+" note for: "+userId);
			noteService.create(note, userId);
			return "success";
		} else {
			log.info("attempting to update note for: "+userId);
			noteService.update(note, userId);
			return "success";
		}
				
	}
	
	private String getUserName(HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		String userName = principal.getName();
		return userName;
	}
}