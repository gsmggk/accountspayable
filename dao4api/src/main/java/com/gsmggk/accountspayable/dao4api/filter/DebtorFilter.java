package com.gsmggk.accountspayable.dao4api.filter;

import java.util.Date;

public class DebtorFilter {
	private String shortName;
	private String fullName;
	private Date controlDate;
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
	public Date getControlDate() {
		return controlDate;
	}
	public void setControlDate(Date controlDate) {
		this.controlDate = controlDate;
	}
	
}
