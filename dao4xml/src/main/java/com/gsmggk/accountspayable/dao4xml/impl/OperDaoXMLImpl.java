package com.gsmggk.accountspayable.dao4xml.impl;

import org.springframework.stereotype.Repository;

import com.gsmggk.accountspayable.dao4api.IOperDao;
import com.gsmggk.accountspayable.dao4xml.impl.generic.GenericDaoXMLImpl;
import com.gsmggk.accountspayable.datamodel.Oper;

@Repository
public class OperDaoXMLImpl extends GenericDaoXMLImpl<Oper> implements IOperDao {

	@Override
	public void getPropert4Update(Oper modelItem, Oper object) {
		// TODO Auto-generated method stub

	}

	@Override
	protected String getXMLFileName() {
		return "oper.xml";
	}

	@Override
	public void insertDetal(Oper oper) {
		// TODO Auto-generated method stub
		
	}

}
