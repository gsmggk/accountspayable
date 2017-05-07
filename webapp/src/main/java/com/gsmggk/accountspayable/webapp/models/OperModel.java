package com.gsmggk.accountspayable.webapp.models;

import java.sql.Timestamp;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

public class OperModel {
	private Integer id;
	@NotNull(message = "Debtor  must be not null")
	private Integer debtorId;
	@NotNull(message = "Clerk  must be not null")
	private Integer clerkId;
	@NotNull(message = "Action  must be not null")
	private Integer actionId;
	@NotNull(message = "Action date  must be not null")
	@DateTimeFormat
	private Timestamp actionDate;
	@DateTimeFormat
	private Date controlDate;
	private String operDesc;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getDebtorId() {
		return debtorId;
	}
	public void setDebtorId(Integer debtorId) {
		this.debtorId = debtorId;
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
	public Timestamp getActionDate() {
		return actionDate;
	}
	public void setActionDate(Timestamp actionDate) {
		this.actionDate = actionDate;
	}
	public Date getControlDate() {
		return controlDate;
	}
	public void setControlDate(Date controlDate) {
		this.controlDate = controlDate;
	}
	public String getOperDesc() {
		return operDesc;
	}
	public void setOperDesc(String operDesc) {
		this.operDesc = operDesc;
	}
	
	
	
}
