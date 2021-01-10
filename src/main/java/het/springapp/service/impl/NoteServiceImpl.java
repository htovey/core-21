package het.springapp.service.impl;

import het.springapp.model.Note;
import het.springapp.dao.NoteDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.internal.util.EntityPrinter;

import java.util.Date;
import java.util.List;

import het.springapp.model.Note;
import het.springapp.service.NoteService;

@Service("noteService")

public class NoteServiceImpl implements NoteService {
	
	@Autowired
	NoteDao noteDao;

	
	public NoteServiceImpl(NoteDao noteDao) {
		this.noteDao = noteDao;
	}
	
	public NoteServiceImpl() {}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void create(Note note, String userId) {
		note.setCreateDate(new Date(System.currentTimeMillis()));
		note.setSaveDate(new Date(System.currentTimeMillis()));
		note.setNoteId(null);
		note.setUserId(userId);
		noteDao.persistNote(note);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public Note findByNoteId(Integer noteId) {
		return noteDao.findNoteById(noteId);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public List<Note> findNotesByPerson(String userId) {
		return noteDao.findNotesByPerson(userId);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void update(Note note, String userId) {
		note.setUserId(userId);
		note.setSaveDate(new Date(System.currentTimeMillis()));
		noteDao.updateNote(note);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void delete(Integer noteId) {
		noteDao.deleteNote(noteId);
	}

}
