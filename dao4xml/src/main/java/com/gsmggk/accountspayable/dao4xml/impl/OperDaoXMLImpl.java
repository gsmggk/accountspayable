package com.gsmggk.accountspayable.dao4xml.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gsmggk.accountspayable.dao4api.IOperDao;
import com.gsmggk.accountspayable.dao4api.filter.Criteria;
import com.gsmggk.accountspayable.dao4xml.impl.exception.NotSupportedMethodException;
import com.gsmggk.accountspayable.dao4xml.impl.generic.GenericDaoXMLImpl;
import com.gsmggk.accountspayable.datamodel.Oper;

@Repository
public class OperDaoXMLImpl extends GenericDaoXMLImpl<Oper> implements IOperDao {

	@Override
	public void getPropert4Update(Oper modelItem, Oper object) {
		// TODO Auto-generated method stub

	}

	@Override
	protected String getXMLFileName() {
		return "oper.xml";
	}


	@Override
	public Oper getOper(Integer operId) {
		throw new NotSupportedMethodException();
	}

	@Override
	public Oper checkAllocated(Integer debtorID, Integer clerkId) {
		throw new NotSupportedMethodException();
	}

	@Override
	public List<Oper> getOpers4Debtor(Integer debtorId) {
		throw new NotSupportedMethodException();
	}

	@Override
	public Oper getDebtorStateOper(Integer debtorId) {
		throw new NotSupportedMethodException();
	}

	

	
	

	

}
