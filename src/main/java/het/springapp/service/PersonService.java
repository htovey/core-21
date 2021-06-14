package het.springapp.service;


import java.util.List;

import org.springframework.stereotype.Service;

import het.springapp.model.Person;

@Service
public interface PersonService {
	public Person findByPersonId(String id);
	
	public List<Person> findPersonsByAdminId(String adminId);
	
	//public List<Person> findAll();
	
	public Person findByUserName(String uname);
	
	//public Person findByFirstName(String fname);
	
	//public Person findByLastName(String lname);
	
	public void create(Person person);
	
	public void update(Person person);
	
	public void delete(Person person);
}
