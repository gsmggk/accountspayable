package com.gsmggk.accountspayable.services.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsmggk.accountspayable.dao4api.IDebtorDao;
import com.gsmggk.accountspayable.dao4api.IOperDao;
import com.gsmggk.accountspayable.datamodel.Debtor;
import com.gsmggk.accountspayable.datamodel.Oper;
import com.gsmggk.accountspayable.datamodel.SystemValue;
import com.gsmggk.accountspayable.services.IDebtorService;
import com.gsmggk.accountspayable.services.util.CurrentLayer;

@Service
public class DebtorServiceImpl implements IDebtorService {
	private static final Logger LOGGER = LoggerFactory.getLogger(DebtorServiceImpl.class);

	@Inject
	private IDebtorDao debtorDao;
	@Inject
	private IOperDao operDao;

	@Transactional
	@Override
	public void save(Debtor debtor) {
		Oper oper = new Oper();
		if (debtor.getId() == null) {
			LOGGER.debug("insert Debtor: " + debtor);
			debtorDao.insert(debtor);

			oper.setDebtorId(debtor.getId());
			oper.setActionId(SystemValue.ADD_ACTION.getCode());

			oper.setClerkId(CurrentLayer.getClerkId());
			operDao.insert(oper);
			oper.setActionDate(new Date());
			// Тут впору сделать добавление деталей операции
			operDao.insertDetal(oper);

			LOGGER.debug("insert Debtor: insert Oper(add debtor) : " + oper);

		} else {
			LOGGER.debug("update Debtor: " + debtor);
			debtorDao.update(debtor);
		}
	}

	@Override
	public List<Debtor> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Debtor get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Debtor debtor) {
		// TODO Auto-generated method stub

	}

}
