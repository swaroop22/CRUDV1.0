package com.paragon;

import java.io.IOException;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.paragon.models.Person;
import com.paragon.services.PersonService;

@RestController
public class PersonController {

	@Autowired
	private PersonService personService;

	/**
	 * This method is used to fetch all persons.
	 *
	 * @return
	 */
	@RequestMapping(value = "/personController", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public @ResponseBody List<Person> getAllPersons() {
		return personService.getAllPersons();
	}

	/**
	 * This method is used to get a single person based on ID.
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/personController/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public @ResponseBody Person getPerson(@PathVariable("id") Integer id) {
		return personService.getPerson(id);
	}

	/**
	 * This method is used for both update and add.
	 *
	 * @param payLoad
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/personController", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody Person addPerson(@RequestBody String payLoad)
			throws JsonParseException, JsonMappingException, IOException {
		return personService.addOrUpdatePerson(payLoad);
	}

	@RequestMapping(value = "/personController/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public @ResponseBody Person UpdatePerson(@RequestBody String payLoad)
			throws JsonParseException, JsonMappingException, IOException {
		return personService.addOrUpdatePerson(payLoad);
	}
	/**
	 * This method is used to delete the user.
	 *
	 * @parampayLoad
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/personController/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public long deletePerson(@PathVariable("id") Long id) {
		personService.deletePerson(id);
		return id;
	 }

}
