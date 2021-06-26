package sample.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import sample.entity.Person;
import sample.repository.PersonRepository;
import sample.services.PersonService;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

	@InjectMocks
	PersonService personService;
	@Mock PersonRepository personRepository;
	Person person;
	
	@Test
	void init() {
		Assertions.assertNotNull(personService);
		Assertions.assertNotNull(personRepository);
	}
	
	@BeforeEach
	void createEntity() {
		person = new Person();
		person.setId(1L);
		person.setFirstName("TEST");
		person.setLastName("TEST");
		person.setActive(Boolean.TRUE);
	}
	
	void testAdd() {
		Mockito.when(personRepository.save(Mockito.any())).thenReturn(person);
		List<Person> p2= personService.add(Collections.singletonList(person));
		Assertions.assertEquals(1, p2.size());
		Assertions.assertEquals(person.getFirstName(), p2.get(0).getFirstName());
	}
	
	void testGet() {
		Mockito.when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));
		Person p2 = new Person();
		p2.setId(person.getId());
		Optional<Person> opt= personService.getById(1L);
		Assertions.assertTrue(opt.isPresent());
		Assertions.assertEquals(person.getFirstName(), opt.get().getFirstName());
	}

}
