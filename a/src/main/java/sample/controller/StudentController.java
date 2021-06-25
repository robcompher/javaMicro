package sample.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sample.entity.Student;
import sample.error.APIErrorException;
import sample.repository.SudentRepository;
import sample.restModels.StudentRestModel;
@RestController
public class StudentController {

	@Autowired
	SudentRepository studentRepository;
	
	@PostMapping(value = "/employees",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	ResponseEntity<Student> all(@RequestBody StudentRestModel model) {
		if (model.getFirstName() == null) {
			throw new APIErrorException("First name is not there");
		}
		Student s = new Student();
		s.setFirstName(model.getFirstName());
		s.setLastName(model.getLastName());
		Student st = studentRepository.save(s);
	  return new ResponseEntity<>(st,HttpStatus.OK);
	}
}
