package com.gsmggk.accountspayable.webapp.models;

public class DebtorStateModel {
	 private Integer id;
     private String shortName;
     private String fullName;
     private String active;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String state) {
		this.active = state;
	}
     
     
}
