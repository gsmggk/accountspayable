package com.gsmggk.accountspayable.dao4api;

import java.util.List;

import com.gsmggk.accountspayable.dao4api.filter.Criteria;
import com.gsmggk.accountspayable.dao4api.generic.IGenericDao;
import com.gsmggk.accountspayable.datamodel.Debtor;
import com.gsmggk.accountspayable.datamodel.Oper;

public interface IDebtorDao extends IGenericDao<Debtor>{

	List<Debtor> getAllocatedDebtor(Boolean allocated);
	
	Debtor creareDebtor(Debtor debtor,Oper oper);
}
