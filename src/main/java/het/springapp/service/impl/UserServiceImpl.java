package het.springapp.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import het.springapp.dao.UserDao;
import het.springapp.model.User;
import het.springapp.service.UserService;
import het.springapp.types.RoleType;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
	public User findByUserName(String userName) {
		User user = userDao.getUser(userName);
		return user;
	}

	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void create(User user) {
		user.setCreateDt(new Date(System.currentTimeMillis()));
		user.setId(null);
		userDao.persistUser(user);
	}

	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void update(User user) {
		userDao.updateUser(user);
	}

	@Override
	public void delete(String userName) {
		userDao.deleteUser(userName);
	}

	@Override
	public List<User> findUsersByAdminId(String adminId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RoleType getRoleType(Integer roleId) {
		return RoleType.getRoleType(roleId);
	}

}
