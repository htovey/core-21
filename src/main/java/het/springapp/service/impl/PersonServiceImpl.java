package het.springapp.service.impl;

import het.springapp.dao.PersonDao;
import het.springapp.dao.impl.PersonDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import het.springapp.model.Person;
import het.springapp.service.PersonService;

@Service("personService")

public class PersonServiceImpl implements PersonService {

	private PersonDao personDao;
	
	@Autowired
	public PersonServiceImpl(PersonDao personDao) {
		this.personDao = personDao;
	}
	
	public PersonServiceImpl() {}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
	public void create(Person person) {
		personDao.persistPerson(person);
	}
	
	public Person findByPersonId(String userId) {
		return personDao.findPersonById(userId);
	}
	
//	public List<Person> findPersonsByType(String personType) {
//		return personDao.(personType);
//	}
//	
//	public String findByUserName(String uname) {
//		return personDao.findByUserName(uname);
//	}
//	
//	public Person findByFirstName(String fname) {
//		return personDao.findByFirstName(fname);
//	}
//
//	public Person findByLastName(String lname) {
//		return personDao.findByLastName(lname);
//	}
	
	public void update(Person person) {
		personDao.updatePerson(person);
	}

	public void delete(Person person) {
		personDao.deletePerson(person);
	}

//	public List<Person> findAll() {
//		return personDao.findAll();
//	}

}
