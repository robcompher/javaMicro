package sample.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity database that matches the liquibase creation record
 * @author comph
 *
 */
@Entity
@Table(name = "student")
public class Person implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "studentSequence", sequenceName = "studentSequence", allocationSize = 1)
	@GeneratedValue(generator = "studentSequence")
	Long id;

	@Column(name = "firstName")
	String firstName;
	
	@Column(name = "lastName", nullable = true)
	String lastName;
	
	@Column(name = "active", nullable = true)
	Boolean active;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}
