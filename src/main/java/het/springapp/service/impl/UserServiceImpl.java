package het.springapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import het.springapp.dao.UserDao;
import het.springapp.model.User;
import het.springapp.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;
	
	@Override
	public User findByUserName(String userName) {
		User user = userDao.getUser(userName);
		return user;
	}

	@Override
	public void create(User user) {
		user.setId(null);
		userDao.persistUser(user);
	}

	@Override
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

}
