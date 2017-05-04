package com.gsmggk.accountspayable.webapp.models;

import java.math.BigDecimal;

public class Account4DebtorModel {
	private Integer id;
	private String accountName;
	private BigDecimal summ;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	@Override
	public String toString() {
		return "Account4DebtorModel [id=" + id + ", accountName=" + accountName + ", summ=" + summ + "]";
	}
	
}
