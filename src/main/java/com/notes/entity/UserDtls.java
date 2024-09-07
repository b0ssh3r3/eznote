package com.notes.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class UserDtls {
	@Id
	@GeneratedValue
	private int id;
	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;
	@Column(name = "password")
	private String password;
	@Column(name = "about")
	private String about;
	@Column(name = "role")
	private String role;

	public UserDtls(int id, String name, String email, String password, String about, String role) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.about = about;
		this.role = role;
	}

	public UserDtls() {
		super();
	}

	public UserDtls(int id, String name, String email, String password, String about) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.about = about;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserDetails [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", about="
				+ about + ", role=" + role + "]";
	}

}
