package com.gsmggk.accountspayable.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.gsmggk.accountspayable.datamodel.Debtor;

public interface IDebtorService {

	@Transactional
	void save(Debtor debtor);

	List<Debtor> getAll();

	Debtor get(Integer id);

	@Transactional
	void delete(Debtor debtor);
}
