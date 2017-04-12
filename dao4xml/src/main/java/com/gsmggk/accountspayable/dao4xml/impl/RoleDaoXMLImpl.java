package com.gsmggk.accountspayable.dao4xml.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.gsmggk.accountspayable.dao4api.IRoleDao;
import com.gsmggk.accountspayable.dao4xml.impl.exception.NotSupportedMethodException;
import com.gsmggk.accountspayable.dao4xml.impl.generic.GenericDaoXMLImpl;
import com.gsmggk.accountspayable.dao4xml.impl.wrapper.XmlModelWrapper;
import com.gsmggk.accountspayable.datamodel.Action;
import com.gsmggk.accountspayable.datamodel.Role;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

@Repository
public class RoleDaoXMLImpl extends GenericDaoXMLImpl<Role> implements IRoleDao {

	@Override
	public List<Action> getActions4Role(Integer roleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean chekAction2role(Integer actionId, Integer roleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addAction2Role(Integer actionId, Integer roleId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAction2Role(Integer actionId, Integer roleId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getPropert4Update(Role modelItem, Role object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String getXMLFileName() {
				return "role.xml";
	}
	
}
