package com.gsmggk.accountspayable.webapp.models;

public class DebtorControlModel {
     private Integer id;
     private String shortName;
     private String fullName;
     private String control;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getControl() {
		return control;
	}
	public void setControl(String control) {
		this.control = control;
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
     
}
