package het.springapp.service;

import het.springapp.model.Person;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface PersonService {
	public Person findByPersonId(String userId);
	
	//public List<Person> findPersonsByType(String personType);
	
	//public List<Person> findAll();
	
	//public String findByUserName(String uname);
	
	//public Person findByFirstName(String fname);
	
	//public Person findByLastName(String lname);
	
	public void create(Person person);
	
	public void update(Person person);
	
	public void delete(Person person);
}
