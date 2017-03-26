package com.gsmggk.accountspayable.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.gsmggk.accountspayable.datamodel.Action;

public interface IActionService {
	@Transactional
	void save(Action action);

	List<Action> getAll();

	Action get(Integer id);

	@Transactional
	void delete(Action action);
}
