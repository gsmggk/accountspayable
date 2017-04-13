package com.gsmggk.accountspayable.services.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsmggk.accountspayable.dao4api.IDebtorDao;
import com.gsmggk.accountspayable.datamodel.Debtor;
import com.gsmggk.accountspayable.datamodel.Oper;
import com.gsmggk.accountspayable.datamodel.SystemValue;
import com.gsmggk.accountspayable.services.IDebtorService;
import com.gsmggk.accountspayable.services.IOperService;
import com.gsmggk.accountspayable.services.util.CurrentLayer;

@Service
public class DebtorServiceImpl implements IDebtorService {
	private static final Logger LOGGER = LoggerFactory.getLogger(DebtorServiceImpl.class);

	@Inject
	private IDebtorDao debtorDao;
	@Inject
	private IOperService operService;

	@Transactional
	@Override
	public void save(Debtor debtor) {
		Oper oper = new Oper();
		Integer clerkId = CurrentLayer.getClerkId();
		if (debtor.getId() == null) {
			LOGGER.debug("insert Debtor: " + debtor);
			debtorDao.insert(debtor);

			oper.setDebtorId(debtor.getId());
			oper.setActionId(SystemValue.ADD_ACTION.getCode());

			oper.setClerkId(clerkId);
			oper.setActionDate(new Timestamp(new Date().getTime()));
			String desc = String.format("Clerk %s do oretation %s for debtor %s", clerkId,
					SystemValue.ADD_ACTION.toString(), debtor.getShortName());
			oper.setOperDesc(desc);
			
			operService.save(oper);
		
		

		} else {
			LOGGER.debug("update Debtor: " + debtor);
			debtorDao.update(debtor);
		}
	}

	@Override
	public List<Debtor> getAll() {
				return debtorDao.getAll();
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

	@Override
	public List<Debtor> getAllocatedDebtor(Boolean allocated) {
		
		return debtorDao.getAllocatedDebtor(allocated);
	}

	@Override
	public void allocateDebtor2Clerk(Integer debtorID, Integer clerkId) {
		// TODO checkAllocated(Integer debtorID, Integer clerkId);
			// TODO allocate(Integer debtorID, Integer clerkId);
	}

	
}
