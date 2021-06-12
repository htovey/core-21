package het.springapp.biz.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import het.springapp.biz.dao.BizDao;
import het.springapp.biz.model.Biz;
import het.springapp.model.Note;

@Repository("bizDao")
public class BizDaoImpl implements BizDao {

	@Autowired
	private EntityManager manager;
	
	public void persistBiz(Biz biz) {
		getSession().persist(biz);
	}

	public Biz findBizById(Integer id) {
		return (Biz) getSession().get(Biz.class, id);
	}

	public void updateBiz(Biz biz) {
		//getSession().update("Biz", biz);
		getSession().saveOrUpdate(biz);
	}
	
	public void deleteBiz(Integer id) {
		Biz biz = findBizById(id);
		getSession().delete(biz);

	}
	
	//TODO: research this further

//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public List<Biz> findBizsByPerson(String userId) {
//		Session session = getSession();
//		Query query = session.getNamedQuery("Biz.findBizsByPerson");
//		query.setParameter("USER_ID", userId);
//		List<Biz> bizList = query.list();
//		return bizList;
//	}
	
	private Session getSession() {
		return manager.unwrap(Session.class);
	}

	public List<Biz> findAll() {
		Session session = getSession();
		Query query = session.getNamedQuery("Biz.findAll");
		List<Biz> bizList = query.list();
		return bizList;
	}

	public int findBizByName(String name) {
		//this method is only used to check for an existing business by the given name
		Session session = getSession();
		Query query = session.getNamedQuery("Biz.findBizByName");
		query.setParameter("name", name);
		return query.getFirstResult();
	}

}
