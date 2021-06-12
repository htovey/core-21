package het.springapp.service.impl;

import het.springapp.dao.PersonDao;
import het.springapp.dao.impl.PersonDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import het.springapp.model.Person;
import het.springapp.service.PersonService;

@Service("personService")

public class PersonServiceImpl implements PersonService {

	@Autowired
	PersonDao personDao;
	
	
	public PersonServiceImpl(PersonDao personDao) {
		this.personDao = personDao;
	}
	
	public PersonServiceImpl() {}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void create(Person person) {
		person.setSaveDate(new Date(System.currentTimeMillis()));
		person.setId(null);
		personDao.persistPerson(person);
	}
	
	public Person findByPersonId(String personId) {
		return personDao.findPersonById(personId);
	}
	
	public Person findByUserName(String userName) {
		return personDao.findPersonByUserName(userName);
	}
	
	public List<Person> findPersonsByAdminId(String adminId) {
		return personDao.findPersonsByAdminId(adminId);
	}
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
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void update(Person person) {
		person.setSaveDate(new Date(System.currentTimeMillis()));
		personDao.updatePerson(person);
	}

	public void delete(Person person) {
		personDao.deletePerson(person);
	}

//	public List<Person> findAll() {
//		return personDao.findAll();
//	}

}
