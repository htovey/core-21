package het.springapp.dao;

import java.util.List;

import het.springapp.model.Role;

public interface RoleDao {
	List<Role> findRolesByBizType(String bizType);
}
