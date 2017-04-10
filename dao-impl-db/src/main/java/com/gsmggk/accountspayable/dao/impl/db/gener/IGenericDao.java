package com.gsmggk.accountspayable.dao.impl.db.gener;

import java.io.Serializable;
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

	T readR(Integer id);

	void deleteR(T object);

	List<T> getAllR();

	T insertR(T object);

	void updateR(T object);

}
