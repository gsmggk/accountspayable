package com.gsmggk.accountspayable.webapp.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class DebtorModel {
	private Integer id;
	@NotNull(message = "Debror short name  must be not null")
	@NotEmpty(message = "Debror short name must be not empty")
	@Size(max=50,message = "Debror short name can't biger then 50")
	private String shortName;
	@NotNull(message = "Debror full name  must be not null")
	@NotEmpty(message = "Debror full name must be not empty")
	@Size(max=50,message = "Debror full name can't biger then 50")
	private String fullName;
	private String address;
	private String phones;
	private String jobe;
	private String family;
	private String other;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhones() {
		return phones;
	}
	public void setPhones(String phones) {
		this.phones = phones;
	}
	public String getJobe() {
		return jobe;
	}
	public void setJobe(String jobe) {
		this.jobe = jobe;
	}
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	
}
