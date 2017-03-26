package com.gsmggk.accountspayable.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.gsmggk.accountspayable.datamodel.Oper;



public interface IOperService {

	@Transactional
	void save(Oper oper);

	List<Oper> getAll();

	Oper get(Integer id);

	@Transactional
	void delete(Oper oper);
}
