package com.gsmggk.accountspayable.datamodel.defaults;

public enum DefaultValue {
	ADD_ACTION(9), EDIT_ACTION(8), CLOSE_ACTION(1), ADMIN_ROLE(0), ADMIN_USER(0), ALLOCATE_DEBTOR_ACTION(
			11), UNLOCATE_DEBTOR_ACTION(2);

	private int code;

	private DefaultValue(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
