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
import com.gsmggk.accountspayable.dao4api.filter.Criteria;
import com.gsmggk.accountspayable.dao4api.filter.Filter;
import com.gsmggk.accountspayable.dao4api.maps.DebtorControl;
import com.gsmggk.accountspayable.datamodel.Clerk;
import com.gsmggk.accountspayable.datamodel.Debtor;
import com.gsmggk.accountspayable.datamodel.Oper;
import com.gsmggk.accountspayable.datamodel.defaults.DefaultValue;
import com.gsmggk.accountspayable.services.IClerkService;
import com.gsmggk.accountspayable.services.IDebtorService;
import com.gsmggk.accountspayable.services.IOperService;
import com.gsmggk.accountspayable.services.util.CurrentLayer;

@Service
public class DebtorServiceImpl implements IDebtorService {
	private static final Logger LOGGER = LoggerFactory.getLogger(DebtorServiceImpl.class);

	@Inject
	private IDebtorDao debtorDao;
	@Inject
	private IClerkService clerkService;

	@Override
	public void save(Debtor debtor) {

		Integer clerkId = CurrentLayer.getClerkId();
		Clerk clerk = new Clerk();
		clerk = clerkService.get(clerkId);
		Oper oper = new Oper();
		oper.setActionDate(new Timestamp(new Date().getTime()));

		oper.setClerkId(clerkId);

		if (debtor.getId() == null) {
			LOGGER.info("Create Debtor: " + debtor);
			oper.setActionId(DefaultValue.ADD_ACTION.getCode());
			String desc = String.format("Клерк %s ДОБАВИЛ должника %s", clerk.getClerkFullName(),
					debtor.getShortName());
			oper.setOperDesc(desc);

			debtorDao.creareDebtor(debtor, oper);

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
		
		return debtorDao.read(id);
	}

	@Override
	public void delete(Debtor debtor) {
		debtorDao.update(debtor);

	}

	@Override
	public List<Debtor> getAllocatedDebtor(Boolean allocated) {

		return debtorDao.getAllocatedDebtor(allocated);
	}

	@Override
	public List<DebtorControl> getDebtors4Clerk(Integer clerkId) {
     	return  debtorDao.getDebtors4Clerk(clerkId, null, null, null, true, null, null);
		
	}

	@Override
	public List<DebtorControl> getDebtors4Clerk(Integer clerkId, String searchShotName, String searchFullName,
			Date equal2Date, Boolean sortControl, Boolean sortShortName, Boolean sortFullName) {
		
 	return  debtorDao.getDebtors4Clerk(clerkId, searchShotName, searchFullName, equal2Date, sortControl, sortShortName, sortFullName);
	}

}
