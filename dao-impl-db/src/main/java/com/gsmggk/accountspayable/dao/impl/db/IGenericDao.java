package com.gsmggk.accountspayable.dao.impl.db;

import java.util.List;

public interface IGenericDao<T> {

	T insert(T model);

	T read(Integer id);

	void update(T model);

	void delete(T model);

	List<T> getAll();

}
