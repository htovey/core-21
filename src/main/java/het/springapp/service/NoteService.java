package het.springapp.service;

import het.springapp.model.Note;
import het.springapp.model.Note;

import java.util.List;

import org.springframework.stereotype.Service;

public interface NoteService {
	public Note findByNoteId(Integer noteId);
	
	public void create(Note note, String userId);
	
	public void update(Note note, String userId);
	
	public void delete(Integer noteId);
	
	public List<Note>findNotesByPerson(String userId);
}
