package het.springapp.biz.dao;

import java.sql.SQLException;
import java.util.List;

import het.springapp.biz.model.Biz;

public interface BizDao {

	void persistBiz(Biz Biz);
	  
	Biz findBizById(Integer id);
	
	int findBizByName(String name);
	  
	void updateBiz(Biz biz);
	  
	void deleteBiz(Integer id);
	
	public List<Biz> findAll();
}
