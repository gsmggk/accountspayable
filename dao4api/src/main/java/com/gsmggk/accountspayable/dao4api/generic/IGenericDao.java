package com.gsmggk.accountspayable.dao4api.generic;

import java.util.List;

import com.gsmggk.accountspayable.dao4api.filter.Criteria;

/**
 * Generic DAO interface for CRUD operations
 * 
 * @author Gena
 *
 * @param <T>
 *            - datamodel name
 */
public interface  IGenericDao<T> {

	/**
	 * Read one object of model this id 
	 * @param id - model id
	 * @return model 
	 */
	T read(Integer id);
	
	

	void delete(Integer id);

	List<T> getAll();

	T insert(T object);

	void update(T object);
	
	
}
