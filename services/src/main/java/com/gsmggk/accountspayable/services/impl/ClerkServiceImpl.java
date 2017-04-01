package com.gsmggk.accountspayable.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.gsmggk.accountspayable.dao.impl.db.IClerkDao;
import com.gsmggk.accountspayable.dao.impl.db.except.MyBadLoginNameException;
import com.gsmggk.accountspayable.dao.impl.db.except.MyBadPasswordException;
import com.gsmggk.accountspayable.datamodel.Clerk;
import com.gsmggk.accountspayable.services.IClerkService;

@Service
public class ClerkServiceImpl implements IClerkService {
	@Inject
	private IClerkDao clerkDao;

	@Override
	public void save(Clerk clerk) {
		if (clerk.getId() == null) {

			clerkDao.insert(clerk);
		} else {

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
		if (login==null) {
			throw new IllegalArgumentException();
		}

		Clerk clerk = new Clerk();
		clerk = clerkDao.loginCheck(login);
		if (clerk != null) {
			// Check password
			if (clerk.getPassword().equals(password)) {
				return clerk;
			} else {
				throw new MyBadPasswordException("Password is invalid.");
			}
		} else {
			throw new MyBadLoginNameException("Login name is invalid.");

		}
	}
}
