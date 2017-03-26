package com.gsmggk.accountspayable.dao.impl.db;

import java.util.List;

import com.gsmggk.accountspayable.datamodel.Debtor;

public interface IDebtorDao {
	Debtor insert(Debtor bedtor);

	Debtor read(Integer id);

	void update(Debtor bedtor);

	void delete(Debtor bedtor);

	List<Debtor> getAll();
}
