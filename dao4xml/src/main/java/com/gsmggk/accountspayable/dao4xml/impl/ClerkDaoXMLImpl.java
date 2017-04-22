package com.gsmggk.accountspayable.dao4xml.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gsmggk.accountspayable.dao4api.IClerkDao;
import com.gsmggk.accountspayable.dao4api.filter.Criteria;
import com.gsmggk.accountspayable.dao4xml.impl.exception.NotSupportedMethodException;
import com.gsmggk.accountspayable.dao4xml.impl.generic.GenericDaoXMLImpl;
import com.gsmggk.accountspayable.datamodel.Clerk;

@Repository
public class ClerkDaoXMLImpl extends GenericDaoXMLImpl<Clerk> implements IClerkDao {

	@Override
	public Clerk loginCheck(String login) {
		throw new NotSupportedMethodException();
	}

	@Override
	public void getPropert4Update(Clerk modelItem, Clerk object) {
		// TODO Auto-generated method stub

	}

	@Override
	protected String getXMLFileName() {
		return "clerk.xml";
	}

	

}
