package com.gsmggk.accountspayable.dao4xml.impl;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gsmggk.accountspayable.dao4api.IClerkDao;
import com.gsmggk.accountspayable.dao4api.exception.MyUnsupportedOperationException;
import com.gsmggk.accountspayable.dao4api.modelmap.ClerkRepo;
import com.gsmggk.accountspayable.dao4api.modelmap.SessionModel;
import com.gsmggk.accountspayable.dao4xml.impl.generic.GenericDaoXMLImpl;
import com.gsmggk.accountspayable.dao4xml.impl.wrapper.XmlModelWrapper;
import com.gsmggk.accountspayable.datamodel.Clerk;

@Repository
public class ClerkDaoXMLImpl extends GenericDaoXMLImpl<Clerk> implements IClerkDao {

	@Override
	public Clerk loginCheck(String login) {
		File file = getFile();
		XmlModelWrapper<Clerk> wrapper = (XmlModelWrapper<Clerk>) xstream.fromXML(file);
		List<Clerk> models = wrapper.getRows();
		Clerk clerk = models.stream().filter(x -> x.getClerkLoginName().equals(login)).findFirst().orElse(null);
		return clerk;
	}

	@Override
	public void getPropert4Update(Clerk modelItem, Clerk object) {
		modelItem.setClerkLoginName(object.getClerkLoginName());
		modelItem.setClerkFullName(object.getClerkFullName());
		modelItem.setPassword(object.getPassword());
		modelItem.setRoleId(object.getRoleId());
	}

	@Override
	protected String getXMLFileName() {
		return "clerk.xml";
	}

	@Override
	public Boolean checkAction4Clerk(Integer clerkId, Integer actionId) {
		throw new MyUnsupportedOperationException();
	}

	@Override
	public List<Clerk> getClerks4Debtor(Integer debtorId) {
		throw new MyUnsupportedOperationException();
	}

	@Override
	public List<ClerkRepo> getClerkRepo() {
		throw new MyUnsupportedOperationException();
	}

	@Override
	public SessionModel readSession(Integer clerkId) {
		throw new MyUnsupportedOperationException();
	}

	@Override
	public void insertSession(SessionModel session) {
		throw new MyUnsupportedOperationException();

	}

	@Override
	public void updateSession(SessionModel session) {
		throw new MyUnsupportedOperationException();

	}

	@Override
	public Boolean chekDebtor4Clerk(Integer clerkId, Integer debtorId) {
		throw new MyUnsupportedOperationException();
	}

}
