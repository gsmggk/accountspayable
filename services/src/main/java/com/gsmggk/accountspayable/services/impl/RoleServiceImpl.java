package com.gsmggk.accountspayable.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gsmggk.accountspayable.dao4api.IActionDao;
import com.gsmggk.accountspayable.dao4api.IRoleDao;
import com.gsmggk.accountspayable.dao4api.MyTrans;
import com.gsmggk.accountspayable.dao4api.exception.MyDataIntegrityViolationException;
import com.gsmggk.accountspayable.dao4api.exception.MyNotFoundException;
import com.gsmggk.accountspayable.datamodel.Action;
import com.gsmggk.accountspayable.datamodel.Role;
import com.gsmggk.accountspayable.services.IRoleService;

@Service
public class RoleServiceImpl implements IRoleService {
	private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);

	@Inject
	private IActionDao actionDao;

	@Inject
	private IRoleDao roleDao;
	@MyTrans
	@Override
	public void save(Role role) {
		if (role.getId() == null) {
			LOGGER.debug("insert Role");
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
@MyTrans
	@Override
	public void delete(Role role) {
		LOGGER.warn("Delete Role: .id={} .roleName={}", role.getId().toString(), role.getRoleName());
		roleDao.delete(role.getId());
	}

	@Override
	public List<Action> getActions4Role(Integer roleId) {
		return roleDao.getActions4Role(roleId);
	}

	@Override
	@MyTrans
	public void addAction2Role(Integer actionId, Integer roleId) {

		if (!chekAction2Role(actionId, roleId)) {
			try {
				roleDao.addAction2Role(actionId, roleId);
			} catch (MyDataIntegrityViolationException e) {
				throw new MyNotFoundException("Action or role not exists");
			}
			
		}

	}
	@MyTrans
	@Override
	public void deleteAction2Role(Integer actionId, Integer roleId) {
		if (chekAction2Role(actionId, roleId)) {
			roleDao.deleteAction2Role(actionId, roleId);
		}

	}

	@Override
	public Boolean chekAction2Role(Integer actionId, Integer roleId) {
		if (!actionDao.chekExist(actionId)) {
			throw new MyNotFoundException("Action not found");
		}
		if (!roleDao.chekExist(roleId)) {
			throw new MyNotFoundException("Role not found");
		}
		
		return roleDao.chekAction2role(actionId, roleId);
	}

}
