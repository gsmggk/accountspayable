package com.gsmggk.accountspayable.datamodel;

public class Role extends AbstractTable {

	private String roleName;
	private String layer;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", roleName=" + roleName +  ", layer=" + layer + "]";

	}

	public String getLayer() {
		return layer;
	}

	public void setLayer(String layer) {
		this.layer = layer;
	}
}
