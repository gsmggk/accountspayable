package com.gsmggk.accountspayable.webapp.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ClerkModel {
	private Integer id;
	@NotNull(message="Clerk login name not null")
	@Size(min=5, max=50,message = "Clerk login name must be on 5 to 50")
	private String clerkLoginName;
	
	private Integer roleId;
	@NotNull(message="Clerk full name not null")
	@Size(min=5, max=50,message = "Clerk full name must be on 5 to 100")
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
