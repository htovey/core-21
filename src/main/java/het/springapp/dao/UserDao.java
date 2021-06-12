package het.springapp.dao;

import het.springapp.model.User;
import java.util.List;

public interface UserDao {
    User findByUserName(String userName);
    
    User getUser(String id);
    
	void persistUser(User user);
	
	void updateUser(User user);
	
	void deleteUser(String userName);

	List<User> findUsersByBizId(int bizId);

	List<User> findUsersByBizIdRoleType(int bizId, String roleType);

	void updatePassword(String userName, String password);
}
