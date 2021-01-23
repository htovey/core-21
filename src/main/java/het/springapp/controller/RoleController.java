 package het.springapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import het.springapp.model.Note;
import het.springapp.model.Role;
import het.springapp.service.RoleService;

public class RoleController {
	
	private RoleService roleService;
	
	public final Log log = LogFactory.getLog(CoreController.class);
	
	@RequestMapping(value = "/roles", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody List<Map<String, String>>roles(@RequestParam String bizType, HttpServletRequest request) {
		
		List<Role> roleListData = roleService.findRolesByBizType(bizType);
		List<Map<String,String>> roleList = new ArrayList<Map<String,String>>();
		
		for (Role role : roleListData) {
			Map<String, String> roleMap = new HashMap<String, String>();
			roleMap.put("roleId", String.valueOf(role.getRoleId()));
			roleMap.put("roleType", role.getType());
			roleMap.put("roleName", role.getRoleName());
			roleList.add(roleMap);
		}
		
		return  roleList;
	}
}
