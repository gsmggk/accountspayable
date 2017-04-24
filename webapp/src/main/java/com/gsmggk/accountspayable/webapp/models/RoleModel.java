package com.gsmggk.accountspayable.webapp.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RoleModel {
	@NotNull
	@Size(min=5, max=30,message = "Role name must be on 5 to 30!")
	private String roleName;
	private Integer id;
	 
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
