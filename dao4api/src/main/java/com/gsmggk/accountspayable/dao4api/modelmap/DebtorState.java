package com.gsmggk.accountspayable.dao4api.modelmap;

public class DebtorState extends DebtorBase{
	
	private Boolean active;
	
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	@Override
	public String toString() {
		return "DebtorState [active=" + active + ", toString()=" + super.toString() + "]";
	}
	
}
