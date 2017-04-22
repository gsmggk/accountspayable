package com.gsmggk.accountspayable.datamodel;

import java.sql.Timestamp;
import java.util.Date;

public class Oper extends AbstractTable {
	private Integer debtorId;
	private Integer clerkId;
	private Integer actionId;
	private Timestamp actionDate;
	private Date controlDate;
	private String operDesc;

	@Override
	public String toString() {
		return "Oper [debtorId=" + debtorId + ", clerkId=" + clerkId + ", actionId=" + actionId + ", actionDate="
				+ actionDate + ", controlDate=" + controlDate + ", operDesc=" + operDesc + ", id=" + id + "]";
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

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Oper other = (Oper) obj;
		if (actionDate == null) {
			if (other.actionDate != null)
				return false;
		} else if (!actionDate.equals(other.actionDate))
			return false;
		if (actionId == null) {
			if (other.actionId != null)
				return false;
		} else if (!actionId.equals(other.actionId))
			return false;
		if (clerkId == null) {
			if (other.clerkId != null)
				return false;
		} else if (!clerkId.equals(other.clerkId))
			return false;
		if (controlDate == null) {
			if (other.controlDate != null)
				return false;
		} else if (!controlDate.equals(other.controlDate))
			return false;
		if (debtorId == null) {
			if (other.debtorId != null)
				return false;
		} else if (!debtorId.equals(other.debtorId))
			return false;
		if (operDesc == null) {
			if (other.operDesc != null)
				return false;
		} else if (!operDesc.equals(other.operDesc))
			return false;
		return true;
	}

}
