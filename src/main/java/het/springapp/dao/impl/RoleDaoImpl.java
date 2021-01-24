package het.springapp.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;

import het.springapp.dao.RoleDao;
import het.springapp.model.Role;

@Repository("roleDao")
public class RoleDaoImpl implements RoleDao {
	
	@Autowired
	EntityManager manager;
	
	public List<Role> findRolesByBizType(String bizType) {
		Session session = getSession();
		Query query = session.getNamedQuery("Role.findRolesByBizType");
		query.setParameter("bizType", bizType);
		List<Role> roleList = query.list();
		return roleList;
	}

	private Session getSession() {
		return manager.unwrap(Session.class);
	}
}
