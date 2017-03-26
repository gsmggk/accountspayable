package com.gsmggk.accountspayable.dao.impl.db;

import java.util.List;

public interface IAbstractDao {

	Object insert(Object objekt);

	Object read(Integer id);

	void update(Object objekt);

	void delete(Object objekt);

	List<Object> getAll();
}
