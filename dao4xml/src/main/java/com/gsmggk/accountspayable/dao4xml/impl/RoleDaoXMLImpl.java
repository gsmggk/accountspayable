package com.gsmggk.accountspayable.dao4xml.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.gsmggk.accountspayable.dao4api.IRoleDao;
import com.gsmggk.accountspayable.dao4xml.impl.exception.NotSupportedMethodException;
import com.gsmggk.accountspayable.dao4xml.impl.wrapper.XmlModelWrapper;
import com.gsmggk.accountspayable.datamodel.Action;
import com.gsmggk.accountspayable.datamodel.Role;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

@Repository
public class RoleDaoXMLImpl implements IRoleDao {
	private final XStream xstream = new XStream(new DomDriver());

	@Value("${root.folder}")
	private String rootFolder;

	@Override
	public Role insert(Role role) {
		File file = getFile();

		XmlModelWrapper<Role> wrapper = (XmlModelWrapper<Role>) xstream.fromXML(file);
		List<Role> roles = wrapper.getRows();
		Integer lastId = wrapper.getLastId();
		int newId = lastId + 1;

		role.setId(newId);
		roles.add(role);

		wrapper.setLastId(newId);
		writeNewData(file, wrapper);
		return role;

	}

	@Override
	public Role read(Integer id) {

		File file = getFile();

		XmlModelWrapper<Role> wrapper = (XmlModelWrapper<Role>) xstream.fromXML(file);
		List<Role> roles = wrapper.getRows();
		for (Role role : roles) {
			if (role.getId().equals(id)) {
				return role;
			}
		}
		return null;

	}

	@Override
	public void update(Role role) {

		File file = getFile();

		XmlModelWrapper<Role> wrapper = (XmlModelWrapper<Role>) xstream.fromXML(file);
		List<Role> roles = wrapper.getRows();
		for (Role roleItem : roles) {
			if (roleItem.getId().equals(role.getId())) {
				// TODO copy all properties
				roleItem.setRoleName(role.getRoleName());
				
				break;
			}
		}

		writeNewData(file, wrapper);

	}

	@Override
	public void delete(Role role) {
		File file = getFile();

		XmlModelWrapper<Role> wrapper = (XmlModelWrapper<Role>) xstream.fromXML(file);
		List<Role> roles = wrapper.getRows();
		Role found = null;
		for (Role r : roles) {
			if (r.getId().equals(role.getId())) {
				found = r;
				break;
			}
		}
		if (found != null) {
			roles.remove(found);
			writeNewData(file, wrapper);
		}

	}

	@Override
	public List<Role> getAll() {
		File file = getFile();
		XmlModelWrapper<Role> wrapper = (XmlModelWrapper<Role>) xstream.fromXML(file);
		return wrapper.getRows();

	}

	@Override
	public List<Action> getActions4Role(Integer roleId) {
		throw new NotSupportedMethodException();

	}

	@Override
	public Boolean chekAction2role(Integer actionId, Integer roleId) {
		throw new NotSupportedMethodException();

	}

	@Override
	public void addAction2Role(Integer actionId, Integer roleId) {
		throw new NotSupportedMethodException();

	}

	@Override
	public void deleteAction2Role(Integer actionId, Integer roleId) {
		throw new NotSupportedMethodException();

	}

	private void writeNewData(File file, XmlModelWrapper obj) {
		try {
			xstream.toXML(obj, new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private File getFile() {
		File file = new File(rootFolder + "role.xml");
		return file;
	}

}
