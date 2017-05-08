package com.gsmggk.accountspayable.dao4api.modelmap;

import java.io.Serializable;

public class DebtorRepo extends DebtorBase implements Serializable{
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
