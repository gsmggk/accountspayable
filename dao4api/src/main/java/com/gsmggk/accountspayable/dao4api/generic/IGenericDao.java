package com.gsmggk.accountspayable.dao4api.generic;

import java.util.List;

/**
 * Generic DAO interface for CRUD operations
 * 
 * @author Gena
 *
 * @param <T>
 *            - datamodel name
 */
public interface  IGenericDao<T> {

	T read(Integer id);
	
	<R> R read(Object[] objects,Class<R> clazzz);

	void delete(T object);

	List<T> getAll();

	T insert(T object);

	void update(T object);

}
