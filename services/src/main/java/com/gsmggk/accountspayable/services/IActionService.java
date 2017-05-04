package com.gsmggk.accountspayable.services;

import java.util.List;

import com.gsmggk.accountspayable.datamodel.Action;

public interface IActionService {

	
	void save(Action action);

	List<Action> getAll();

	Action get(Integer id);

	
	void delete(Action action);

	

	
}
