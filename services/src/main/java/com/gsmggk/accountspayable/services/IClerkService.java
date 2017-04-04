package com.gsmggk.accountspayable.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.gsmggk.accountspayable.datamodel.Clerk;
import com.gsmggk.accountspayable.datamodel.Role;

public interface IClerkService {

	@Transactional
	void save(Clerk clerk);

	List<Clerk> getAll();

	Clerk get(Integer id);

	@Transactional
	void delete(Clerk clerk);

	/**
	 * Try Login to Application
	 * 
	 * @param login
	 *            -Login name
	 * @param password
	 *            -password
	 * @return return <b>Clerk</b> if success or generate exceptions
	 */
	Clerk loginCheck(String login, String password);

	@Transactional
	void addRole2Clerk(Clerk clerk, Role role);

}
