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
import com.gsmggk.accountspayable.dao4api.MyTrans;
import com.gsmggk.accountspayable.dao4api.exception.MyNotFoundException;
import com.gsmggk.accountspayable.datamodel.Oper;
import com.gsmggk.accountspayable.datamodel.defaults.DefaultValue;
import com.gsmggk.accountspayable.services.IOperService;
import com.gsmggk.accountspayable.services.impl.exceptions.MyAccessDeniedException;

@Service
public class OperServiceImpl implements IOperService {
	private static final Logger LOGGER = LoggerFactory.getLogger(OperServiceImpl.class);

	@Inject
	private IOperDao operDao;

	@Inject
	private IClerkDao clerkDao;
	@Inject
	private IDebtorDao debtorDao;
	
	@MyTrans
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
	@MyTrans
	@Override
	public void delete(Oper oper) {
		operDao.delete(oper.getId());

	}
@MyTrans
	@Override
	public void linkDebtor2Clerk(Integer conductClerkId, Integer debtorId, Integer clerkId) {

		if (!clerkDao.checkAction4Clerk(conductClerkId, DefaultValue.ALLOCATE_DEBTOR_ACTION.getCode())) {
			LOGGER.warn("Clerk id:{} access denid to allocate debtor id:{}", clerkId, debtorId);
			throw new MyAccessDeniedException("Allocate debtor access denied");
		}

		existsDebtorClerk(debtorId, clerkId);
		
		Oper oper = operDao.checkAllocated(debtorId, clerkId);
		if (oper == null) {
			
			 oper = new Oper();// insert new operation link
			prepareLinkDebtor2Clerk(true, conductClerkId, debtorId, clerkId, oper);
			operDao.insert(oper);
			return;
		}
		if (oper.getActionId().equals(DefaultValue.ALLOCATE_DEBTOR_ACTION.getCode())) {
			LOGGER.debug("Debtor id:{} allready allocated to Clerk id:{}", debtorId, clerkId);
			throw new MyNotFoundException("Debtor allready allocated");
		}

		prepareLinkDebtor2Clerk(true, conductClerkId, debtorId, clerkId, oper);
		operDao.update(oper);
	}

	/**
	 * Check debtor, clerk exists
	 * @param debtorId
	 * @param clerkId
	 */
	private void existsDebtorClerk(Integer debtorId, Integer clerkId) {
		if (!debtorDao.chekExist(debtorId)){throw new MyNotFoundException("Debtor not found");}
		if (!clerkDao.chekExist(clerkId)){throw new MyNotFoundException("Clerk not found");}
	}

	private void prepareLinkDebtor2Clerk(Boolean flagUpdate, Integer conductClerkId, Integer debtorId, Integer clerkId,
			Oper oper) {

		oper.setActionId(DefaultValue.ALLOCATE_DEBTOR_ACTION.getCode());
		oper.setActionDate(new Timestamp(new Date().getTime()));
		oper.setControlDate(new Date(new Date().getTime()));
		oper.setDebtorId(debtorId);
		oper.setClerkId(clerkId);

		StringBuilder desc = new StringBuilder();
		if (flagUpdate) {
			desc.append(oper.getOperDesc()+ "\n");
		}
		desc.append(oper.getActionDate().toString() + "\n");
		
		desc.append(String.format("Клерк %s НАЗНАЧИЛ должника %s - клерку %s   ",
				clerkDao.read(conductClerkId).getClerkFullName(), debtorDao.read(debtorId).getShortName(),
				clerkDao.read(clerkId).getClerkFullName()));
		
		oper.setOperDesc(desc.toString());
		LOGGER.info("Allocate Debtor: Clerk id-{} Allocate Debtor id-{}  to clerk id-{}", conductClerkId, debtorId,
				clerkId);
	}
@MyTrans
	@Override
	public void unlinkDebtor2Clerk(Integer conductClerkId, Integer debtorId, Integer clerkId) {

		if (!clerkDao.checkAction4Clerk(conductClerkId, DefaultValue.UNLOCATE_DEBTOR_ACTION.getCode())) {
			LOGGER.warn("Clerk id:{} access denid to unlocate debtor id:{}", clerkId, debtorId);
			throw new MyAccessDeniedException("Unlocate debtor access denied");
		}
		existsDebtorClerk(debtorId, clerkId);
		Oper oper = operDao.checkAllocated(debtorId, clerkId);
		if (oper == null || oper.getActionId() == DefaultValue.UNLOCATE_DEBTOR_ACTION.getCode()) {
			LOGGER.debug("Debtor id:{} not allocated to Clerk id:{}", debtorId, clerkId);
			throw new MyNotFoundException("Debtor not allocated to Clerk");
		}

		oper.setActionId(DefaultValue.UNLOCATE_DEBTOR_ACTION.getCode());
		oper.setActionDate(new Timestamp(new Date().getTime()));
		oper.setControlDate(new Date(new Date().getTime()));
		oper.setDebtorId(debtorId);
		oper.setClerkId(clerkId);

		StringBuilder desc = new StringBuilder();
		desc.append(oper.getOperDesc() + "\n");
		desc.append(oper.getActionDate().toString() + "\n");

		desc.append(String.format("Клерк %s СНЯЛ НАЗНАЧЕНИЕ должника %s - клерку %s   ",
				clerkDao.read(conductClerkId).getClerkFullName(), debtorDao.read(debtorId).getShortName(),
				clerkDao.read(clerkId).getClerkFullName()));
		oper.setOperDesc(desc.toString());
		LOGGER.info("Allocate Debtor: Clerk id-{} Allocate Debtor id-{}  to clerk id-{}", conductClerkId, debtorId,
				clerkId);
		operDao.update(oper);
	}
@MyTrans
	@Override
	public void addOper(Oper oper) {
		oper.setActionDate(new Timestamp(new Date().getTime()));

		Integer clerkId = oper.getClerkId();
		Integer actionId = oper.getActionId();
		Integer debtorId=oper.getDebtorId();
		if (!clerkDao.checkAction4Clerk(clerkId, actionId)) {
			LOGGER.error("Clerk id:{}  access denide to Add operation with action id:{}", clerkId, actionId);
			throw new MyAccessDeniedException("Access clerk to operation denieded");
		}
	
		if (!clerkDao.chekDebtor4Clerk(clerkId, debtorId)){
			LOGGER.error("Clerk id:{} not assigned to debtor id:{}", clerkId, debtorId);
			throw new MyAccessDeniedException("Clerk not assigned ro debtor");
		}
		operDao.insert(oper);
		LOGGER.info("Operatin id:{} inserted- debtor id:{} action id:{} clerk id:{}", oper.getId(), oper.getDebtorId(),
				oper.getActionId(), oper.getClerkId());
	}

