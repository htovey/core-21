package het.springapp.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import het.springapp.dao.PersonDao;
import het.springapp.model.Person;

@Repository("personDao")
public class PersonDaoImpl implements PersonDao {

	@Autowired
	private EntityManager manager;

	public void persistPerson(Person person) {
		getSession().persist(person);
		manager.close();
	}

	public Person findPersonById(String id) {
		Person person = getSession().get(Person.class, id);
		manager.close();
		return person;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Person> findPersonsByAdminId(String adminId) {
		Session session = getSession();
		Query query = session.getNamedQuery("Person.findPersonsByAdminId");
		query.setParameter("admin_id", adminId);
		List<Person> personList = query.list();
		manager.close();
		return personList;
}

	public void updatePerson(Person person) {
		getSession().update(person);
		manager.close();
	}
	public void deletePerson(Person person) {
		getSession().delete(person);
		manager.close();
	}
	
	private Session getSession() {
		return manager.unwrap(Session.class);
	}

	@Override
	public Person findPersonByUserName(String userName) {
		Session session = getSession();
		Query query = session.getNamedQuery("Person.findPersonByUserName");
		query.setParameter("user_name", userName);
		Person person = (Person) query.getSingleResult();
		manager.close();
		return person;
	}
	
//	private EntityManager em;
//	
//	public EntityManager getEM() {
//		return em;
//	}
//	
//	@PersistenceContext
//	public void setEm(EntityManager em) {
//		this.em = em;
//	}
//
//	public Person findByPersonId(int personId) {
//		return em.find(Person.class, personId);
//	}
//	
//	public List<Person> findAll() {
//		CriteriaBuilder cb = em.getCriteriaBuilder();
//		CriteriaQuery<Person> criteria = cb.createQuery(Person.class);
//		Root<Person> person = criteria.from(Person.class);
//		
//		criteria.select(person).orderBy(cb.asc(person.get("lastName")));
//		return em.createQuery(criteria).getResultList();
//	}
//	
//	public List<Person> findPersonsByType(String personType) {
//		Query personsByType = em.createNamedQuery("Person.findByType");
//		personsByType.setParameter("personType", personType);
//		return personsByType.getResultList();
//	}
//	
//
//	public Person findByLastName(String lname) {
//		return em.find(Person.class, lname);
//	}
//
//	public Person findByFirstName(String fname) {
//		return em.find(Person.class, fname);
//	}
//
//	public void create(Person person) {
//		em.persist(person);
//	}
//
//	public void update(int personId, Person person) {
//		Person tempPerson = findByPersonId(personId);
//		tempPerson.setFirstName(person.getFirstName());
//		tempPerson.setLastName(person.getLastName());
//		tempPerson.setPersonType(person.getPersonType());
//		em.persist(tempPerson);
//	}
//
//	public void delete(int personId) {
//		Person person = findByPersonId(personId);
//		em.remove(person);
//	}
//
//	@Override
//	public String findByUserName(String uname) {
//		String user = "";
//		Person foundPerson = em.find(Person.class, uname);
//		if (foundPerson != null) {
//			user = foundPerson.getFirstName();
//		}
//		return user;
//	}

}
