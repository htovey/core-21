package het.springapp.biz.controller;

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

}
