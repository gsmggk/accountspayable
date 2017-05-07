package com.gsmggk.accountspayable.dao4api.params;

public class ParamsDebtor extends Params {
	/**
	 * Search string by debtor short name
	 */
	private String searchShortName;
	/**
	 * Search string by debtor full name
	 */
	private String seachFullName;
	/**
	 * Sort by debtor short name
	 */
	private Boolean sortShortName;
	/**
	 * Sort by debtor full name
	 */
	private Boolean sortFullName;

	public String getSearchShortName() {
		return searchShortName;
	}

	public void setSearchShortName(String searchShortName) {
		this.searchShortName = searchShortName;
	}

	public String getSeachFullName() {
		return seachFullName;
	}

	public void setSeachFullName(String seachFullName) {
		this.seachFullName = seachFullName;
	}

	public Boolean getSortShortName() {
		return sortShortName;
	}

	public void setSortShortName(Boolean sortShortName) {
		this.sortShortName = sortShortName;
	}

	public Boolean getSortFullName() {
		return sortFullName;
	}

	public void setSortFullName(Boolean sortFullName) {
		this.sortFullName = sortFullName;
	}
@Override
	public boolean nullable() {
		if (super.nullable()&&
			sortShortName==null&&
			sortFullName==null&&
			seachFullName==null&&
			searchShortName==null) {return true;} else{
		return false;}
	}

}
