package com.gsmggk.accountspayable.dao4xml.impl;

import org.springframework.stereotype.Repository;

import com.gsmggk.accountspayable.dao4api.IActionDao;
import com.gsmggk.accountspayable.dao4xml.impl.generic.GenericDaoXMLImpl;
import com.gsmggk.accountspayable.datamodel.Action;
@Repository
public class ActionDaoXMLImpl extends GenericDaoXMLImpl<Action> implements IActionDao{

	@Override
	public void getPropert4Update(Action newObject, Action oldObject) {
		newObject.setActionName(oldObject.getActionName());
		newObject.setDuration(oldObject.getDuration());
		
	}

	@Override
	protected String getXMLFileName() {
		return "action.xml";
	}

	

}
