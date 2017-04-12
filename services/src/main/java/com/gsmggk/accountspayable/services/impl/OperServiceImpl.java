package com.gsmggk.accountspayable.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.gsmggk.accountspayable.dao4api.IOperDao;
import com.gsmggk.accountspayable.datamodel.Oper;
import com.gsmggk.accountspayable.services.IOperService;

@Service
public class OperServiceImpl implements IOperService{
	@Inject
	private IOperDao operDao;

	@Override
	public void save(Oper oper) {
		if (oper.getId() == null) {

			operDao.insert(oper);
		} else {

			operDao.update(oper);
		}
		
	}

	@Override
	public List<Oper> getAll() {
		
		return operDao.getAll();
	}

	@Override
	public Oper get(Integer id) {
		
		return operDao.read(id);
	}

	@Override
	public void delete(Oper oper) {
		operDao.delete(oper);
		
	}
}
