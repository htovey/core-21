package het.springapp.dao.impl;

import het.springapp.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import het.springapp.dao.UserDao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.query.Query;
import org.hibernate.Session;

@Repository("userDao")
public class UserDaoImpl implements UserDao {

    @Autowired(required = true)
    private EntityManager manager;
    
   // @Autowired
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    public User findByUserName(String userName) {
       Query query = getSession().getNamedQuery("User.findUser");
       query.setParameter("user_name", userName);
       User user = (User) query.uniqueResult();
       manager.close();
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
		Query query = getSession().getNamedQuery("User.updateUser");
		query.setParameter("id", user.getId());
		query.setParameter("user_name", user.getUserName());
		query.setParameter("role_id", user.getRoleId());
		query.executeUpdate();
	}
	
	@Override
	public void updatePassword(String userName, String password) {
		Query query = getSession().getNamedQuery("User.updatePassword");
		query.setParameter("user_name", userName);
		query.setParameter("password", passwordEncoder.encode(password));
		query.executeUpdate();
	}

	@Override
	public void deleteUser(String userName) {
		//TODO this op will fail with Person table key constraint
		User user = findByUserName(userName);
		getSession().delete(user);
	}

	@Override
	public List<User> findUsersByBizId(int bizId) {
		Query query = getSession().getNamedQuery("User.findUsersByBizId");
	    query.setParameter("bizId", bizId);
	    List<User> userList = query.list();
	    return userList;
	}

	@Override
	public List<User> findUsersByBizIdRoleType(int bizId, String roleType) {
		Query query = getSession().getNamedQuery("User.findUsersByBizIdRoleType");
	    query.setParameter("bizId", bizId);
	    query.setParameter("roleType", roleType);
	    List<User> userList = query.list();
	    return userList;
	}

	@Override
	public User getUser(String id) {
		return getSession().find(User.class, id);
	}
}
