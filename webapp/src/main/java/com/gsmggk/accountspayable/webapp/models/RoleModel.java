package com.gsmggk.accountspayable.webapp.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RoleModel {
	@NotNull(message="Role name must be not null")
	@Size(min=5, max=30,message = "Role name must be on 5 to 30")
	private String roleName;
	@NotNull(message="Role layer must be not null")
	@Pattern(regexp = "WORK|BOSS|ADMIN", flags = Pattern.Flag.CASE_INSENSITIVE,message = "Role layer must be WORK, BOSS or ADMIN")
	private String layer;
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

	public String getLayer() {
		return layer;
	}

	public void setLayer(String layer) {
		this.layer = layer;
	}

}
