package com.gsmggk.accountspayable.dao4api.params;

/**
 * Contains request parameter for select debtors into boss layer
 * @author Gena
 *
 */
public class ParamsDebtors4Boss extends ParamsDebtor{
	/*
	 * Sort by debtor active state
	 */
	private Boolean sortActive;
	
	public Boolean getSortActive() {
		return sortActive;
	}
	public void setSortActive(Boolean sortActive) {
		this.sortActive = sortActive;
	}
	
	

}
