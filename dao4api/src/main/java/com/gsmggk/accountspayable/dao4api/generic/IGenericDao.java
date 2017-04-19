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
	
	/**
	 * Read one property of model  
	 * @param objects - field array
	 * @param clazzz - f.e. <i>Integer.Class</i>
	 * @return property value
	 */
	<R> R read(Object[] objects,Class<R> clazzz);

	void delete(Integer id);

	List<T> getAll();

	T insert(T object);

	void update(T object);
	
	/*List<T> search(Criteria criteria);

	<R> R search1(Criteria criteria);*/

}
