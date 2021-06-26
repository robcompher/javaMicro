package sample.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Mono;
import sample.StartApplication;
import sample.entity.Person;
import sample.restModels.PersonRestModel;
import sample.restModels.PersonsRestModel;
import sample.services.PersonService;

@ActiveProfiles("test")
@Transactional
@Rollback
@SpringBootTest(classes = StartApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonControllerTest {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	PersonService personService;

	@Test
	void init() {
		Assertions.assertNotNull(personService);
		Assertions.assertNotNull(webTestClient);
	}

	@Test
	void testAdd() {
		Person p = new Person();
		p.setId(1L);
		p.setFirstName("TEST");
		p.setLastName("TEST");
		Mockito.when(personService.add(Mockito.anyList())).thenReturn(Collections.singletonList(p));
		PersonsRestModel prm = new PersonsRestModel();
		PersonRestModel pr = new PersonRestModel();
		pr.setFirstName("TEST");
		prm.setPerson(Collections.singletonList(pr));
		webTestClient.post().uri("/person").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).body(Mono.just(prm), PersonsRestModel.class).exchange()
				.expectStatus().isOk().expectBody(Object.class).returnResult();
	}

	@Test
	void testGet() {
		Person p = new Person();
		p.setId(1L);
		p.setFirstName("TEST");
		p.setLastName("TEST");
		Mockito.when(personService.getById(Mockito.any())).thenReturn(Optional.of(p));
		PersonRestModel pr = new PersonRestModel();
		pr.setId(1L);
		EntityExchangeResult<Person> person = webTestClient.post().uri("/person/byId")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.body(Mono.just(pr), PersonRestModel.class).exchange().expectStatus().isOk().expectBody(Person.class)
				.returnResult();

		Assertions.assertEquals(p.getFirstName(), person.getResponseBody().getFirstName());
	}
}
