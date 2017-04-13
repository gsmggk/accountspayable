package com.gsmggk.accountspayable.dao4xml.impl;

import org.springframework.stereotype.Repository;

import com.gsmggk.accountspayable.dao4api.IAccountDao;
import com.gsmggk.accountspayable.dao4xml.impl.generic.GenericDaoXMLImpl;
import com.gsmggk.accountspayable.datamodel.Account;

@Repository
public class AccountDaoXMLImpl extends GenericDaoXMLImpl<Account> implements IAccountDao {

	@Override
	public void getPropert4Update(Account modelItem, Account object) {
		modelItem.setAccountName(object.getAccountName());
		modelItem.setDebtorId(object.getDebtorId());	
		modelItem.setMoney(object.getMoney());
	}

	@Override
	protected String getXMLFileName() {
			return "account.xml";
	}

	

}