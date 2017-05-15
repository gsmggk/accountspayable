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

	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DebtorRepo other = (DebtorRepo) obj;
		if (count == null) {
			if (other.count != null)
				return false;
		} else if (!count.equals(other.count))
			return false;
		return true;
	}
      
}
