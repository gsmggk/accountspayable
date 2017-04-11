package com.gsmggk.accountspayable.dao4api;

import java.util.List;

import com.gsmggk.accountspayable.datamodel.Oper;

public interface IOperDao {
	Oper insert(Oper oper);

	Oper read(Integer id);

	void update(Oper oper);

	void delete(Oper oper);

	List<Oper> getAll();
}
