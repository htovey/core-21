 package het.springapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import het.springapp.model.Role;
import het.springapp.service.RoleService;

@RestController
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	public final Log log = LogFactory.getLog(CoreController.class);
	
	@RequestMapping(value = "/roles", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody List<Map<String, String>>roles(@RequestParam int bizId, HttpServletRequest request) {
		log.info("Role List request for bizId "+bizId);
		List<Role> roleListData = roleService.findRolesByBizId(bizId);
		List<Map<String,String>> roleList = new ArrayList<Map<String,String>>();
		
		for (Role role : roleListData) {
			Map<String, String> roleMap = new HashMap<String, String>();
			roleMap.put("id", String.valueOf(role.getId()));
			roleMap.put("type", role.getType());
			roleMap.put("name", role.getName());
			roleList.add(roleMap);
		}
		
		return  roleList;
	}
}
