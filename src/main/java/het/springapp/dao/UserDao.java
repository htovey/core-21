package het.springapp.dao;

import het.springapp.model.User;
import java.util.List;

public interface UserDao {
    public User getUser(String userName);
    
	public void persistUser(User user);
	
	public void updateUser(User user);
	
	public void deleteUser(String userName);
	
	public List<User> findUsersByAdminId(String adminId);
}
