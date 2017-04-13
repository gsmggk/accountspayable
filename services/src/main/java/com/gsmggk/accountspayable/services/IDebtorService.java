package com.gsmggk.accountspayable.services;

import java.util.List;

import com.gsmggk.accountspayable.datamodel.Debtor;

public interface IDebtorService {

	void save(Debtor debtor);

	List<Debtor> getAll();

	Debtor get(Integer id);

	void delete(Debtor debtor);

	/**
	 * This method return list debtors those have or have'nt allocated with
	 * clerk for managing. Debtors must have add operation in oper table.
	 * @param allocated <br>
	 *    <b>true</b> -  debtors those allocated this clerk. <br>         
	 *   <b>false</b> -  debtors those NOT allocated this clerk.
	 *            
	 * @return List <b>debtors</b>
	 */
	List<Debtor> getAllocatedDebtor(Boolean allocated);
	
	
	
	void allocateDebtor2Clerk(Integer debtorID,Integer clerkId);

}
