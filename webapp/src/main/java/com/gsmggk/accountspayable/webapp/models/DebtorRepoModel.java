package com.gsmggk.accountspayable.webapp.models;

public class DebtorRepoModel {
	private String shortName;
	private String fullName;
	 private Integer count;
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
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "DebtorRepoModel [shortName=" + shortName + ", fullName=" + fullName + ", count=" + count + "]";
	}
	 
}
