package com.gsmggk.accountspayable.webapp.models;

public class OperDebtorModel {
	private Integer id;

	private Integer clerkId;
	private Integer actionId;
	private String actionDate;
	private String controlDate;
	private String operDesc;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getClerkId() {
		return clerkId;
	}
	public void setClerkId(Integer clerkId) {
		this.clerkId = clerkId;
	}
	public Integer getActionId() {
		return actionId;
	}
	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}
	public String getActionDate() {
		return actionDate;
	}
	public void setActionDate(String actionDate) {
		this.actionDate = actionDate;
	}
	public String getControlDate() {
		return controlDate;
	}
	public void setControlDate(String controlDate) {
		this.controlDate = controlDate;
	}
	public String getOperDesc() {
		return operDesc;
	}
	public void setOperDesc(String operDesc) {
		this.operDesc = operDesc;
	}
	@Override
	public String toString() {
		return "OperDebtorModel [id=" + id + ", clerkId=" + clerkId + ", actionId=" + actionId + ", actionDate="
				+ actionDate + ", controlDate=" + controlDate + ", operDesc=" + operDesc + "]";
	}
	
}
