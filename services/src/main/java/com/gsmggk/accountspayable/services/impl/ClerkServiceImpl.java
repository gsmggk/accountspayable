package com.gsmggk.accountspayable.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gsmggk.accountspayable.dao.impl.db.IClerkDao;
import com.gsmggk.accountspayable.dao.impl.db.except.MyBadLoginNameException;
import com.gsmggk.accountspayable.dao.impl.db.except.MyBadPasswordException;
import com.gsmggk.accountspayable.datamodel.Clerk;
import com.gsmggk.accountspayable.services.IClerkService;

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
		clerkDao.delete(clerk);

	}

	@Override
	public Clerk loginCheck(String login, String password) {
		if (login == null) {
			LOGGER.error("login is null");
			throw new IllegalArgumentException();
		}

		Clerk clerk = new Clerk();
		clerk = clerkDao.loginCheck(login);
		if (clerk != null) {
			// Check password
			if (clerk.getPassword().equals(password)) {
				LOGGER.debug("login is ok");
				return clerk;
			} else {
				LOGGER.error("passwor is wrong");
				throw new MyBadPasswordException("Password is invalid.");
			}
		} else {
			LOGGER.error("loginname is wrong");
			throw new MyBadLoginNameException("Login name is invalid.");

		}
	}
}
