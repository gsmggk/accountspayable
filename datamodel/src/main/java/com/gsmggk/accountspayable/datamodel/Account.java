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

}
