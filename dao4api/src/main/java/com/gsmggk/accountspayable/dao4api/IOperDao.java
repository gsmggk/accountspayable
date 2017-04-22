package com.gsmggk.accountspayable.dao4api;

import java.util.List;

import com.gsmggk.accountspayable.dao4api.generic.IGenericDao;
import com.gsmggk.accountspayable.datamodel.Oper;

public interface IOperDao extends IGenericDao<Oper> {

	/**
	 * Check debtor allocated to clerk. Prevent double allocation link.
	 * 
	 * @param debtorID
	 * @param clerkId
	 * @return true - allocated <br>
	 *         false - not
	 */
	Boolean checkAllocated(Integer debtorID, Integer clerkId);

	/**
	 * Get full oper with detail
	 * 
	 * @param operId
	 * @return oper
	 */
	Oper getOper(Integer operId);
	/**
	 * Get operations for  debtor
	 * @param debtorId
	 * @return List oper
	 */
	List<Oper> getOpers4Debtor(Integer debtorId);
	/**
	 * Get debtor state operation. Open state-  operation with action =9
	 * Close state- operation with action =1
	 * @param debtor Id
	 * @return  Operation init Current debtor state 
	
	 */ 
	Oper getDebtorStateOper(Integer debtorId);

	

}
