package com.gsmggk.accountspayable.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.gsmggk.accountspayable.dao.impl.db.IActionDao;
import com.gsmggk.accountspayable.datamodel.Action;
import com.gsmggk.accountspayable.services.IActionService;
@Service
public class ActionServiceImpl implements IActionService {
	@Inject
	private IActionDao actionDao;
// TO DO почистить println-ы
	@Override
	public void save(Action action) {
		if (action.getId() == null) {
			System.out.println("Insert new Role");
			actionDao.insert(action);
		} else {
			System.out.println("Update Role id "+action.getId());
			actionDao.update(action);
		}

	}

	@Override
	public List<Action> getAll() {
		return actionDao.getAll();
	}

	@Override
	public Action get(Integer id) {
		return actionDao.read(id);
	}

	@Override
	public void delete(Action action) {
		actionDao.delete(action);

	}

}
