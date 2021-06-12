package het.springapp.biz.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import het.springapp.biz.model.Biz;
import het.springapp.biz.service.BizService;
import het.springapp.model.Note;

@RestController
@RequestMapping(path="/biz")
public class BizController {

	@Autowired
	BizService bizService;
	
	public final Log log = LogFactory.getLog(BizController.class);
	
	@RequestMapping(path = "/create", method = RequestMethod.POST, consumes="application/json", produces="application/json")
	ResponseEntity <String> createBiz(@RequestBody Biz biz, HttpServletRequest request) {
		String msg = "success";
		HttpStatus status = HttpStatus.CREATED;
		if (bizService.findBizByName(biz.getName()) > 0) {
			msg = "Error - business with requested name already exists";
			status = HttpStatus.CONFLICT;
		} else {
			bizService.create(biz);
		}
		
		return new ResponseEntity<String>(msg, status);				
	}
	
	@RequestMapping(path = "/update", method = RequestMethod.POST, consumes="application/json", produces="application/json")
	ResponseEntity <String> updateBiz(@RequestBody Biz biz, HttpServletRequest request) {
		String msg = "success";
		HttpStatus status = HttpStatus.OK;
		
		try {
			bizService.update(biz);
		} catch(Exception ex) {
			status = HttpStatus.NOT_ACCEPTABLE;
			msg = "Attempt to update Biz failed.  Please try again.";
		}
		
		return new ResponseEntity<String>(msg, status);				
	}
	
	@RequestMapping(value = "/bizList", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody List<Map<String, String>>bizList(HttpServletRequest request) {
		//String userId = getUserName(request);
		//log.info("getting notes for "+userId);
		List<Biz> bizListFromDb = bizService.findAll();
		
		List<Map<String,String>> bizList = new ArrayList<Map<String,String>>();
		
		for (Biz biz : bizListFromDb) {
			Map<String, String> bizMap = new HashMap<String, String>();
			bizMap.put("id", String.valueOf(biz.getId()));
			bizMap.put("name", biz.getName());
			bizMap.put("type", biz.getType());
			bizMap.put("saveDate", biz.getSaveDate().toString());
			bizList.add(bizMap);
		}
		
		return bizList;
	}

}
