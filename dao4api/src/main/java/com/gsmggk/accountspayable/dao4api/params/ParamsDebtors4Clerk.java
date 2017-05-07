package com.gsmggk.accountspayable.dao4api.params;

import java.util.Date;

/**
 * Parameters for search and sort data into DebtorConrol table from
 * getDebtors4clerk
 * 
 * @author Gena
 *
 */

public class ParamsDebtors4Clerk extends ParamsDebtor {

	/**
	 * Sort only by one date <b>equal2Date</b>
	 */
	private Date equal2Date;

	/**
	 * Sort by control date
	 */
	private Boolean sortControl;

	public Date getEqual2Date() {
		return equal2Date;
	}

	public void setEqual2Date(Date equal2Date) {
		this.equal2Date = equal2Date;
	}

	public Boolean getSortControl() {
		return sortControl;
	}

	public void setSortControl(Boolean sortControl) {
		this.sortControl = sortControl;
	}

	/**
	 * return true if all field null
	 * 
	 * @author Gena
	 *
	 */
	@Override
	public boolean nullable() {
		if (super.nullable()&&
				sortControl==null&&
				equal2Date==null) {
			return true;
		} else {
			return false;
		}
	}

}
