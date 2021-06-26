package sample.repository;


import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import sample.StartApplication;
import sample.entity.Person;



@ActiveProfiles("test")
@Transactional
@Rollback
@SpringBootTest (classes=StartApplication.class)
public class BaseRepositoryTest {
    
	@Autowired
	PersonRepository person;
	
	@Test
    void test() {
    	Assertions.assertNotNull(this);
    }
	
	@BeforeEach
	void createPerson() {
		Person p = new Person();
		p.setFirstName("TEST");
		p.setLastName("TEST");
		Person p2 = person.save(p);
		Assertions.assertNotNull(p2.getId());
		Assertions.assertEquals(p.getFirstName(), p2.getFirstName());	
	}
	
	
	@Test
	void testGet() {
		Person p = new Person();
		p.setFirstName("Test");
		List<Person> page = person.findAll(PersonSpecification.getSpec(p));
		Assertions.assertEquals(1, page.size());
		Assertions.assertEquals(p.getFirstName().toLowerCase(), page.get(0).getFirstName().toLowerCase());
	}
}
