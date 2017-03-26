package com.gsmggk.accountspayable.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface IAbstractService {
	@Transactional
	void save(Object objekt);

	Object get(Integer id);

	@Transactional
	void delete(Object object);

	List<Object> getAll();
}
