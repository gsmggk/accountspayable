package com.gsmggk.accountspayable.dao.impl.db;

import java.util.List;

import com.gsmggk.accountspayable.datamodel.Clerk;

public interface IClerkDao {
	Clerk insert(Clerk clerk);

	Clerk read(Integer id);

	void update(Clerk clerk);

	void delete(Clerk clerk);

	List<Clerk> getAll();

	/**
	 * query for Clerk with same login name as <b>login</b>
	 * 
	 * @param login
	 *            - login name String
	 * @return Clerk
	 */
	Clerk loginCheck(String login);
}
