package com.gsmggk.accountspayable.dao4xml.impl;

import org.springframework.stereotype.Repository;

import com.gsmggk.accountspayable.dao4api.IOperDao;
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
	public Boolean checkAllocated(Integer debtorID, Integer clerkId) {
		throw new NotSupportedMethodException();
	}

	@Override
	public void allocate(Integer debtorID, Integer clerkId) {
		throw new NotSupportedMethodException();
		
	}

	

	

}
