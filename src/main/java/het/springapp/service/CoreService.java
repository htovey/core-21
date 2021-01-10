package het.springapp.service;

import org.springframework.stereotype.Service;

import het.springapp.model.Note;

@Service
public interface CoreService {
	public Note saveNote() throws Exception;
}
