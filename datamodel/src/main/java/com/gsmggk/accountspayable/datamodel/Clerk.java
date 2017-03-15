package com.gsmggk.accountspayable.datamodel;

public class Clerk {
	private Integer id;
	private String clerkName;
	private String password;
	private Integer roleId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClerkName() {
		return clerkName;
	}

	public void setClerkName(String clerkName) {
		this.clerkName = clerkName;
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

}
