package com.gsmggk.accountspayable.dao.impl.db;

import java.util.List;

import com.gsmggk.accountspayable.datamodel.Role;

public interface IRoleDao {

	Role insert(Role role);

	Role read(Integer id);

	void update(Role role);

	void delete(Role role);

	List<Role> getAll();

}
