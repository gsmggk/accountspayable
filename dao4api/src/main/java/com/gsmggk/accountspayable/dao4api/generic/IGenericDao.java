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
	
	
	/**
	 * Check <b>object</b> for exist row with <b>id</b>
	
	 * @param id- search for wits id
	 * @return <b>true</b> if exist, <b>false</b> if not.
	 */
		public Boolean chekExist(Integer id) ;
	
	
}
