package com.gsmggk.accountspayable.dao4xml.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gsmggk.accountspayable.dao4api.IDebtorDao;
import com.gsmggk.accountspayable.dao4api.filter.Criteria;
import com.gsmggk.accountspayable.dao4api.filter.Filter;
import com.gsmggk.accountspayable.dao4api.filter.SortData;
import com.gsmggk.accountspayable.dao4api.maps.DebtorControl;
import com.gsmggk.accountspayable.dao4xml.impl.exception.NotSupportedMethodException;
import com.gsmggk.accountspayable.dao4xml.impl.generic.GenericDaoXMLImpl;
import com.gsmggk.accountspayable.datamodel.Debtor;
import com.gsmggk.accountspayable.datamodel.Oper;
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

	

	@Override
	public Debtor creareDebtor(Debtor debtor, Oper oper) {
		throw new NotSupportedMethodException();
	}

	@Override
	public List<DebtorControl> getDebtors4Clerk(Integer clerkId, String searchShotName, String searchFullName,
			Date equal2Date, Boolean sortControl, Boolean sortShortName, Boolean sortFullName,Integer limit
			,Integer offset) {
		throw new NotSupportedMethodException();
	}

	

	

}
