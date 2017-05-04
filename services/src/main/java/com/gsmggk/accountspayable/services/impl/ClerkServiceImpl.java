package com.gsmggk.accountspayable.services.impl;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gsmggk.accountspayable.dao4api.IClerkDao;
import com.gsmggk.accountspayable.dao4api.IRoleDao;
import com.gsmggk.accountspayable.dao4api.modelmap.ClerkRepo;
import com.gsmggk.accountspayable.dao4api.modelmap.SessionModel;
import com.gsmggk.accountspayable.datamodel.Action;
import com.gsmggk.accountspayable.datamodel.Clerk;
import com.gsmggk.accountspayable.datamodel.Role;
import com.gsmggk.accountspayable.services.IClerkService;
import com.gsmggk.accountspayable.services.impl.exceptions.MyBadLoginNameException;
import com.gsmggk.accountspayable.services.impl.exceptions.MyBadPasswordException;
import com.gsmggk.accountspayable.services.impl.exceptions.MyNotDistributedClerkException;
import com.gsmggk.accountspayable.services.impl.exceptions.MyNotFoundException;
import com.gsmggk.accountspayable.services.util.CurrentLayer;
import com.gsmggk.accountspayable.services.util.PasswordHash;

@Service
public class ClerkServiceImpl implements IClerkService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ClerkServiceImpl.class);

	@Inject
	private IClerkDao clerkDao;
	@Inject
	private IRoleDao roleDao;

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
		clerkDao.delete(clerk.getId());

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
			Boolean flag = null;

			try {
				flag = PasswordHash.validatePassword(password, clerk.getPassword());
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} catch (InvalidKeySpecException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}

			if (flag) {
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

	@Override
	public Boolean checkAction4Clerk(Integer clerkId, Integer actionId) {

		return clerkDao.checkAction4Clerk(clerkId, actionId);
	}

	@Override
	public List<Clerk> getClerks4Debtor(Integer debtorId) {

		return clerkDao.getClerks4Debtor(debtorId);
	}

	@Override
	public List<ClerkRepo> getClerkRepo() {

		return clerkDao.getClerkRepo();
	}

	@Override
	public void allocatePassword(Integer clerkId, String password) {
		String passwordHash = null;
		try {
			passwordHash = PasswordHash.createHash(password);
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("Password allocated error to clerk id:{} error:{} ", clerkId, e.getMessage());
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			LOGGER.error("Password allocated error to clerk id:{} error:{} ", clerkId, e.getMessage());
			e.printStackTrace();
		}

		Clerk clerk = clerkDao.read(clerkId);
		clerk.setPassword(passwordHash);
		LOGGER.info("Password allocated to clerk id:{}", clerkId);
		clerkDao.update(clerk);

	}

	@Override
	public void addSession(Integer clerkId, String token) {
		SessionModel session;
		session = clerkDao.readSession(clerkId);
		if (session == null) {
			 session = new SessionModel();
			LOGGER.debug("insert session");
			session.setId(clerkId);
			session.setValue(token);
			clerkDao.insertSession(session);
		} else {
			LOGGER.debug("update session");
			session.setValue(token);
			clerkDao.updateSession(session);
		}
	}

	@Override
	public List<Action> getActions4Clerk(Integer clerkId) {
		Clerk clerk =clerkDao.read(clerkId);
		if (clerk==null){throw new MyNotFoundException("Clerk not found");}
		if (clerk.getRoleId()==null){throw new MyNotDistributedClerkException("Clerk not allowed to role");}
		return roleDao.getActions4Role(clerk.getRoleId());
	}

	@Override
	public Boolean chekDebtor4Clerk(Integer clerkId, Integer debtorId) {
		
		return clerkDao.chekDebtor4Clerk( clerkId,  debtorId);
	}
	
}
