package com.gsmggk.accountspayable.services;

import java.util.List;

import com.gsmggk.accountspayable.datamodel.Debtor;

public interface IDebtorService {

	
	void save(Debtor debtor);

	List<Debtor> getAll();

	Debtor get(Integer id);
	
	void delete(Debtor debtor);
	
}
