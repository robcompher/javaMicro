package sample.controller;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sample.entity.Person;
import sample.restModels.PersonRestModel;
import sample.restModels.PersonWithPagination;
import sample.restModels.PersonsRestModel;
import sample.services.PersonService;

/**
 * End points to add/update/get/delete/find person
 * 
 * @author comph
 *
 */

@RestController
public class PersonController {

	@Autowired
	PersonService personService;

	/**
	 * Adds a person to the database
	 * Will return a 422 error if not found
	 * @param model DTO of iist of people to add
	 * @return database Entity
	 */
	@PostMapping(value = "/person", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	ResponseEntity<List<Person>> add(@RequestBody PersonsRestModel model) {
		if (Objects.isNull(model)) {
			return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NO_CONTENT);
		}
		List<Person> persons = model.getPersons().stream().map(person -> {
			Person s = new Person();
			s.setFirstName(person.getFirstName());
			s.setLastName(person.getFirstName());
			s.setActive(person.isActive() != null ? person.isActive() : true);
			s.setId(null);
			return s;
		}).collect(Collectors.toList());
		personService.validateAddUpdate(persons);
		List<Person> st = personService.add(persons);
		return new ResponseEntity<>(st, HttpStatus.OK);
	}

	/**
	 * Update people to the database
	 * @param model DTO of iist of people to add
	 * @return database Entity
	 */
	@PutMapping(value = "/person", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	ResponseEntity<List<Person>> update(@RequestBody PersonsRestModel model) {
		if (Objects.isNull(model)) {
			return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NO_CONTENT);
		}
		List<Person> persons = model.getPersons().stream().map(person -> {
			Person s = new Person();
			s.setFirstName(person.getFirstName());
			s.setLastName(person.getFirstName());
			s.setActive(person.isActive() != null ? person.isActive() : true);
			s.setId(person.getId());
			return s;
		}).collect(Collectors.toList());
		personService.validateAddUpdate(persons);
		List<Person> st = personService.update(persons);
		return new ResponseEntity<>(st, HttpStatus.OK);
	}

	/**
	 * Find list of people right now by first name and id
	 * Can do pagination where tell what page and how many people to rerurn
	 *  and the sort by and sort order
	 * @param model
	 * @return
	 */
	@PostMapping(value = "/person/find", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	ResponseEntity<Page<Person>> update(@RequestBody PersonWithPagination model) {

		Person s = new Person();
		s.setFirstName(model.getFirstName());
		s.setLastName(model.getFirstName());
		s.setActive(model.isActive());
		s.setId(model.getId());
		Page<Person> st = personService.getPersons(s, getPageable(model));
		return new ResponseEntity<>(st, HttpStatus.OK);
	}

	/**
	 * Find a specific person by ID
	 * Will return 404 if not found
	 * @param model
	 * @return
	 */
	@PostMapping(value = "/person/byId", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	ResponseEntity<Person> byId(@RequestBody PersonRestModel model) {
		if (Objects.isNull(model) || Objects.isNull(model.getId())) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		Optional<Person> opt = personService.getById(model.getId());
		return opt.isPresent() ? new ResponseEntity<>(opt.get(), HttpStatus.OK)
				: new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

	}

	@PostMapping(value = "/person/delete", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	ResponseEntity<Person> deleteById(@RequestBody Person model) {
		if (Objects.isNull(model) || Objects.isNull(model.getId())) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		Optional<Person> opt = personService.deleteById(model.getId());
		return opt.isPresent() ? new ResponseEntity<>(opt.get(), HttpStatus.OK)
				: new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

	}

	/**
	 * Coverage pagination object to a database pageable object
	 * @param model
	 * @return
	 */
	private Pageable getPageable(PersonWithPagination model) {
		PageRequest pageRequest;
		Integer page;
		Integer pageSize;
		String sortBy;
		if (model.getPagination() != null) {
			page = model.getPagination().getPage() != null ? model.getPagination().getPage() : 0;
			pageSize = model.getPagination().getPageSize() != null ? model.getPagination().getPageSize() : 10;
			sortBy = model.getPagination().getSortBy() != null ? model.getPagination().getSortBy() : "id";
			Sort.Direction direction = model.getPagination().getSortOrder() != null
					? Direction.fromString(model.getPagination().getSortBy())
					: Direction.ASC;
			pageRequest = PageRequest.of(page, pageSize, direction, sortBy);
		} else {
			pageRequest = PageRequest.of(0, 10);
		}
		return pageRequest;

	}
}
