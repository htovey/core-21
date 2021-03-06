package het.springapp.controller;

import het.springapp.model.Note;
import het.springapp.model.Person;
import het.springapp.model.User;
import het.springapp.service.NoteService;
import het.springapp.service.PersonService;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
	private boolean firstLoad = false;

		
	public final Log log = LogFactory.getLog(CoreController.class);
	@Autowired
	public CoreController(NoteService noteService, PersonService personService) {
		this.noteService = noteService;
		this.personService = personService;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity <String> login(HttpServletRequest request) throws JSONException {
		String userName = getUserName(request);
		String respStatus = "success";
		HttpStatus status = HttpStatus.OK;
		JSONObject json = new JSONObject();
		
		if (userName == null) {
			respStatus =  "error";
			status = HttpStatus.FORBIDDEN;
		} else {
			Person person = personService.findByUserName(userName);
			json.put("userName", userName);
			json.put("id", person.getId());
			json.put("fName", person.getfName());
			json.put("lName", person.getlName());
		}
		
		json.put("responseStatus", respStatus);
		
		return new ResponseEntity<String>(json.toString(), status);		
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
	
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody String delete(@RequestBody String [] noteArray) {
		for (String noteId : noteArray) {
			log.info("deleting note "+noteId);
			noteService.delete(Integer.valueOf(noteId));
		}
		return "success";
	}
	
	private String getUserName(HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		String userName = principal.getName();
		return userName;
	}
}