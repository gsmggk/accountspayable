package com.gsmggk.accountspayable.webapp.models;

public class ClerkModel {
	private Integer id;
	private String clerkLoginName;
	private String password;
	private Integer roleId;
	private String clerkFullName;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getClerkLoginName() {
		return clerkLoginName;
	}
	public void setClerkLoginName(String clerkLoginName) {
		this.clerkLoginName = clerkLoginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getClerkFullName() {
		return clerkFullName;
	}
	public void setClerkFullName(String clerkFullName) {
		this.clerkFullName = clerkFullName;
	}
	
}
