package het.springapp.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
	public User findByUserName(String userName) {
		User user = userDao.findByUserName(userName);
		return user;
	}

	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void create(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
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
	public List<User> findUsersByBizIdRoleType(int bizId, String roleType) {
		return userDao.findUsersByBizIdRoleType(bizId, roleType);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	@Override
	public void resetPassword(User user) {
		userDao.updatePassword(user.getUserName(), user.getPassword());
	}

	@Transactional
	@Override
	public User getUser(String userId) {
		return userDao.getUser(userId);
	}
}
