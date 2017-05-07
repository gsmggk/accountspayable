package com.gsmggk.accountspayable.dao4api.params;

public class Params {
	private Integer limit;
	private Integer offset;
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public boolean nullable() {
		if (limit==null&&offset==null){return true;}else{
		return false;}
	}
	
}
