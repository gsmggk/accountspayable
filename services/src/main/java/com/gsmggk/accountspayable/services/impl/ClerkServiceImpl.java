package com.gsmggk.accountspayable.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.gsmggk.accountspayable.dao.impl.db.IClerkDao;
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
}
