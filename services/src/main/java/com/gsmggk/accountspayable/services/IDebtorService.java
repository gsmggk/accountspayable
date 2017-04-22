package com.gsmggk.accountspayable.services;

import java.util.Date;
import java.util.List;

import com.gsmggk.accountspayable.dao4api.modelmap.DebtorControl;
import com.gsmggk.accountspayable.dao4api.modelmap.DebtorState;
import com.gsmggk.accountspayable.dao4api.params.ParamsDebtors4Boss;
import com.gsmggk.accountspayable.dao4api.params.ParamsDebtors4Clerk;
import com.gsmggk.accountspayable.datamodel.Debtor;

/**
 * @author Gena
 *
 */
public interface IDebtorService {

	/**
	 * Create new debtor or modify existing debtor. For administration use.
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
	 * open act (debtor have operation with action=9(<i>Open</i>) and havn't
	 * operation with action=1(<i>Close</i>)
	 * 
	 * @param clerkId
	 * @return List<Debtor>
	 */
	List<DebtorControl> getDebtors4Clerk(Integer clerkId);

	/**
	 * Generate List debtors with control date for clerk. Futures search by
	 * short debtor name, full debtor name. Query for single control date. Sort
	 * by control date ,debtor full name and sort name
	 * 
	 * @param clerkId
	 *            Integer - clerk id
	 * @param serchShotName
	 *            String - part of short name for search
	 * @param searchFullName
	 *            String - part of full name for search
	 * @param equal2Date
	 *            - Date single date sort
	 * @param sortControl
	 * @param sortShornName
	 * @param sortFullName
	 * @return List<DebtorControl>
	 */
	List<DebtorControl> getDebtors4Clerk(Integer clerkId, String searchShotName, String searchFullName, Date equal2Date,
			Boolean sortControl, Boolean sortShortName, Boolean sortFullName, Integer limit, Integer offset);

	/**
	 * 
	 * * Generate List debtors with control date for clerk. Futures search by
	 * short debtor name, full debtor name. Query for single control date. Sort
	 * by control date ,debtor full name and sort name
	 * 
	 * @param params
	 *            from ParamDebtors4Clerk
	 * @return List<DebtorControl>
	 */
	List<DebtorControl> getDebtors4Clerk(Integer clerkId, ParamsDebtors4Clerk params);

	/**
	 * Create or update debtor and create for new debtor operation with
	 * description. Creation operation=9, Update operation =8
	 * 
	 * @param clerkId
	 *            - creator clerk
	 * @param debtor
	 *            - new debtor
	 * 
	 */
	void saveDebtor(Integer clerkId, Debtor debtor);

	/**
	 * Close debtor from debtor. Modify open operation with action 9 to close
	 * operation with action 1
	 * 
	 * @param clerkId
	 *            - conductor clerk id
	 * @param debtorId
	 *            - debtor id
	 */
	void closeDedtor(Integer clerkId, Integer debtorId);

	/**
	 * Get list debtors with sate active or close. Can search and sort data by
	 * ParamsDebtors4Boss parameters.
	 * 
	 * @param params
	 *            search, sort parameter, limit, offset
	 * @return List<DebtorState>
	 */
	List<DebtorState> getDebtors4Boss(ParamsDebtors4Boss params);

}
