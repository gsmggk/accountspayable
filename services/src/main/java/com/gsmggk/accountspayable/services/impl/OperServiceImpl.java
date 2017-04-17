package com.gsmggk.accountspayable.services.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gsmggk.accountspayable.dao4api.IClerkDao;
import com.gsmggk.accountspayable.dao4api.IDebtorDao;
import com.gsmggk.accountspayable.dao4api.IOperDao;
import com.gsmggk.accountspayable.datamodel.Clerk;
import com.gsmggk.accountspayable.datamodel.Debtor;
import com.gsmggk.accountspayable.datamodel.Oper;
import com.gsmggk.accountspayable.datamodel.defaults.DefaultValue;
import com.gsmggk.accountspayable.services.IClerkService;
import com.gsmggk.accountspayable.services.IDebtorService;
import com.gsmggk.accountspayable.services.IOperService;
import com.gsmggk.accountspayable.services.util.CurrentLayer;

@Service
public class OperServiceImpl implements IOperService {
	private static final Logger LOGGER = LoggerFactory.getLogger(OperServiceImpl.class);

	@Inject
	private IOperDao operDao;

	@Inject
	private IClerkDao clerkDao;
	@Inject
	private IDebtorDao debtorDao;

	@Override
	public void save(Oper oper) {
		if (oper.getId() == null) {
			operDao.insert(oper);
			LOGGER.info("insert Oper: " + oper);
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
	public void allocateDebtor2Clerk(Integer debtorId, Integer clerkId) {

		if (!operDao.checkAllocated(debtorId, clerkId)) {
			Oper oper = new Oper();
			oper.setActionId(DefaultValue.ALLOCATE_DEBTOR_ACTION.getCode());
			oper.setActionDate(new Timestamp(new Date().getTime()));
			oper.setDebtorId(debtorId);
			oper.setClerkId(clerkId);
			Clerk clerk = new Clerk();
			clerk = clerkDao.read(clerkId);
			Debtor debtor = new Debtor();
			debtor = debtorDao.read(debtorId);
			String desc = String.format("Клерк %s НАЗНАЧИЛ должника %s - клерку %s   ", CurrentLayer.getClerkFullName(),
					debtor.getShortName(), clerk.getClerkFullName());
			oper.setOperDesc(desc);

			operDao.insert(oper);
		}

	}

	@Override
	public void addOper(Oper oper) {
		oper.setActionDate(new Timestamp(new Date().getTime()));
		oper.setClerkId(CurrentLayer.getClerkId());
		
		operDao.insert(oper);

	}
}