	@Override
	public Oper getOper(Integer operId) {

		return operDao.getOper(operId);
	}
	@MyTrans
	@Override
	public void updateOper(Integer conductClerkId, Oper oper) {
		Oper oldOper=operDao.read(oper.getId());
		
		if (!clerkDao.checkAction4Clerk(conductClerkId, oldOper.getActionId())) {
			LOGGER.error("Clerk id:{} access denide Edit operation with old action id:{}", conductClerkId,
					oper.getActionId());
			throw new MyAccessDeniedException("Access denied edit this operations action");
		}
		
		if (!clerkDao.checkAction4Clerk(conductClerkId, oper.getActionId())) {
			LOGGER.error("Clerk id:{}  access denide  Edit operation with new action id:{}", conductClerkId,
					oper.getActionId());
			throw new MyAccessDeniedException("Access denied to this operations action");
		}
		Integer clerkId=conductClerkId;
		Integer debtorId=oper.getDebtorId();
		if (!clerkDao.chekDebtor4Clerk(clerkId, debtorId)){
			LOGGER.error("Clerk id:{} not assigned to debtor id:{}", clerkId, debtorId);
			throw new MyAccessDeniedException("Clerk not assigned ro debtor");
		}
		operDao.update(oper);
		LOGGER.info("Operatin id:{} updated- debtor id:{} action id:{} conduct clerk id:{}", oper.getId(),
				oper.getDebtorId(), oper.getActionId(), conductClerkId);
	}

	@Override
	public List<Oper> getOpers4Debtor(Integer debtorId) {

		return operDao.getOpers4Debtor(debtorId);
	}
	@MyTrans
	@Override
	public void deleteOper(Integer conductClerkId, Oper oper) {
		if (!clerkDao.checkAction4Clerk(conductClerkId, oper.getActionId())) {
			LOGGER.error("Clerk id:{} have access denide to Delete operation with action id:{}", conductClerkId,
					oper.getActionId());
			throw new MyAccessDeniedException("Access denied delete this operation");
		}
		Integer clerkId=conductClerkId;
		Integer debtorId=oper.getDebtorId();
		if (!clerkDao.chekDebtor4Clerk(clerkId, debtorId)){
			LOGGER.error("Clerk id:{} not assigned to debtor id:{}", clerkId, debtorId);
			throw new MyAccessDeniedException("Clerk not assigned ro debtor");
		}
		operDao.delete(oper.getId());
		LOGGER.warn("Operatin id:{} delete- debtor id:{} action id:{} conduct clerk id:{}", oper.getId(),
				oper.getDebtorId(), oper.getActionId(), conductClerkId);

	}
}
