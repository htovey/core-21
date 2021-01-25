package het.springapp.service;

import java.util.List;

import het.springapp.model.User;
import het.springapp.types.RoleType;

public interface UserService {
	public User findByUserName(String userName);
	
	public void create(User user);
	
	public void update(User user);
	
	public void delete(String userName);
	
	public List<User>findUsersByAdminId(String adminId);
	
	public RoleType getRoleType(Integer roleId);
}