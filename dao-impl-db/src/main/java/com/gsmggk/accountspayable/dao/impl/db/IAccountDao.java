package com.gsmggk.accountspayable.dao.impl.db;

import java.util.List;

import com.gsmggk.accountspayable.datamodel.Account;

public interface IAccountDao {
	Account insert(Account account);

	Account read(Integer id);

	void update(Account account);

	void delete(Account account);

	List<Account> getAll();
}
