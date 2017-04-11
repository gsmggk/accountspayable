package com.gsmggk.accountspayable.services;

import java.util.List;

import com.gsmggk.accountspayable.datamodel.Action;
import com.gsmggk.accountspayable.datamodel.Role;

public interface IRoleService {

	//@Transactional
	void save(Role role);

	List<Role> getAll();

	Role get(Integer id);

//	@Transactional
	void delete(Role role);

	/**
	 * Get all <b>Actions</b> assigned to <b>Role</b>
	 * 
	 * @param roleId
	 *            -Role id
	 * @return List of <b>Actions</b>
	 */
	List<Action> getActions4Role(Integer roleId);

	/**
	 * Check <i>role2action</i> table for exist link role to action and create
	 * link for this if link not present .
	 * 
	 * @param actionId
	 *            - Action id
	 * @param roleId
	 *            -Role id
	 */
	//@Transactional
	void addAction2Role(Integer actionId, Integer roleId);
	
	/**
	 * Delete link in <i>role2action</i> with parameters
	 * 
	 * @param actionId
	 *            - Action id
	 * @param roleId
	 *            -Role id
	 */
//	@Transactional
	void deleteAction2Role(Integer actionId, Integer roleId);
	   
}
