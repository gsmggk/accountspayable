package com.gsmggk.accountspayable.webapp.models;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class AccountModel {
	private Integer id;
	@NotNull(message = "Account name  must be not null")
	@NotEmpty(message = "Account name must be not empty")
	@Size(max=20,message = "Account name can't biger then 20")
		private String accountName;
	private BigDecimal summ;
	@NotNull(message = "Account  must connect to the debtor")
		private Integer debtorId;
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
	public Integer getDebtorId() {
		return debtorId;
	}
	public void setDebtorId(Integer debtorId) {
		this.debtorId = debtorId;
	}

}
