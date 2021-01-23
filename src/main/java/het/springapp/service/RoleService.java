package het.springapp.service;

import java.util.List;

import het.springapp.model.Role;

public interface RoleService {
	List<Role> findRolesByBizType(String bizType);
}
