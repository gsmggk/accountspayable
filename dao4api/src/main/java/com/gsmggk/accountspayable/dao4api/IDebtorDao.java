package com.gsmggk.accountspayable.dao4api;

import java.util.Date;
import java.util.List;

import com.gsmggk.accountspayable.dao4api.generic.IGenericDao;
import com.gsmggk.accountspayable.dao4api.modelmap.DebtorControl;
import com.gsmggk.accountspayable.dao4api.modelmap.DebtorRepo;
import com.gsmggk.accountspayable.dao4api.modelmap.DebtorState;
import com.gsmggk.accountspayable.dao4api.params.ParamsDebtor;
import com.gsmggk.accountspayable.dao4api.params.ParamsDebtors4Boss;
import com.gsmggk.accountspayable.dao4api.params.ParamsDebtors4Clerk;
import com.gsmggk.accountspayable.datamodel.Debtor;
import com.gsmggk.accountspayable.datamodel.Oper;

/**
 * @author Gena
 *
 */
public interface IDebtorDao extends IGenericDao<Debtor> {
	/**
	 * This method return list debtors those have or have'nt allocated with
	 * clerk for managing. Debtors must have add operation 9 in oper table.
	 * 
	 * @param allocated
	 *            <br>
	 *            <b>true</b> - debtors those allocated this clerk. <br>
	 *            <b>false</b> - debtors those NOT allocated this clerk.
	 * @param ParamsDebtor parameters for search sort debtors
	 * @return List <b>debtors</b>
	 */
	List<Debtor> getAllocatedDebtors(Boolean allocated, ParamsDebtor params);

	Debtor creareDebtor(Debtor debtor, Oper oper);

	void updateDebtor(Debtor debtor, Oper oper);

	/**
	 * Generate List debtors with control date for clerk. Futures search by
	 * short debtor name, full debtor name. Query for single control date. Sort
	 * by control date ,debtor full name and sort name
	 * 
	 * @param clerkId
	 *            Integer - clerk id
	 * 
	 * @param params
	 *            parameter from ParamsDebtors4Clerk
	 * @return List<DebtorControl>
	 */
	List<DebtorControl> getDebtors4Clerk(Integer clerkId, ParamsDebtors4Clerk params);

	/**
	 * Get list debtors with sate active or close. Can search and sort data by
	 * ParamsDebtors4Boss parameters.
	 * 
	 * @param params
	 *            search, sort parameter, limit, offset
	 * @return List<DebtorState>
	 */
	List<DebtorState> getDebtors4Boss(ParamsDebtors4Boss params);
	
	/**Generate debtors report. Get active state debtors with  operations count into the period. 
	 * @param from start period date
	 * @param to end period date
	 * @param params Search/sort parameters
	 * @return List<DebtorRepo>
	 */
	List<DebtorRepo> getDebtorRepo(Date from, Date to, ParamsDebtor params);

}
