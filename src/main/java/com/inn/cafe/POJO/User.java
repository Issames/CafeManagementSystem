package com.inn.cafe.POJO;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

 
 @NamedQuery(name = "User.findByEmailId", query = "select u from User u where u.email=:email")
 
 
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "user")
public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "name")
	private String name;
	@Column(name = "contactNumber")
	private String contactNumber;
	@Column(name = "email")
	private String email;
	@Column(name = "password")
	private String password;
	@Column (name = "status")
	 private String status;
	@Column(name = "role")
	private String role;
	
	public User() {
		
	}
	public User(String name, String contactNumber, String email, String password, String status, String role) {
		this.name = name;
		this.contactNumber = contactNumber;
		this.email = email;
		this.password = password;
		this.status = status;
		this.role = role;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getname() {
		return name;
	}
	public void setname(String name) {
		this.name = name;
	}
	public String getcontactNumber() {
		return contactNumber;
	}
	public void setcontactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getemail() {
		return email;
	}
	public void setemail(String email) {
		this.email = email;
	}
	public String getpassword() {
		return password;
	}
	public void setpassword(String password) {
		this.password = password;
	}
	public String getrole(){
		return role;
	}
	public void setrole(String role) {
		this.role = role;
	}
	public String getstatus() {
		return status;
	}
	public void setstatus(String status) {
		this.status = status;
	}
	
	
	
	
	

}
