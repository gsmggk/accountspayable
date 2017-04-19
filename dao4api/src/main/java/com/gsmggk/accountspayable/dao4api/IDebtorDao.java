package com.gsmggk.accountspayable.dao4api;

import java.util.Date;
import java.util.List;

import com.gsmggk.accountspayable.dao4api.filter.Criteria;
import com.gsmggk.accountspayable.dao4api.generic.IGenericDao;
import com.gsmggk.accountspayable.dao4api.maps.DebtorControl;
import com.gsmggk.accountspayable.datamodel.Debtor;
import com.gsmggk.accountspayable.datamodel.Oper;

public interface IDebtorDao extends IGenericDao<Debtor> {

	List<Debtor> getAllocatedDebtor(Boolean allocated);

	Debtor creareDebtor(Debtor debtor, Oper oper);

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
			Boolean sortControl, Boolean sortShortName, Boolean sortFullName);
}
