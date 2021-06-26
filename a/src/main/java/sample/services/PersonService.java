package sample.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import sample.entity.Person;
import sample.error.APIErrorException;
import sample.repository.PersonRepository;
import sample.repository.PersonSpecification;

/**
 * Service layer to add/update/delete/get/find people
 * @author comph
 *
 */
@Service
public class PersonService {

	@Autowired
	PersonRepository repo;

	public void validateAddUpdate(List<Person> persons) {
		persons.stream().filter(
				person -> ObjectUtils.isEmpty(person.getLastName()) || ObjectUtils.isEmpty(person.getLastName()))
				.findFirst().ifPresent(s -> {
					throw new APIErrorException("First or Last Name is blank");
				});
	}

	public List<Person> add(List<Person> persons) {
		return repo.saveAll(persons);
	}
	
	public List<Person> update(List<Person> persons) {

		
		return repo.saveAll(persons);
	}
	
	public Page<Person> getPersons(Person person, Pageable page) {
		Specification<Person> spec = PersonSpecification.getSpec(person);
		return repo.findAll(spec, page);
	}
	
	public Optional<Person> getById(Long id) {
		return repo.findById(id);
	}
	
	public Optional<Person> deleteById(Long id) {
		Optional<Person> person = repo.findById(id);
		if (person.isPresent()) {
			person.get().setActive(Boolean.FALSE);
			person = Optional.of(repo.save(person.get()));
		}
		return person;
	}

}
