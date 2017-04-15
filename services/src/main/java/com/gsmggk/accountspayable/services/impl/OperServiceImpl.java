package com.gsmggk.accountspayable.services.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gsmggk.accountspayable.dao4api.IOperDao;
import com.gsmggk.accountspayable.datamodel.DefaultValue;
import com.gsmggk.accountspayable.datamodel.Oper;
import com.gsmggk.accountspayable.services.IOperService;
import com.gsmggk.accountspayable.services.util.CurrentLayer;

@Service
public class OperServiceImpl implements IOperService {
	private static final Logger LOGGER = LoggerFactory.getLogger(OperServiceImpl.class);

	@Inject
	private IOperDao operDao;

	@Override
	public void save(Oper oper) {
		if (oper.getId() == null) {
			operDao.insert(oper);
			LOGGER.debug("insert Oper: " + oper);
		} else {

			operDao.update(oper);
		}

	}

	@Override
	public List<Oper> getAll() {

		return operDao.getAll();
	}

	@Override
	public Oper get(Integer id) {

		return operDao.read(id);
	}

	@Override
	public void delete(Oper oper) {
		operDao.delete(oper);

	}

	@Override
	public void allocateDebtor2Clerk(Integer debtorID, Integer clerkId) {

		if (!operDao.checkAllocated(debtorID, clerkId)) {
			Oper oper = new Oper();
			oper.setActionId(DefaultValue.ALLOCATE_DEBTOR_ACTION.getCode());
			oper.setActionDate(new Timestamp(new Date().getTime()));
			oper.setDebtorId(debtorID);
			oper.setClerkId(clerkId);
			String desc = String.format("Clerk %s do oretation Allocate with debtor %s for Clerk %s   ",
					CurrentLayer.getClerkId(), debtorID, clerkId);
			oper.setOperDesc(desc);

			operDao.insert(oper);
		}

	}
}
