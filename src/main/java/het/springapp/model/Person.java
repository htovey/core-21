package het.springapp.model;

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
@Table(name = "person")

@NamedQueries({
	//@NamedQuery(name="Person.findPersonsByAdminId", query="SELECT p FROM Person p WHERE p.adminId = :admin_id"),
	@NamedQuery(name="Person.findPersonByUserName", query="SELECT p FROM Person p where p.userName = :user_name")
})

public class Person implements Serializable {
	
	private static final long serialVersionUID = 7746627867118059647L;
	
	  	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	  	@Column(name = "id")
	    private String id;
	  	
	  	@Column(name = "user_name")
	    private String userName;
	  	
//	  	@Column(name = "admin_id")
//	    private String adminId;
	  	
	    @Column(name = "fname")
	    private String fName;
	    
	    @Column(name="lname")
	  	private String lName;
	    
//	    @Column(name="create_dt", updatable = false)
//	    private Date createDate;
	    
	  	@Column(name="save_dt")
	    private Date saveDate;
	   
	  	public Person () {}	  	
	  	
		
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
//
//		public Date getCreateDate() {
//			return createDate;
//		}
//
//		public void setCreateDate(Date createDate) {
//			this.createDate = createDate;
//		}

		public Date getSaveDate() {
			return saveDate;
		}

		public void setSaveDate(Date saveDate) {
			this.saveDate = saveDate;
		}

//		public String getAdminId() {
//			return adminId;
//		}
//
//		public void setAdminId(String adminId) {
//			this.adminId = adminId;
//		}

		public String getfName() {
			return fName;
		}

		public void setfName(String fName) {
			this.fName = fName;
		}

		public String getlName() {
			return lName;
		}

		public void setlName(String lName) {
			this.lName = lName;
		}

}
