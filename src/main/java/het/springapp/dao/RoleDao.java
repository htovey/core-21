package het.springapp.dao;

import java.util.List;

import het.springapp.model.Role;

public interface RoleDao {
	Role findById(int roleId);
	
	List<Role> findRolesByBizType(String bizType);
	
	List<Role> findRolesByBizId(int bizId);
}
