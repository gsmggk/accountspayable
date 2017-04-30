package com.gsmggk.accountspayable.dao4xml.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gsmggk.accountspayable.dao4api.IDebtorDao;
import com.gsmggk.accountspayable.dao4api.modelmap.DebtorControl;
import com.gsmggk.accountspayable.dao4api.modelmap.DebtorRepo;
import com.gsmggk.accountspayable.dao4api.modelmap.DebtorState;
import com.gsmggk.accountspayable.dao4api.params.ParamsDebtor;
import com.gsmggk.accountspayable.dao4api.params.ParamsDebtors4Boss;
import com.gsmggk.accountspayable.dao4api.params.ParamsDebtors4Clerk;
import com.gsmggk.accountspayable.dao4xml.impl.exception.NotSupportedMethodException;
import com.gsmggk.accountspayable.dao4xml.impl.generic.GenericDaoXMLImpl;
import com.gsmggk.accountspayable.datamodel.Debtor;
import com.gsmggk.accountspayable.datamodel.Oper;

@Repository
public class DebtorDaoXMLImpl extends GenericDaoXMLImpl<Debtor> implements IDebtorDao {

	@Override
	public void getPropert4Update(Debtor modelItem, Debtor object) {
		modelItem.setShortName(object.getShortName());
		modelItem.setFullName(object.getFullName());
		modelItem.setAddress(object.getAddress());
		modelItem.setPhones(object.getPhones());
		modelItem.setFamily(object.getFamily());
		modelItem.setJobe(object.getJobe());
		modelItem.setOther(object.getOther());
	}

	@Override
	protected String getXMLFileName() {
		return "debtor.xml";
	}

	@Override
	public Debtor creareDebtor(Debtor debtor, Oper oper) {
		throw new NotSupportedMethodException();
	}

	@Override
	public List<Debtor> getAllocatedDebtors(Boolean allocated, ParamsDebtor params) {
		throw new NotSupportedMethodException();
	}

	@Override
	public void updateDebtor(Debtor debtor, Oper oper) {
		throw new NotSupportedMethodException();

	}

	@Override
	public List<DebtorControl> getDebtors4Clerk(Integer clerkId, ParamsDebtors4Clerk params) {
		throw new NotSupportedMethodException();
	}

	@Override
	public List<DebtorState> getDebtors4Boss(ParamsDebtors4Boss params) {
		throw new NotSupportedMethodException();
	}

	@Override
	public List<DebtorRepo> getDebtorRepo(Date from, Date to, ParamsDebtor params) {
		throw new NotSupportedMethodException();
	}

}
