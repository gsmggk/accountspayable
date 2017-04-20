package com.gsmggk.accountspayable.services;

import java.util.List;

import com.gsmggk.accountspayable.datamodel.Action;
import com.gsmggk.accountspayable.datamodel.Role;

public interface IRoleService {

	
	void save(Role role);

	List<Role> getAll();

	Role get(Integer id);


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
	
	void addAction2Role(Integer actionId, Integer roleId);
	
	/**
	 * Delete link in <i>role2action</i> with parameters
	 * 
	 * @param actionId
	 *            - Action id
	 * @param roleId
	 *            -Role id
	 */

	void deleteAction2Role(Integer actionId, Integer roleId);
	
	/**
	 * Check link in role2action table for actionId, roleId
	 * 
	 * @param actionId
	 * @param roleId
	 * @return <b>false</b> - if don't exist <b>true</b> - if exist
	 */
	Boolean chekAction2Role(Integer actionId, Integer roleId);
	   
}
