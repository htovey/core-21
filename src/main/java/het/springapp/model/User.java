/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package het.springapp.model;

/**
 *
 * @author heather
 */
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Entity
@Table(name = "user")

@NamedQueries({
    @NamedQuery(name="User.authenticate", query="SELECT u FROM User u WHERE u.userName = :user_name and u.password = :password"),
    @NamedQuery(name="User.findUser", query="SELECT u FROM User u WHERE u.userName = :user_name")
})

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "id", nullable = false)
    private String id;
    
    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "role_id", nullable = false)
    private int roledId;
    
    public User() {
    }
    
    public User(String [] values) {
        Log log = LogFactory.getLog(User.class);
        setUserName(values[1]);
        setPassword(values[2]);
        log.info("converting json object to user for userId "+values[0]);
    }

    public String getId() {
            return id;
    }

    public void setId(String id) {
            this.id = id;
    }
    public String getUserName() {
        return userName;
}

    public void setUserName(String userName) {
        this.userName = userName;
	}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public int getRoledId() {
		return roledId;
	}

	public void setRoledId(int roledId) {
		this.roledId = roledId;
	}
}
