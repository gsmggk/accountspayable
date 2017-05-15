package com.gsmggk.accountspayable.dao4xml.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gsmggk.accountspayable.dao4api.IRoleDao;
import com.gsmggk.accountspayable.dao4api.exception.MyUnsupportedOperationException;
import com.gsmggk.accountspayable.dao4xml.impl.generic.GenericDaoXMLImpl;
import com.gsmggk.accountspayable.datamodel.Action;
import com.gsmggk.accountspayable.datamodel.Role;

@Repository
public class RoleDaoXMLImpl extends GenericDaoXMLImpl<Role> implements IRoleDao {

	@Override
	public List<Action> getActions4Role(Integer roleId) {
		 throw new MyUnsupportedOperationException();
	}

	@Override
	public Boolean chekAction2role(Integer actionId, Integer roleId) {
		 throw new MyUnsupportedOperationException();
	}

	@Override
	public void addAction2Role(Integer actionId, Integer roleId) {
		 throw new MyUnsupportedOperationException();
		
	}

	@Override
	public void deleteAction2Role(Integer actionId, Integer roleId) {
		 throw new MyUnsupportedOperationException();
		
	}

	@Override
	public void getPropert4Update(Role modelItem, Role object) {
		modelItem.setRoleName(object.getRoleName());
		modelItem.setLayer(object.getLayer());
		
		
	}

	@Override
	protected String getXMLFileName() {
				return "role.xml";
	}

	
	
}
