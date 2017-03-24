package com.gsmggk.accountspayable.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface IGenericService<T> {
	@Transactional
	void save(T model);

	T get(Integer id);

	@Transactional
	void delete(T model);

	List<T> getAll();
}
