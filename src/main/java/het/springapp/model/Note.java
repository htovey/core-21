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
@Table(name = "note")

@NamedQueries({
	@NamedQuery(name="Note.findNotesByPerson", query="SELECT n FROM Note n WHERE n.userId = :USER_ID")
})

public class Note implements Serializable {
	
	private static final long serialVersionUID = 7746627867118059647L;
	
	  	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	  	@Column(name = "NOTE_ID")
	    private Integer noteId;
	    @Column(name = "TEXT")
	    private String noteText;
	    @Column(name = "USER_ID")
	    private String userId;
	    @Column(name="CREATE_DT", updatable = false)
	    private Date createDate;
	  	@Column(name="SAVE_DT")
	    private Date saveDate;
	  	@Column(name="CATEGORY")
	  	private String category;
	   
	  	public Note () {}
	  	
	  	public Note (String[] values) {
	  		 
	  		Log log = LogFactory.getLog(Note.class);
	  		setNoteText(values[1].toString());
	  		setUserId(values[2].toString());
	  		setCategory(values[5].toString());
	  		setNoteId(Integer.parseInt(values[6].toString()));
	  		log.info("converting note for "+values[2].toString());
	  		
	  	}
	  	
	    public Integer getNoteId() {
	        return noteId;
	    }
	    
	    public void setNoteId(Integer noteId) {
	    	this.noteId = noteId;
	    }

		public String getNoteText() {
			return noteText;
		}

		public void setNoteText(String noteText) {
			this.noteText = noteText;
		}

		
		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
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

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}
}
