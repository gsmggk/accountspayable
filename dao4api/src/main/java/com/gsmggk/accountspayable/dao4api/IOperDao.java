package com.gsmggk.accountspayable.dao4api;

import com.gsmggk.accountspayable.dao4api.generic.IGenericDao;
import com.gsmggk.accountspayable.datamodel.Oper;

public interface IOperDao extends IGenericDao<Oper> {

	/**
	 * Check debtor allocated to clerk.
	 * Prevent double allocation link.
	 * @param debtorID
	 * @param clerkId
	 * @return true - allocated <br>
	 * false - not
	 */
	Boolean checkAllocated(Integer debtorID, Integer clerkId);

	
	
}
