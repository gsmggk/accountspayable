package com.gsmggk.accountspayable.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.gsmggk.accountspayable.datamodel.Role;

public interface IRoleService {

	@Transactional
	void save(Role role);

	List<Role> getAll();

	Role get(Integer id);

	@Transactional
	void delete(Role role);
}
