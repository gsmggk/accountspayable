package com.gsmggk.accountspayable.dao4api.modelmap;

import java.util.Date;

import com.gsmggk.accountspayable.datamodel.Debtor;

public class DebtorControl {
private Integer debtorId;
private String shortName;
private String fullName;
private Date control;
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
public Date getControl() {
	return control;
}
public void setControl(Date control) {
	this.control = control;
}
@Override
public String toString() {
	return "DebtorControl [debtorId=" + debtorId + ", shortName=" + shortName + ", fullName=" + fullName + ", control="
			+ control + "]";
}



}
