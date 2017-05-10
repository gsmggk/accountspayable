package com.gsmggk.accountspayable.datamodel;

public class Action extends AbstractTable {
	public final static String ACTION_TABLE_NAME="action";
	public final static String ACTION_TABLE_ALIAS="act";
	public final static String ACTION_ID_FIELD_NAME="id";
	public final static String ACTION_ACTION_NAME_FIELD_NAME="action_name";
	public final static String ACTION_ACTION_DESC_FIELD_NAME="action_desc";
	public final static String ACTION_DURATION_FIELD_NAME="duration";

	private String actionName;
	private String actionDesc;
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

	

	public String getActionDesc() {
		return actionDesc;
	}

	public void setActionDesc(String actionDesc) {
		this.actionDesc = actionDesc;
	}

	@Override
	public String toString() {
		return "Action [actionName=" + actionName + ", actionDesc=" + actionDesc + ", duration=" + duration + "]";
	}
}
