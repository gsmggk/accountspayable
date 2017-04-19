package com.gsmggk.accountspayable.dao4api.maps;

import java.util.Date;

import com.gsmggk.accountspayable.datamodel.Debtor;

public class DebtorControl extends Debtor{
private Date control;

public Date getControl() {
	return control;
}

public void setControl(Date control) {
	this.control = control;
}

}
