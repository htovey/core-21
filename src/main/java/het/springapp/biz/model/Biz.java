package het.springapp.biz.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name = "biz")

@NamedQueries({
	@NamedQuery(name="Biz.findAll", query="SELECT b FROM Biz b ORDER BY b.name"),
	@NamedQuery(name="Biz.findBizByName", query="SELECT b FROM Biz b WHERE b.name = :name")
})

public class Biz implements Serializable {
	
	private static final long serialVersionUID = 7746627867118059647L;
	
	  	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	  	@Column(name = "id")
	    private Integer id;
	  	
	    @Column(name = "type")
	    private String type;
	    
	    @Column(name = "name")
	    private String name;
	    
	    @Column(name="create_dt", updatable = false)
	    private Date createDate;
	    
	  	@Column(name="save_dt")
	    private Date saveDate;
	  	
	  	public Biz () {}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Date getCreateDate() {
			return createDate;
		}

		public void setCreateDate(Date createDate) {
			this.createDate = createDate;
		}

		public Date getSaveDate() {
			return saveDate;
		}

		public void setSaveDate(Date saveDate) {
			this.saveDate = saveDate;
		}
	  	
}
