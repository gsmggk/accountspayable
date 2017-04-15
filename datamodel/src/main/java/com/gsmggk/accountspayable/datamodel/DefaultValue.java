package com.gsmggk.accountspayable.datamodel;

public enum DefaultValue {
	ADD_ACTION(9), EDIT_ACTION(8), CLOSE_ACTION(7), ADMIN_ROLE(0), ADMIN_USER(0),ALLOCATE_DEBTOR_ACTION(11);

	private int code;

	private DefaultValue(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
