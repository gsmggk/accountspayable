package com.gsmggk.accountspayable.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.gsmggk.accountspayable.datamodel.Clerk;

public interface IClerkService {

	@Transactional
	void save(Clerk clerk);

	List<Clerk> getAll();

	Clerk get(Integer id);

	@Transactional
	void delete(Clerk clerk);
	
	/**
	 * Try Log in to Application 
	 * @param login -Login name
	 * @param password -password
	 * @return return true is success or generate exceptions
	 */
	Boolean loginClerk(String login,String password);
	

}
