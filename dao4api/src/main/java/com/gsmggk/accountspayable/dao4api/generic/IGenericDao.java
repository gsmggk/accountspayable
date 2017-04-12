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
public interface IGenericDao<T> {

	T read(Integer id);

	void delete(T object);

	List<T> getAll();

	T insert(T object);

	void update(T object);

}
