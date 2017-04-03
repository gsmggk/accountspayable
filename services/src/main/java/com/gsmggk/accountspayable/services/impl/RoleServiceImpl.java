package com.gsmggk.accountspayable.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gsmggk.accountspayable.dao.impl.db.IRoleDao;
import com.gsmggk.accountspayable.datamodel.Role;
import com.gsmggk.accountspayable.services.IRoleService;

@Service
public class RoleServiceImpl implements IRoleService {
	private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);

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
			LOGGER.debug("add Role");
			roleDao.insert(role);
		} else {
			LOGGER.debug("update Role");
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
		LOGGER.debug("delete Role");
		roleDao.delete(role);
	}

}
