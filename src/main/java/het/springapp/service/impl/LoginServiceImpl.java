package het.springapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import het.springapp.model.Person;
import het.springapp.service.LoginService;
import het.springapp.service.PersonService;
import het.springapp.dao.impl.*;
import het.springapp.model.User;
import het.springapp.dao.UserDao;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("loginService")
public class LoginServiceImpl implements LoginService {
	
    @Autowired
    private UserDao userDao;
    
    @Transactional(propagation=Propagation.REQUIRED, readOnly=false)
    public User login(String userId, String password) throws Exception {
       return userDao.login(userId, password);
    }

}
