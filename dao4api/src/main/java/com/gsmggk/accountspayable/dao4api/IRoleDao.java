package com.gsmggk.accountspayable.dao4api;

import java.util.List;

import com.gsmggk.accountspayable.dao4api.generic.IGenericDao;
import com.gsmggk.accountspayable.datamodel.Action;
import com.gsmggk.accountspayable.datamodel.Role;


public interface IRoleDao extends IGenericDao<Role>{



	List<Action> getActions4Role(Integer roleId);

	/**
	 * Check link in role2action table for actionId, roleId
	 * 
	 * @param actionId
	 * @param roleId
	 * @return <b>false</b> - if don't exist <b>true</b> - if exist
	 */
	Boolean chekAction2role(Integer actionId, Integer roleId);

	/**
	 * Add link in role2action table for actionId, roleId
	 * 
	 * @param actionId
	 *            - Action id
	 * @param roleId
	 *            - Role id
	 * 
	 */
	void addAction2Role(Integer actionId, Integer roleId);
	
	
	/**
	 * Delete link in role2action table for actionId, roleId
	 * 
	 * @param actionId
	 *            - Action id
	 * @param roleId
	 *            - Role id
	 * 
	 */
	void deleteAction2Role(Integer actionId, Integer roleId);

}
