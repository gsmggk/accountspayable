package com.gsmggk.accountspayable.datamodel;

import java.math.BigDecimal;

public class Account extends AbstractTable {
	private String accountName;
	private BigDecimal money;
	private Integer debtorId;

	@Override
	public String toString() {
		return "Account [accountName=" + accountName + ", money=" + money + ", debtorId=" + debtorId + ", id=" + id
				+ "]";
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
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
		if (money == null) {
			if (other.money != null)
				return false;
		} else if (!money.equals(other.money))
			return false;
		return true;
	}

}
