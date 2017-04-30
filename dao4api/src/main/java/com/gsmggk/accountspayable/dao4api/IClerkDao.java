package com.gsmggk.accountspayable.dao4api;

import java.util.List;

import com.gsmggk.accountspayable.dao4api.generic.IGenericDao;
import com.gsmggk.accountspayable.dao4api.modelmap.ClerkRepo;
import com.gsmggk.accountspayable.dao4api.modelmap.SessionModel;
import com.gsmggk.accountspayable.datamodel.Clerk;

public interface IClerkDao  extends IGenericDao<Clerk>{
	

	/**
	 * query for Clerk with same login name as <b>login</b>
	 * 
	 * @param login
	 *            - login name String
	 * @return Clerk
	 */
	Clerk loginCheck(String login);

	/**
	 * Check access clerk to action.
	 * @param clerkId
	 * @param actionId
	 * @return true - if approved <br>
	 * false - if denied 
	 */
	Boolean checkAction4Clerk(Integer clerkId, Integer actionId);
	/**
	 * Get  clerks list allocated to debtor 
	 * @param debtorId -debtor id
	 * @return List<Clerk>
	 */
	List<Clerk> getClerks4Debtor(Integer debtorId);

	List<ClerkRepo> getClerkRepo();

	SessionModel readSession(Integer clerkId);

	void insertSession(SessionModel session);

	void updateSession(SessionModel session);

	
}
