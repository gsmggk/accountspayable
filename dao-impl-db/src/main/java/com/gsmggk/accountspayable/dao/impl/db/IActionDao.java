package com.gsmggk.accountspayable.dao.impl.db;

import com.gsmggk.accountspayable.datamodel.Action;

public interface IActionDao {
	void insert(Action action);

	void update(Action action);

	void delete(Action action);
}
