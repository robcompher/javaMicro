package sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sample.entity.Student;

@Repository
public interface SudentRepository extends JpaRepository<Student, Long>{

}
