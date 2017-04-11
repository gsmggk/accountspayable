package com.gsmggk.accountspayable.services;

import java.util.List;

import com.gsmggk.accountspayable.datamodel.Oper;



public interface IOperService {

	
	void save(Oper oper);

	List<Oper> getAll();

	Oper get(Integer id);

	
	void delete(Oper oper);
}
