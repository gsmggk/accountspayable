package com.gsmggk.accountspayable.datamodel;

public class Clerk extends AbstractTable {

	private String clerkLoginName;
	private String password;
	private Integer roleId;
	private String clerkFullName;

	public String getClerkLoginName() {
		return clerkLoginName;
	}

	public void setClerkLoginName(String clerkLoginName) {
		this.clerkLoginName = clerkLoginName;
	}

	public String getClerkFullName() {
		return clerkFullName;
	}

	public void setClerkFullName(String clerkFullName) {
		this.clerkFullName = clerkFullName;
	}

	
	


	@Override
	public String toString() {
		return "Clerk [clerkLoginName=" + clerkLoginName + ", password=" + password + ", roleId=" + roleId
				+ ", clerkFullName=" + clerkFullName + ", id=" + id + "]";
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
