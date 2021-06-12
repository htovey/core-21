package het.springapp.service;

import java.util.List;

import het.springapp.model.Role;

public interface RoleService {
	Role findRoleById(int roleId);
	
	List<Role> findRolesByBizType(String bizType);
	
	List<Role> findRolesByBizId(int bizId);
}
