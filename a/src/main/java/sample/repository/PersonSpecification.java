package sample.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import sample.entity.Person;

public class PersonSpecification {
	
	public static Specification<Person> getSpec(Person person) {
		return (root, query, builder) -> {
		List<Predicate> predicate = new ArrayList<>();
		if (Objects.nonNull(person.getId())) {
			predicate.add(builder.and(builder.equal(root.get("id"),person.getId())));
		}
		if (Objects.nonNull(person.getFirstName())) {
			predicate.add(builder.and(builder.like(builder.lower(root.get("firstName")),"%" + person.getFirstName().toLowerCase() + "%")));
		}
		Predicate[] predicatesArray = new Predicate[predicate.size()];
		return builder.and(predicate.toArray(predicatesArray));
		};
	}

}
