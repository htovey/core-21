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
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
    @NamedQuery(name="User.findUser", query="SELECT u FROM User u WHERE u.userName = :user_name"),
    @NamedQuery(name="User.findUsersByBizId", query="SELECT u FROM User u WHERE u.bizId = :bizId"),
    @NamedQuery(name="User.findUsersByBizIdRoleType", query="SELECT u FROM User u WHERE u.bizId = :bizId AND u.roleId IN ("
    		+ "SELECT r.id from Role r WHERE r.type = :roleType)"),
    @NamedQuery(name="User.updatePassword", query="UPDATE User u SET u.password = :password WHERE u.userName = :user_name"),
    @NamedQuery(name="User.updateUser", query="UPDATE User u SET u.userName = :user_name, u.roleId = :role_id WHERE u.id = :id")
})

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private String id;
    
    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "role_id", nullable = false)
    private int roleId;
    
    @Column(name = "admin_id")
    private int adminId;
    
    @Column(name = "biz_id")
    private int bizId;
    
    @Column(name = "create_dt", nullable = false)
    private Date createDt;
    
    @Column(name="save_dt")
    private Date saveDate;
    
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

	public int getBizId() {
		return bizId;
	}

	public void setBizId(int bizId) {
		this.bizId = bizId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public Date getSaveDate() {
		return saveDate;
	}

	public void setSaveDate(Date saveDate) {
		this.saveDate = saveDate;
	}
}
