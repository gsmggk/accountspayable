package com.gsmggk.accountspayable.datamodel;

public enum SystemValue {
	ADD_ACTION(9), EDIT_ACTION(8), CLOSE_ACTION(7), ADMIN_ROLE(0), ADMIN_USER(0),DISTRIBUTE_DECTOR_ACTION(11);

	private int code;

	private SystemValue(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
