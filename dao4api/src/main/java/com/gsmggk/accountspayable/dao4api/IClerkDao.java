package com.gsmggk.accountspayable.dao4api;

import com.gsmggk.accountspayable.dao4api.generic.IGenericDao;
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
}
