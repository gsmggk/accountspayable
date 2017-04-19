package com.gsmggk.accountspayable.services;

import java.util.List;

import com.gsmggk.accountspayable.datamodel.Debtor;

public interface IDebtorService {

	/**
	 * Create new debtor or modify existing
	 * 
	 * @param debtor
	 */
	void save(Debtor debtor);

	List<Debtor> getAll();

	Debtor get(Integer id);

	void delete(Debtor debtor);

	/**
	 * This method return list debtors those have or have'nt allocated with
	 * clerk for managing. Debtors must have add operation in oper table.
	 * 
	 * @param allocated
	 *            <br>
	 *            <b>true</b> - debtors those allocated this clerk. <br>
	 *            <b>false</b> - debtors those NOT allocated this clerk.
	 * 
	 * @return List <b>debtors</b>
	 */
	List<Debtor> getAllocatedDebtor(Boolean allocated);

	/**
	 * Get debtors for clerk with <b>clerkId</b>. List contains debtors with
	 * open act (debtor have operation with action=9(<i>Open</i>)  and havn't operation
	 * with action=1(<i>Close</i>)
	 * 
	 * @param clerkId
	 * @return List<Debtor>
	 */
	List<Debtor> getDebtors4Clerk(Integer clerkId);
	
	
	

}
