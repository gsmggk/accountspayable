package com.gsmggk.accountspayable.dao4api;

import java.util.Date;
import java.util.List;

import com.gsmggk.accountspayable.dao4api.generic.IGenericDao;
import com.gsmggk.accountspayable.dao4api.modelmap.DebtorControl;
import com.gsmggk.accountspayable.dao4api.modelmap.DebtorState;
import com.gsmggk.accountspayable.dao4api.params.ParamsDebtors4Boss;
import com.gsmggk.accountspayable.dao4api.params.ParamsDebtors4Clerk;
import com.gsmggk.accountspayable.datamodel.Debtor;
import com.gsmggk.accountspayable.datamodel.Oper;

/**
 * @author Gena
 *
 */
public interface IDebtorDao extends IGenericDao<Debtor> {

	List<Debtor> getAllocatedDebtor(Boolean allocated);

	Debtor creareDebtor(Debtor debtor, Oper oper);

	void updateDebtor(Debtor debtor, Oper oper);

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
	 * @param offset
	 * @param limit
	 * @return List<DebtorControl>
	 */
	List<DebtorControl> getDebtors4Clerk(Integer clerkId, String searchShotName, String searchFullName, Date equal2Date,
			Boolean sortControl, Boolean sortShortName, Boolean sortFullName, Integer limit, Integer offset);

	/**
	 *   Generate List debtors with control date for clerk. Futures search by
	 * short debtor name, full debtor name. Query for single control date. Sort
	 * by control date ,debtor full name and sort name
	 * 
	 * @param clerkId
	 *            Integer - clerk id
	 * 
	 * @param params parameter from ParamsDebtors4Clerk
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

	
	
}
