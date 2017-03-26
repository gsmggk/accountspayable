package com.gsmggk.accountspayable.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.gsmggk.accountspayable.dao.impl.db.IRoleDao;
import com.gsmggk.accountspayable.datamodel.Role;
import com.gsmggk.accountspayable.services.IRoleService;

@Service
public class RoleServiceImpl implements IRoleService {
	/*
	 * @Value("${key1}") private String key1;
	 * 
	 * @Value("${key2}") private Integer key2;
	 */

	@Inject
	private IRoleDao roleDao;

	@Override
	public void save(Role role) {
		if (role.getId() == null) {

			roleDao.insert(role);
		} else {

			roleDao.update(role);
		}

	}

	@Override
	public List<Role> getAll() {
		return roleDao.getAll();
	}

	@Override
	public Role get(Integer id) {

		return roleDao.read(id);
	}

	@Override
	public void delete(Role role) {
		roleDao.delete(role);
	}

}
