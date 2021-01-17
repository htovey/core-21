package het.springapp.dao.impl;

import het.springapp.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Repository;
import het.springapp.dao.UserDao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Query;
import org.hibernate.Session;

@Repository("userDao")
public class UserDaoImpl implements UserDao {

    @Autowired(required = true)
    private EntityManager manager;
    
    public User getUser(String userName) {
       Query query = getSession().getNamedQuery("User.findUser");
       query.setParameter("user_name", userName);
       User user = (User) query.uniqueResult();
       return user;
    }
    
    private Session getSession() {
    	return manager.unwrap(Session.class);
    }

	@Override
	public void persistUser(User user) {
		getSession().persist(user);
	}

	@Override
	public void updateUser(User user) {
		getSession().saveOrUpdate(user);
	}

	@Override
	public void deleteUser(String userName) {
		User user = getUser(userName);
		getSession().delete(user);
	}

	@Override
	public List<User> findUsersByAdminId(String adminId) {
		// TODO Auto-generated method stub
		return null;
	}
}
