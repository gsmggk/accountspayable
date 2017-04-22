package com.gsmggk.accountspayable.dao4api.modelmap;

public class DebtorState {
	private Integer debtorId;
	private String shortName;
	private String fullName;
	private Boolean active;
	public Integer getDebtorId() {
		return debtorId;
	}
	public void setDebtorId(Integer debtorId) {
		this.debtorId = debtorId;
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
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	@Override
	public String toString() {
		return "DebtorState [debtorId=" + debtorId + ", shortName=" + shortName + ", fullName=" + fullName + ", active="
				+ active + "]";
	}
	
}
