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
@Table(name = "role")

@NamedQueries({
   @NamedQuery(name="User.authenticate", query="SELECT u FROM User u WHERE u.roleName = :role_name and u.bizType = :bizType"),
   @NamedQuery(name="User.findUser", query="SELECT u FROM User u WHERE u.roleName = :role_name")
})
public class Role {
   private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private String id;
    
    @Column(name = "role_name", nullable = false)
    private String roleName;

    @Column(name = "bizType", nullable = false)
    private String bizType;
    
    @Column(name = "role_id", nullable = false)
    private int roleId;
    
    @Column(name = "type")
    private String type;
    
    @Column(name = "level_id", nullable = false)
    private String levelId;
    
    @Column(name = "area_id", nullable = false)
    private String areaId;
    
    @Column(name = "create_dt", nullable = false)
    private Date createDt;
    
    @Column(name="save_dt")
    private Date saveDate;
    
    public Role() {
    }
    
    public Role(String [] values) {
        Log log = LogFactory.getLog(Role.class);
        setRoleName(values[1]);
        setType(values[2]);
        log.info("converting json object to role for id "+values[0]);
    }

    public String getId() {
            return id;
    }

    public void setId(String id) {
            this.id = id;
    }
    public String getRoleName() {
        return roleName;
	}

    public void setRoleName(String roleName) {
        this.roleName = roleName;
	}

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
 
	public String getLevelId() {
		return levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
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
