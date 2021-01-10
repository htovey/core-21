package het.springapp.dao;

import het.springapp.model.User;
import java.util.List;

public interface UserDao {
   
    public User login(String name, String pass);
    public User getUser(String userId);
}
