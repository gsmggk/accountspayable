package com.gsmggk.accountspayable.webapp.models;

public class ClerkRepoModel {
	private Integer id;

	private String clerkFullName;
	private Integer debtors;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getClerkFullName() {
		return clerkFullName;
	}
	public void setClerkFullName(String clerkFullName) {
		this.clerkFullName = clerkFullName;
	}
	public Integer getDebtors() {
		return debtors;
	}
	public void setDebtors(Integer debtors) {
		this.debtors = debtors;
	}
	@Override
	public String toString() {
		return "ClerkRepoModel [id=" + id + ", clerkFullName=" + clerkFullName + ", debtors=" + debtors + "]";
	}
}
