package com.paragon.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.paragon.models.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paragon.domain.PersonWrapper;
import com.paragon.models.Person;
import com.paragon.repos.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * This method is used to get all available persons.
	 * @return
	 */
	public List<Person> getAllPersons() {
		Iterable<Person> persons = personRepository.findAll();
		List<Person> personsList = new ArrayList<>();
		Iterator<Person> it = persons.iterator();
		while(it.hasNext()){
			personsList.add(it.next());
		}
		return personsList;
	}

	public Person getPerson(int id){
		return personRepository.findOne((long) id);
	}

	public Person addOrUpdatePerson(String payLoad) throws JsonParseException, JsonMappingException, IOException{

    PersonWrapper personWrapper = objectMapper.readValue(payLoad, PersonWrapper.class);
		long personId = personWrapper.getId();
		Person person = new Person();
		person.setId(personId);
		person.setFirstName(personWrapper.getFirstName());
		person.setLastName(personWrapper.getLastName());
		person.setAddress(personWrapper.getAddress());
		person.setOccupation(personWrapper.getOccupation());
		person.setAge(personWrapper.getAge());
		person.setDob(personWrapper.getDob());
		person.setMiddleName(personWrapper.getMiddleName());

    if(personWrapper.getId() == 0){
      Address address = personWrapper.getAddress();
      person.setCreatedOn(new Date());
      person.setUpdatedOn(new Date());
			Long size = personRepository.count();
			person.setId(size+1);
			address.setPerson(person);
			person.getAddressList().add(address);
		} else {
      Person newPerson = this.getPerson((int) personId);
      if (newPerson != null) {
        person.setCreatedOn(newPerson.getCreatedOn());
        person.setUpdatedOn(newPerson.getUpdatedOn());
      }
      person.setId(personWrapper.getId());

      for (Address add : personWrapper.getAddressList()) {
        add.setPerson(person);
        person.getAddressList().add(add);
      }
    }
		person = personRepository.save(person);
		return person;
	}

	public long deletePerson(long id) {
		Person person = personRepository.findOne(id);
		personRepository.delete(person);
		return id;
	}
}
