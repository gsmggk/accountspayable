package com.gsmggk.accountspayable.dao4db;

import java.util.List;

import com.gsmggk.accountspayable.datamodel.Action;

public interface IActionDao {

	Action insert(Action action);

	Action read(Integer id);

	void update(Action action);

	void delete(Action action);

	List<Action> getAll();
}
