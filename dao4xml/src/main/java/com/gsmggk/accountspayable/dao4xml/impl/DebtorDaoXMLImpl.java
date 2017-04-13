package com.gsmggk.accountspayable.dao4xml.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gsmggk.accountspayable.dao4api.IDebtorDao;
import com.gsmggk.accountspayable.dao4xml.impl.exception.NotSupportedMethodException;
import com.gsmggk.accountspayable.dao4xml.impl.generic.GenericDaoXMLImpl;
import com.gsmggk.accountspayable.datamodel.Debtor;
@Repository
public class DebtorDaoXMLImpl extends GenericDaoXMLImpl<Debtor> implements IDebtorDao{

	@Override
	public void getPropert4Update(Debtor modelItem, Debtor object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String getXMLFileName() {
		return "debtor.xml";
	}

	@Override
	public List<Debtor> getAllocatedDebtor(Boolean allocated) {
		throw new NotSupportedMethodException();
	}

	

}
