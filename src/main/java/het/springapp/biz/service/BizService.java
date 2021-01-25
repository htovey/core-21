package het.springapp.biz.service;

import java.sql.SQLException;
import java.util.List;
import het.springapp.biz.model.Biz;

public interface BizService {

	public Biz findByBizId(Integer id);
	
	public int findBizByName(String name);
	
	public void create(Biz biz);
	
	public void update(Biz biz) throws SQLException;
	
	public void delete(Integer id);
	
	public List<Biz>findAll();
	
}
