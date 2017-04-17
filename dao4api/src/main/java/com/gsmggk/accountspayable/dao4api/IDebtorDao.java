package com.gsmggk.accountspayable.dao4api;

import java.util.List;

import com.gsmggk.accountspayable.dao4api.filter.DebtorFilter;
import com.gsmggk.accountspayable.dao4api.generic.IGenericDao;
import com.gsmggk.accountspayable.datamodel.Debtor;
import com.gsmggk.accountspayable.datamodel.Oper;

public interface IDebtorDao extends IGenericDao<Debtor>{

	List<Debtor> getAllocatedDebtor(Boolean allocated);
	
	 List<Debtor> search(DebtorFilter debtorFilter);

	Debtor creareDebtor(Debtor debtor,Oper oper);
}
