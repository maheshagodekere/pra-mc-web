package com.pradeya.cast.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class Member implements Serializable{
		
		@Id
		private long id;
	    
	    private String userName;
		private String password;
		private String firstName;
		private String lastName;
		private String role;
		private String status;
		private String lastLogin;
		
		private String address1;
		private String address2;
		private String designation;
		private String email;
		private long phone;
		
		@DBRef
		private List<Role> roles = new ArrayList<Role>();


		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
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
		public String getRole() {
			return role;
		}
		public void setRole(String role) {
			this.role = role;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getLastLogin() {
			return lastLogin;
		}
		public void setLastLogin(String lastLogin) {
			this.lastLogin = lastLogin;
		}
		public String getAddress1() {
			return address1;
		}
		public void setAddress1(String address1) {
			this.address1 = address1;
		}
		public String getAddress2() {
			return address2;
		}
		public void setAddress2(String address2) {
			this.address2 = address2;
		}
		public String getDesignation() {
			return designation;
		}
		public void setDesignation(String designation) {
			this.designation = designation;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public long getPhone() {
			return phone;
		}
		public void setPhone(long phone) {
			this.phone = phone;
		}
		public List<Role> getRoles() {
			return roles;
		}
		public void setRoles(List<Role> roles) {
			this.roles = roles;
		}
		
		public void addRole(Role role) {
			this.roles.add(role);
		}
		
		public void removeRole(Role role) {
			//use iterator to avoid java.util.ConcurrentModificationException with foreach
			for (Iterator<Role> iter = this.roles.iterator(); iter.hasNext(); )
			{
			   if (iter.next().equals(role))
			      iter.remove();
			}
		}
		
		public String fetchRolesAsCSV() {
			StringBuilder sb = new StringBuilder();
			for (Iterator<Role> iter = this.roles.iterator(); iter.hasNext(); )
			{
			   sb.append(iter.next().getId());
			   if (iter.hasNext()) {
				   sb.append(',');
			   }
			}
			return sb.toString();
		}	


}
