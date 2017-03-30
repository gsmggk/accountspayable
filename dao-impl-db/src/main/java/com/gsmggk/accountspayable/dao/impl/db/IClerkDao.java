package com.gsmggk.accountspayable.dao.impl.db;

import java.util.List;

import com.gsmggk.accountspayable.datamodel.Clerk;

public interface IClerkDao {
	Clerk insert(Clerk clerk);

	Clerk read(Integer id);

	void update(Clerk clerk);

	void delete(Clerk clerk);

	List<Clerk> getAll();

	Boolean loginClerk(String login, String password);
}
