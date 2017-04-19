package com.gsmggk.accountspayable.datamodel;

import java.math.BigDecimal;

public class Account extends AbstractTable {
	private String accountName;
	private BigDecimal summ;
	private Integer debtorId;

	@Override
	public String toString() {
		return "Account [accountName=" + accountName + ", summ=" + summ + ", debtorId=" + debtorId + ", id=" + id
				+ "]";
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public BigDecimal getSumm() {
		return summ;
	}

	public void setSumm(BigDecimal summ) {
		this.summ = summ;
	}

	public Integer getDebtorId() {
		return debtorId;
	}

	public void setDebtorId(Integer debtorId) {
		this.debtorId = debtorId;
	}


	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accountName == null) {
			if (other.accountName != null)
				return false;
		} else if (!accountName.equals(other.accountName))
			return false;
		if (debtorId == null) {
			if (other.debtorId != null)
				return false;
		} else if (!debtorId.equals(other.debtorId))
			return false;
		if (summ == null) {
			if (other.summ != null)
				return false;
		} else if (!summ.equals(other.summ))
			return false;
		return true;
	}

}
