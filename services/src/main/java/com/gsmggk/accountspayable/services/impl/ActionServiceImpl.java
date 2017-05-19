package com.gsmggk.accountspayable.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gsmggk.accountspayable.dao4api.IActionDao;
import com.gsmggk.accountspayable.dao4api.MyTrans;
import com.gsmggk.accountspayable.datamodel.Action;
import com.gsmggk.accountspayable.services.IActionService;

@Service
public class ActionServiceImpl implements IActionService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ActionServiceImpl.class);
	
	@Inject
	private IActionDao actionDao;
	
	@MyTrans
	@Override
	public void save(Action action) {
		if (action.getId() == null) {
			LOGGER.debug("insert Action");
			actionDao.insert(action);
		//	throw new RuntimeException("test exception for transaction");
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
	@MyTrans
	@Override
	public void delete(Action action) {
		LOGGER.warn("Delete Action: .id={} .actionName={}",action.getId().toString(),action.getActionName());

		actionDao.delete(action.getId());

	}

	

}
