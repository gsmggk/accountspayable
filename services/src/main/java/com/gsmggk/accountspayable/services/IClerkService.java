package com.gsmggk.accountspayable.services;

import java.util.List;

import com.gsmggk.accountspayable.dao4api.modelmap.ClerkRepo;
import com.gsmggk.accountspayable.datamodel.Action;
import com.gsmggk.accountspayable.datamodel.Clerk;
import com.gsmggk.accountspayable.datamodel.Role;
import com.gsmggk.accountspayable.services.impl.exceptions.MyBadLoginNameException;

public interface IClerkService {

	void save(Clerk clerk);

	List<Clerk> getAll();

	Clerk get(Integer id);

	void delete(Clerk clerk);

	/**
	 * Try Login to Application.
	 * And save clerk id, full name into CurrentLayer property.
	 * @param login
	 *            -Login name
	 * @param password
	 *            -password
	 * @return return <b>Clerk</b> if success or generate exceptions
	 * 
	 */
	Clerk loginCheck(String login, String password) throws IllegalArgumentException;

	void addRole2Clerk(Clerk clerk, Role role);
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

	/**
	 * Generate clerks report - count of debtors allocated to the clerk 
	 * 
	 * @return
	 */
	List<ClerkRepo> getClerkRepo();

	/**
	 * Set  password hash to clerk table
	 * @param clerkId clerk id
	 * @param password string
	 */
	void allocatePassword(Integer clerkId, String password);

	
	/**
	 * Save session into DB
	 * @param clerkId
	 * @param token
	 */
	void addSession(Integer clerkId, String token);
	/**
	 * Get action list for clerk 
	 * @param clerkId clerk id
	 * @return List<Action>
	 */
	List<Action> getActions4Clerk(Integer clerkId);

	/**
	 * Check debtor assigned to clerk.
	 * @param clerkId clerk id
	 * @param debtorId debtor id
	 * @return true if assigned <br>
	 * false if not
	 */
	Boolean chekDebtor4Clerk(Integer clerkId, Integer debtorId);
}
