package het.springapp.dao;

import java.util.List;

import het.springapp.model.Note;
import het.springapp.model.Person;

public interface PersonDao {
	void persistPerson(Person person);
	  
	Person findPersonById(String id);
	
	void updatePerson(Person person);
	  
	void deletePerson(Person person);

	List<Person> findPersonsByAdminId(String adminId);

	Person findPersonByUserName(String userName);
}
