package com.gsmggk.accountspayable.services;

import java.util.List;

import com.gsmggk.accountspayable.datamodel.Oper;



public interface IOperService {

	
	void save(Oper oper);

	List<Oper> getAll();

	Oper get(Integer id);

	
	void delete(Oper oper);
	
	
	
	/**
	 * Check debtor allocated to clerk.
	 *Allocate if not.
	 * @param debtorID debtor id
	 * @param clerkId clerk id
	 */
	void allocateDebtor2Clerk(Integer debtorId,Integer clerkId);
	

	/**
	 * Add operation
	 * @param oper -operation detail
	 */
	void addOper(Oper oper);
}
