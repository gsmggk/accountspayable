package com.gsmggk.accountspayable.datamodel;

public class Action extends AbstractTable{
	
	private String actionName;
	private Integer duration;

	
	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "Action [id=" + id + ", actionName=" + actionName  + ", duration=" + duration + "]";

	}
}
