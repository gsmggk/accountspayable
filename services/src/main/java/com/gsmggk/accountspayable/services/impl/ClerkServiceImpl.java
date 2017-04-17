package com.gsmggk.accountspayable.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gsmggk.accountspayable.dao4api.IClerkDao;
import com.gsmggk.accountspayable.datamodel.Clerk;
import com.gsmggk.accountspayable.datamodel.Role;
import com.gsmggk.accountspayable.services.IClerkService;
import com.gsmggk.accountspayable.services.impl.exceptions.MyBadLoginNameException;
import com.gsmggk.accountspayable.services.impl.exceptions.MyBadPasswordException;
import com.gsmggk.accountspayable.services.util.CurrentLayer;

@Service
public class ClerkServiceImpl implements IClerkService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ClerkServiceImpl.class);

	@Inject
	private IClerkDao clerkDao;

	@Override
	public void save(Clerk clerk) {
		if (clerk.getId() == null) {
			LOGGER.debug("insert clerk");
			clerkDao.insert(clerk);
		} else {
			LOGGER.debug("update clerk");
			clerkDao.update(clerk);
		}

	}

	@Override
	public List<Clerk> getAll() {

		return clerkDao.getAll();
	}

	@Override
	public Clerk get(Integer id) {

		return clerkDao.read(id);
	}

	@Override
	public void delete(Clerk clerk) {
		LOGGER.warn("Delete Clerk: .id={} .clerkLoginName={}", clerk.getId().toString(), clerk.getClerkLoginName());
		clerkDao.delete(clerk);

	}

	@Override
	public Clerk loginCheck(String login, String password) throws IllegalArgumentException {
		if (login == null) {
			LOGGER.warn("login is null");
			throw new IllegalArgumentException();
		}

		Clerk clerk = new Clerk();
		clerk = clerkDao.loginCheck(login);
		if (clerk != null) {
			// Check password
			if (clerk.getPassword().equals(password)) {
				LOGGER.debug("login is ok");
				// Write CurrentLayer properties
				CurrentLayer.setClerkId(clerk.getId());
				CurrentLayer.setClerkFullName(clerk.getClerkFullName());
				return clerk;
			} else {
				LOGGER.warn("passwor is wrong");
				throw new MyBadPasswordException("Password is invalid.");
			}
		} else {
			LOGGER.warn("loginname is wrong");
			throw new MyBadLoginNameException("Login name is invalid.");

		}
	}

	@Override
	public void addRole2Clerk(Clerk clerk, Role role) {
		clerk.setRoleId(role.getId());
		clerkDao.update(clerk);
	}
}
