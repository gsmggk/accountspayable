package com.gsmggk.accountspayable.services.impl;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gsmggk.accountspayable.dao.impl.db.IRoleDao;
import com.gsmggk.accountspayable.datamodel.Role;
import com.gsmggk.accountspayable.services.IRoleService;

@Service
public class RoleServiceImpl implements IRoleService {
	@Value("${key1}")
	private String key1;
	@Value("${key2}")
	private Integer key2;
	
	@Inject
	private IRoleDao iRoleDao;

	@Override
	public void insert(Role role) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
