package het.springapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import het.springapp.dao.RoleDao;
import het.springapp.model.Note;
import het.springapp.model.Role;
import het.springapp.service.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
	@Autowired
	RoleDao roleDao;
	
	public RoleServiceImpl() {}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public List<Role> findRolesByBizId(int bizId) {
		return roleDao.findRolesByBizId(bizId);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public List<Role> findRolesByBizType(String bizType) {
		return roleDao.findRolesByBizType(bizType);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public Role findRoleById(int roleId) {
		Role role = roleDao.findById(roleId);
		return role;
	}
}
