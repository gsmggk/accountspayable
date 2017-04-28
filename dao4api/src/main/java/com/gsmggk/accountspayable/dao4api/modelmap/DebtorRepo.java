package com.gsmggk.accountspayable.dao4api.modelmap;

public class DebtorRepo extends DebtorBase{
      private Integer count;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "DebtorRepo [count=" + count + ", toString()=" + super.toString() + "]";
	}
      
}
