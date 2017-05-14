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
import com.gsmggk.accountspayable.dao4api.MyTrans;
import com.gsmggk.accountspayable.dao4api.exception.MyNotFoundException;
import com.gsmggk.accountspayable.dao4api.modelmap.DebtorControl;
import com.gsmggk.accountspayable.dao4api.modelmap.DebtorRepo;
import com.gsmggk.accountspayable.dao4api.modelmap.DebtorState;
import com.gsmggk.accountspayable.dao4api.params.ParamsDebtor;
import com.gsmggk.accountspayable.dao4api.params.ParamsDebtors4Boss;
import com.gsmggk.accountspayable.dao4api.params.ParamsDebtors4Clerk;
import com.gsmggk.accountspayable.datamodel.Clerk;
import com.gsmggk.accountspayable.datamodel.Debtor;
import com.gsmggk.accountspayable.datamodel.Oper;
import com.gsmggk.accountspayable.datamodel.defaults.DefaultValue;
import com.gsmggk.accountspayable.services.IDebtorService;
import com.gsmggk.accountspayable.services.impl.exceptions.MyAccessDeniedException;

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
	@MyTrans
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
	@MyTrans
	@Override
	public void delete(Debtor debtor) {
		debtorDao.update(debtor);

	}

	@Override
	public List<Debtor> getAllocatedDebtors(Boolean allocated) {
		ParamsDebtor params = new ParamsDebtor();
		params.setSortShortName(true);
		return debtorDao.getAllocatedDebtors(allocated, params);
	}

	@Override
	public List<Debtor> getAllocatedDebtors(Boolean allocated, ParamsDebtor params) {

		return debtorDao.getAllocatedDebtors(allocated, params);
	}

	@Override
	public List<DebtorControl> getDebtors4Clerk(Integer clerkId) {
		ParamsDebtors4Clerk params = new ParamsDebtors4Clerk();
		params.setSortControl(true);
		params.setSortShortName(true);
		return debtorDao.getDebtors4Clerk(clerkId, params);

	}

	@Override
	public List<DebtorControl> getDebtors4Clerk(Integer clerkId, ParamsDebtors4Clerk params) {

		return debtorDao.getDebtors4Clerk(clerkId, params);
	}
	@MyTrans
	@Override
	public void saveDebtor(Integer clerkId, Debtor debtor) {
		Clerk clerk = clerkDao.read(clerkId);
		Oper oper = new Oper();
		oper.setActionDate(new Timestamp(new Date().getTime()));
		oper.setClerkId(clerkId);

		if (debtor.getId() == null) {

			int code = DefaultValue.ADD_ACTION.getCode();
			if (!roleDao.chekAction2role(code, clerk.getRoleId())) {
				LOGGER.warn("Clerk :{} access denied Create Debtor:{} ", clerkId, debtor);
				throw new MyAccessDeniedException("Debtor create access denied");
			}

			LOGGER.info("Clerk :{} Create Debtor:{} ", clerkId, debtor);
			oper.setActionId(code);
			StringBuilder desc = new StringBuilder();
			desc.append(oper.getActionDate().toString() + "\n");
			desc.append(String.format("Клерк %s ДОБАВИЛ должника %s", clerk.getClerkFullName(), debtor.getShortName())
					+ "\n");

			oper.setOperDesc(desc.toString());

			debtorDao.creareDebtor(debtor, oper);

		} else {

			int code = DefaultValue.EDIT_ACTION.getCode();
			if (!roleDao.chekAction2role(code, clerk.getRoleId())) {
				LOGGER.warn("Clerk :{} try Update Debtor:{} ", clerkId, debtor);
				throw new MyAccessDeniedException("Debtor update access denied");
			}
			Integer debtorId = debtor.getId();
			if (!clerkDao.chekDebtor4Clerk(clerkId, debtorId)) {
				LOGGER.error("Clerk id:{} not assigned to debtor id:{}", clerkId, debtorId);
				throw new MyAccessDeniedException("Clerk not assigned ro debtor");
			}
			LOGGER.info("Clerk :{} Update Debtor:{} ", clerkId, debtor);
			oper.setActionId(code);
			StringBuilder desc = new StringBuilder();
			desc.append(oper.getActionDate().toString() + "\n");
			desc.append(
					String.format("Клерк %s МОДИФИЦИРОВАЛ должника %s", clerk.getClerkFullName(), debtor.getShortName())
							+ "\n");

			oper.setOperDesc(desc.toString());

			debtorDao.updateDebtor(debtor, oper);
		}
	}
	@MyTrans
	@Override
	public void closeDedtor(Integer clerkId, Integer debtorId) {
		if (!debtorDao.chekExist(debtorId)) {
			throw new MyNotFoundException("Debtor not found");
		}

		if (!clerkDao.checkAction4Clerk(clerkId, DefaultValue.CLOSE_ACTION.getCode())) {
			LOGGER.warn("Clerk id:{} access denid to close debtor id:{}", clerkId, debtorId);
			throw new MyAccessDeniedException("Close debtor access denied");
		}

		Oper operInit = operDao.getDebtorStateOper(debtorId);

		if (operInit.equals(null)) {
			LOGGER.debug("Debtor id:{} not found");
			throw new MyNotFoundException("Debtor not found");
		}
		Integer isDebtorOpen = operInit.getActionId();

		if (isDebtorOpen.equals(DefaultValue.CLOSE_ACTION.getCode())) {
			LOGGER.debug("Debtor id:{} allready closed");
			throw new MyNotFoundException("Debtor allready closed");
		}

		operInit.setActionDate(new Timestamp(new Date().getTime()));
		StringBuilder desc = new StringBuilder();
		desc.append(operInit.getOperDesc() + "\n");
		desc.append(operInit.getActionDate().toString() + "\n");
		desc.append(String.format("Клерк %s ЗАКРЫЛ должника %s \n", clerkDao.read(clerkId).getClerkFullName(),
				debtorDao.read(debtorId).getShortName()));
		operInit.setOperDesc(desc.toString());
		operInit.setClerkId(clerkId);
		operInit.setActionId(DefaultValue.CLOSE_ACTION.getCode());
		operDao.update(operInit);
		LOGGER.warn("Clerk id:{} Close debtor id:{}", clerkId, debtorId);
	}

	@Override
	public List<DebtorState> getDebtors4Boss(ParamsDebtors4Boss params) {

		return debtorDao.getDebtors4Boss(params);
	}

	@Override
	public List<DebtorState> getDebtors4Boss() {
		ParamsDebtors4Boss params = new ParamsDebtors4Boss();
		params.setSortActive(false);
		params.setSortShortName(true);
		return debtorDao.getDebtors4Boss(params);
	}

	@MyTrans	
	@Override
	public void reopenDedtor(Integer clerkId, Integer debtorId) {
		if (!clerkDao.checkAction4Clerk(clerkId, DefaultValue.ADD_ACTION.getCode())) {
			LOGGER.warn("Clerk id:{} access denid to reopen debtor id:{}", clerkId, debtorId);
			throw new MyAccessDeniedException("Reopen debtor access denied");
		}
		Oper operInit = operDao.getDebtorStateOper(debtorId);
		if (operInit==null) {
			LOGGER.debug("Debtor id:{} not found");
			throw new MyNotFoundException("Debtor not found");
		}

		Integer isDebtorOpen = operInit.getActionId();

		if (isDebtorOpen.equals(DefaultValue.ADD_ACTION.getCode())) {
			LOGGER.debug("Debtor id:{} allready active");
			throw new MyNotFoundException("Debtor allready active");
		}
		operInit.setActionDate(new Timestamp(new Date().getTime()));
		operInit.setControlDate(new Date(new Date().getTime()));
		StringBuilder desc = new StringBuilder();
		desc.append(operInit.getOperDesc() + "\n");
		desc.append(operInit.getActionDate().toString() + "\n");
		desc.append(String.format("Клерк %s ОТКРЫЛ ПОВТОРНО должника %s \n", clerkDao.read(clerkId).getClerkFullName(),
				debtorDao.read(debtorId).getShortName()));
		operInit.setOperDesc(desc.toString());
		operInit.setClerkId(clerkId);
		operInit.setActionId(DefaultValue.ADD_ACTION.getCode());
		operDao.update(operInit);
		LOGGER.warn("Clerk id:{} Close debtor id:{}", clerkId, debtorId);

	}

	@Override
	public List<DebtorRepo> getDebtorRepo(Date from, Date to, ParamsDebtor params) {

		return debtorDao.getDebtorRepo(from, to, params);
	}

}
