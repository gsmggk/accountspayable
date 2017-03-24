package com.gsmggk.accountspayable.datamodel;

public class Clerk extends AbstractTable {

	
	
	

	@Override
	public String toString() {
		String result = null;
		result = "Clerk [id=" + id + ", clerkName=" + clerkName + ", password=" + password + ", roleId=" + roleId + "]";
		return result;
	}

	private String clerkName;
	private String password;
	private Integer roleId;

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
