package com.gsmggk.accountspayable.services;

import java.util.List;

import com.gsmggk.accountspayable.datamodel.Oper;



public interface IOperService {

	
	void save(Oper oper);

	List<Oper> getAll();

	Oper get(Integer id);

	
	void delete(Oper oper);
	
	
	
	/**
	 * Check access conduct clerk for allocate debtor to other clerk.
	 * Check if debtor yet allocated to clerk.
	 * Allocate if not.
	 * @param conductClerkId Conduct clerk id
	 * @param debtorID debtor id
	 * @param clerkId clerk id
	 */
	void linkDebtor2Clerk(Integer conductClerkId,Integer debtorId,Integer clerkId);
	/**
	 * Check access conduct clerk for unlocked debtor to other clerk.
	 * 
	 * Unlocked if not.
	 * @param conductClerkId Conduct clerk id
	 * @param debtorID debtor id
	 * @param clerkId clerk id
	 */
	void unlinkDebtor2Clerk(Integer conductClerkId, Integer debtorId, Integer clerkId);

	/**
	 * Add operation
	 * @param oper -operation detail
	 */
	void addOper(Oper oper);
	
	/**
	 * Get full oper with detail
	 * @param operId
	 * @return oper
	 */
	Oper getOper(Integer operId);

	
	/**
	 * Clerk modified debtors operation. Check if new and old operation for same debtor.
	 *   Check clerk access for old and new operations action.
	 * If access exist modify operation 
	 * @param conductClerkId - clerk id who conduct update
	 * @param oper operation
	 */
	void updateOper(Integer conductClerkId, Oper newOper);

	/**
	 * Get operations for  debtor
	 * @param debtorId
	 * @return List oper
	 */
	List<Oper> getOpers4Debtor(Integer debtorId);

	/**
	 * Check clerk access to operation action.
	 * If allowed delete operation. 
	 * @param conductClerkId - clerk id who conduct update
	 * @param oper operation
	 */
	void deleteOper(Integer conductClerkId, Oper oper);

	
}
