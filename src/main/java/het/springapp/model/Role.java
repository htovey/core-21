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
   @NamedQuery(name="Role.findRolesByBizType", query="SELECT r FROM Role r WHERE r.bizType = :bizType"),
   @NamedQuery(name="Role.findRolesByBizId", query="SELECT r FROM Role r WHERE r.bizId = :bizId")
})
public class Role {
   private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "biz_type", nullable = false)
    private String bizType;

    @Column(name = "biz_id", nullable = false)
    private int bizId;
    
    @Column(name = "type")
    private String type;
    
    @Column(name = "level_id", nullable = false)
    private String levelId;
    
    @Column(name = "area_id", nullable = false)
    private String areaId;
    
    public Role() {
    }
    
    public Role(String [] values) {
        Log log = LogFactory.getLog(Role.class);
        setName(values[1]);
        setType(values[2]);
        log.info("converting json object to role for id "+values[0]);
    }

    public int getId() {
            return id;
    }

    public void setId(int id) {
            this.id = id;
    }
    public String getName() {
        return name;
	}

    public void setName(String name) {
        this.name = name;
	}

    public int getBizId() {
		return bizId;
	}

	public void setBizId(int bizId) {
		this.bizId = bizId;
	}

	public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
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
}
