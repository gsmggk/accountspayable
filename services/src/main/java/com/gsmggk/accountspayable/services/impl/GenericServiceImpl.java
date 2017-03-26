package com.gsmggk.accountspayable.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.gsmggk.accountspayable.dao.impl.db.IActionDao;
import com.gsmggk.accountspayable.dao.impl.db.IGenericDao;
import com.gsmggk.accountspayable.services.IAbstractService;

@Service
public class GenericServiceImpl<T> implements IAbstractService {

	@Override
	public void save(Object objekt) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Object object) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Object> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
	/*
	 * @Inject private IGenericDao<T> genericDao;
	 * 
	 * @Override public void save(T model) { // TODO Auto-generated method stub
	 * 
	 * }
	 * 
	 * @Override public T get(Integer id) { // TODO Auto-generated method stub
	 * return null; }
	 * 
	 * @Override public void delete(T model) { genericDao.delete(model);
	 * 
	 * }
	 * 
	 * @Override public List<T> getAll() { // TODO Auto-generated method stub
	 * return null; }
	 */
}
