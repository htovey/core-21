package het.springapp.biz.service.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.internal.util.EntityPrinter;

import het.springapp.biz.dao.BizDao;
import het.springapp.biz.model.Biz;
import het.springapp.biz.service.BizService;

@Service("bizService")
public class BizServiceImpl implements BizService {

	@Autowired
	BizDao bizDao;
	
	public BizServiceImpl() {}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public Biz findByBizId(Integer id) {
		return bizDao.findBizById(id);
	}

	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void create(Biz biz) {
		biz.setCreateDate(new Date(System.currentTimeMillis()));
		biz.setSaveDate(new Date(System.currentTimeMillis()));
		biz.setId(null);
		bizDao.persistBiz(biz);
	}

	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void update(Biz biz) {
		biz.setSaveDate(new Date(System.currentTimeMillis()));
		bizDao.updateBiz(biz);
	}

	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void delete(Integer id) {
		bizDao.deleteBiz(id);
	}

	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public List<Biz> findAll() {
		return bizDao.findAll();
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public int findBizByName(String name) {
		return bizDao.findBizByName(name);
	}
}
