package com.gsmggk.accountspayable.dao4api.modelmap;

import java.util.Date;

public class DebtorControl extends DebtorBase {

private Date control;

public Date getControl() {
	return control;
}
public void setControl(Date control) {
	this.control = control;
}
@Override
public String toString() {
	return "DebtorControl [control=" + control + ", toString()=" + super.toString() + "]";
}




}
