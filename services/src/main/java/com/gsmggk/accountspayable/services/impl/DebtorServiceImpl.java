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
import com.gsmggk.accountspayable.dao4api.IRoleDao;
import com.gsmggk.accountspayable.dao4api.modelmap.DebtorControl;
import com.gsmggk.accountspayable.dao4api.modelmap.DebtorState;
import com.gsmggk.accountspayable.dao4api.params.ParamsDebtors4Boss;
import com.gsmggk.accountspayable.dao4api.params.ParamsDebtors4Clerk;
import com.gsmggk.accountspayable.datamodel.Clerk;
import com.gsmggk.accountspayable.datamodel.Debtor;
import com.gsmggk.accountspayable.datamodel.Oper;
import com.gsmggk.accountspayable.datamodel.defaults.DefaultValue;
import com.gsmggk.accountspayable.services.IDebtorService;
import com.gsmggk.accountspayable.services.impl.exceptions.MyAccessDeniedException;
import com.gsmggk.accountspayable.services.impl.exceptions.MyNotFoundException;

@Service
public class DebtorServiceImpl implements IDebtorService {
	private static final Logger LOGGER = LoggerFactory.getLogger(DebtorServiceImpl.class);

	@Inject
	private IDebtorDao debtorDao;
	@Inject
	private IClerkDao clerkDao;
	@Inject
	private IRoleDao roleDao;
	@Inject
	private IOperDao operDao;

	@Override
	public void save(Debtor debtor) {

		if (debtor.getId() == null) {
			LOGGER.info("Admin Create Debtor: " + debtor);

			debtorDao.insert(debtor);

		} else {
			LOGGER.info("Admin Update Debtor: " + debtor);

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
		return debtorDao.getDebtors4Clerk(clerkId, null, null, null, true, null, null, null, null);

	}

	@Override
	public List<DebtorControl> getDebtors4Clerk(Integer clerkId, String searchShotName, String searchFullName,
			Date equal2Date, Boolean sortControl, Boolean sortShortName, Boolean sortFullName, Integer limit,
			Integer offset) {

		return debtorDao.getDebtors4Clerk(clerkId, searchShotName, searchFullName, equal2Date, sortControl,
				sortShortName, sortFullName, limit, offset);
	}
	@Override
	public List<DebtorControl> getDebtors4Clerk(Integer clerkId, ParamsDebtors4Clerk params) {
		
		return debtorDao.getDebtors4Clerk(clerkId,params);
	}

	@Override
	public void saveDebtor(Integer clerkId, Debtor debtor) {
		Clerk clerk = clerkDao.read(clerkId);
		Oper oper = new Oper();
		oper.setActionDate(new Timestamp(new Date().getTime()));
		oper.setClerkId(clerkId);

		if (debtor.getId() == null) {

			int code = DefaultValue.ADD_ACTION.getCode();
			if (!roleDao.chekAction2role(code, clerk.getRoleId())) {
				LOGGER.warn("Clerk :{} try Create Debtor:{} ", clerkId, debtor);
				throw new MyAccessDeniedException("Debtor create access denied");
			}

			LOGGER.info("Clerk :{} Create Debtor:{} ", clerkId, debtor);
			oper.setActionId(code);
			StringBuilder desc=new StringBuilder();
			desc.append(oper.getActionDate().toString()+"\n");
			desc.append( String.format("Клерк %s ДОБАВИЛ должника %s", clerk.getClerkFullName(),
					debtor.getShortName())+"\n");
			
			oper.setOperDesc(desc.toString());

			debtorDao.creareDebtor(debtor, oper);

		} else {

			int code = DefaultValue.EDIT_ACTION.getCode();
			if (!roleDao.chekAction2role(code, clerk.getRoleId())) {
				LOGGER.warn("Clerk :{} try Update Debtor:{} ", clerkId, debtor);
				throw new MyAccessDeniedException("Debtor update access denied");
			}

			LOGGER.info("Clerk :{} Update Debtor:{} ", clerkId, debtor);
			oper.setActionId(code);
			StringBuilder desc=new StringBuilder();
			desc.append(oper.getActionDate().toString()+"\n");
			desc.append( String.format("Клерк %s МОДИФИЦИРОВАЛ должника %s", clerk.getClerkFullName(),
					debtor.getShortName())+"\n");
			
			oper.setOperDesc(desc.toString());

			debtorDao.updateDebtor(debtor, oper);
		}
	}

	@Override
	public void closeDedtor(Integer clerkId, Integer debtorId) {

		if (!clerkDao.checkAction4Clerk(clerkId, DefaultValue.CLOSE_ACTION.getCode())) {
			LOGGER.warn("Clerk id:{} access denid to close debtor id:{}", clerkId, debtorId);
			throw new MyAccessDeniedException("Close debtor access denied");
		}

		Oper operInit = operDao.getDebtorStateOper(debtorId);

		if (operInit.equals(null)) {
			LOGGER.debug("Debtor id:{} not found");
			throw new MyNotFoundException("Debtor not found");
		}
		Integer isDebtorOpen = operInit.getId();

		if (isDebtorOpen.equals(DefaultValue.CLOSE_ACTION.getCode())) {
			LOGGER.debug("Debtor id:{} allready closed");
			throw new MyNotFoundException("Debtor allready closed");
		}
     
      operInit.setActionDate(new Timestamp(new Date().getTime()));
      StringBuilder desc=new StringBuilder();
      desc.append(operInit.getOperDesc()+"\n");
      desc.append(operInit.getActionDate().toString()+"\n");
      desc.append(String.format("Клерк %s ЗАКРЫЛ должника %s \n", 
    		 clerkDao.read(clerkId).getClerkFullName(),
			 debtorDao.read(debtorId).getShortName()));
      operInit.setOperDesc(desc.toString());
      operInit.setClerkId(clerkId);
      operInit.setActionId(DefaultValue.CLOSE_ACTION.getCode());
      operDao.update(operInit);
      LOGGER.warn("Clerk id:{} Close debtor id:{}",clerkId,debtorId);
	}

	@Override
	public List<DebtorState> getDebtors4Boss(ParamsDebtors4Boss params) {
	
		return debtorDao.getDebtors4Boss(params);
	}



}
