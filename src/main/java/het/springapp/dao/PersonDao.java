package het.springapp.dao;

import java.util.List;

import het.springapp.model.Note;
import het.springapp.model.Person;

public interface PersonDao {
	void persistPerson(Person person);
	  
	Person findPersonById(String personId);
	
	Person findPersonByUserName(String userName);
	  
	void updatePerson(Person person);
	  
	void deletePerson(Person person);

	List<Person> findPersonsByAdminId(String adminId);
}
