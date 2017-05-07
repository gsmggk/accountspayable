package com.gsmggk.accountspayable.webapp.models;

import javax.validation.constraints.NotNull;

/**
 * Model for adding into database.
 * @author Gena
 *
 */
public class OperInsertModel {
	@NotNull(message = "Debtor  must be not null")
	private Integer debtorId;
	
	@NotNull(message = "Action  must be not null")
	private Integer actionId;
	
	@NotNull(message = "Control date  must be not null")
	private String controlDate;
	private String operDesc;
	public Integer getDebtorId() {
		return debtorId;
	}
	public void setDebtorId(Integer debtorId) {
		this.debtorId = debtorId;
	}
	public Integer getActionId() {
		return actionId;
	}
	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}
	
	public String getOperDesc() {
		return operDesc;
	}
	public void setOperDesc(String operDesc) {
		this.operDesc = operDesc;
	}
	
	public String getControlDate() {
		return controlDate;
	}
	public void setControlDate(String controlDate) {
		this.controlDate = controlDate;
	}
	@Override
	public String toString() {
		return "OperInsertModel [debtorId=" + debtorId + ", actionId=" + actionId + ", controlDate=" + controlDate
				+ ", operDesc=" + operDesc + "]";
	}
	
}
