package com.gsmggk.accountspayable.dao4xml.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gsmggk.accountspayable.dao4api.IActionDao;
import com.gsmggk.accountspayable.dao4api.filter.Criteria;
import com.gsmggk.accountspayable.dao4xml.impl.exception.NotSupportedMethodException;
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
