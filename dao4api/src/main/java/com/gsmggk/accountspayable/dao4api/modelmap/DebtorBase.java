package com.gsmggk.accountspayable.dao4api.modelmap;

public class DebtorBase {
	private Integer debtorId;
	private String shortName;
	private String fullName;
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
	@Override
	public String toString() {
		return "DebtorBase [debtorId=" + debtorId + ", shortName=" + shortName + ", fullName=" + fullName + "]";
	}
}
