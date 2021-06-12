/**
 * 
 */
package het.springapp.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;

import het.springapp.dao.NoteDao;
import het.springapp.model.Note;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author heather
 *
 */

@Repository("noteDao")
public class NoteDaoImpl implements NoteDao {

	@Autowired
	private EntityManager manager;
	
	public void persistNote(Note note) {
		getSession().persist(note);
	}

	public Note findNoteById(Integer noteId) {
		return (Note) getSession().get(Note.class, noteId);
	}

	public void updateNote(Note note) {
		//getSession().update("Note", note);
		getSession().saveOrUpdate(note);
	}
	public void deleteNote(Integer noteId) {
		Note note = findNoteById(noteId);
		getSession().delete(note);

	}
	
	//TODO: research this further

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Note> findNotesByPerson(String userId) {
		Session session = getSession();
		Query query = session.getNamedQuery("Note.findNotesByPerson");
		query.setParameter("USER_ID", userId);
		List<Note> noteList = query.list();
		return noteList;
	}
	
	private Session getSession() {
		return manager.unwrap(Session.class);
	}

}
