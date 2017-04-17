package com.gsmggk.accountspayable.services;

import java.util.List;

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

}
