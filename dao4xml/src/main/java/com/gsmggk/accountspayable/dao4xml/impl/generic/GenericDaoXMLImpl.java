package com.gsmggk.accountspayable.dao4xml.impl.generic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.gsmggk.accountspayable.dao4api.generic.IGenericDao;
import com.gsmggk.accountspayable.dao4xml.impl.exception.NotSupportedMethodException;
import com.gsmggk.accountspayable.dao4xml.impl.wrapper.XmlModelWrapper;
import com.gsmggk.accountspayable.datamodel.AbstractTable;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public abstract class GenericDaoXMLImpl<T extends AbstractTable> implements IGenericDao<T> {
	private final XStream xstream = new XStream(new DomDriver());

	public abstract void getPropert4Update(T newObject, T oldObject);

	protected abstract String getXMLFileName();

	@Value("${root.folder}")
	private String rootFolder;

	private File getFile() {
		File file = new File(rootFolder + getXMLFileName());
		return file;
	}

	private void writeNewData(File file, XmlModelWrapper obj) {
		try {
			xstream.toXML(obj, new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public T read(Integer id) {
		File file = getFile();
		XmlModelWrapper<T> wrapper = (XmlModelWrapper<T>) xstream.fromXML(file);
		List<T> models = wrapper.getRows();
		for (T model : models) {
			if (model.getId().equals(id)) {
				return model;
			}
		}
		return null;
	}

	@Override
	public void delete(Integer id) {
		File file = getFile();

		XmlModelWrapper<T> wrapper = (XmlModelWrapper<T>) xstream.fromXML(file);
		List<T> models = wrapper.getRows();
		T found = null;
		for (T model : models) {
			if (model.getId().equals(id)) {
				found = model;
				break;
			}
		}
		if (found != null) {
			models.remove(found);
			writeNewData(file, wrapper);
		}

	}

	@Override
	public List<T> getAll() {
		File file = getFile();
		XmlModelWrapper<T> wrapper = (XmlModelWrapper<T>) xstream.fromXML(file);
		return wrapper.getRows();

	}

	@Override
	public T insert(T object) {
		File file = getFile();

		XmlModelWrapper<T> wrapper = (XmlModelWrapper<T>) xstream.fromXML(file);
		List<T> models = wrapper.getRows();
		Integer lastId = wrapper.getLastId();
		int newId = lastId + 1;

		object.setId(newId);
		models.add(object);

		wrapper.setLastId(newId);
		writeNewData(file, wrapper);
		return object;
	}

	@Override
	public void update(T object) {
		File file = getFile();

		XmlModelWrapper<T> wrapper = (XmlModelWrapper<T>) xstream.fromXML(file);
		List<T> models = wrapper.getRows();
		for (T modelItem : models) {
			if (modelItem.getId().equals(object.getId())) {
				// TODO copy all properties
				getPropert4Update(modelItem, object);
				break;
			}
		}

		writeNewData(file, wrapper);

	}

	
}