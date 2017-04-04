package com.gsmggk.accountspayable.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gsmggk.accountspayable.dao.impl.db.IActionDao;
import com.gsmggk.accountspayable.datamodel.Action;
import com.gsmggk.accountspayable.services.IActionService;

@Service
public class ActionServiceImpl implements IActionService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ActionServiceImpl.class);
	
	@Inject
	private IActionDao actionDao;
	
	@Override
	public void save(Action action) {
		if (action.getId() == null) {
			LOGGER.debug("insert Action");
			actionDao.insert(action);
		} else {
			LOGGER.debug("update Action");
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
		LOGGER.warn("Delete Action: .id={} .actionName={}",action.getId().toString(),action.getActionName());

		actionDao.delete(action);

	}

}
