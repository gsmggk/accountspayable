package com.gsmggk.accountspayable.datamodel;

public class Debtor extends AbstractTable {

	private String shortName;
	private String fullName;
	private String address;
	private String phones;
	private String jobe;
	private String family;
	private String other;

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
