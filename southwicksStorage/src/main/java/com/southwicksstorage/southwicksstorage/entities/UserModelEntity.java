package com.southwicksstorage.southwicksstorage.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.southwicksstorage.southwicksstorage.constants.Roles;

@Entity
@Table(name = "users")
public class UserModelEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@NotEmpty(message = "First name can not be empty")
	@Size(max = 45, message = "First name can not be greater than 45 characters")
	@Column(name = "firstName")
	private String firstName;
	
	@NotEmpty(message = "Last name can not be empty")
	@Size(max = 45, message = "Last name can not be greater than 45 charcters")
	@Column(name = "lastName")
	private String lastName;
	
	@NotEmpty(message = "Username can not be empty")
	@Size(max = 30, message = "Username can not exceed 30 characters")
	@Column(name = "username")
	private String username;
	
	@NotNull(message = "Password can not be empty")
	@Column(name = "password")
	private String password;
	
	@Column(name = "role")
	private Roles role;
	
	public UserModelEntity() {
		/* Default Constructor */
	}
	
	public UserModelEntity(String firstName, String lastName, String username, String password, Roles role) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}
	
}
