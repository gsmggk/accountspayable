package com.gsmggk.accountspayable.dao.impl.db.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gsmggk.accountspayable.dao.impl.db.IGenericDao;
import com.gsmggk.accountspayable.datamodel.AbstractTable;
import com.gsmggk.accountspayable.datamodel.Action;

@Repository
public class GenericDaoImpl<T> implements IGenericDao<T> {
	@Inject
	private JdbcTemplate jdbcTemplate;

	@Override
	public void delete(T model) {

		String arg0 = model.getClass().getSimpleName();
	//	System.out.println(arg0);
		 String DELETE_SQL = "delete from "+arg0+" where id=";
		 jdbcTemplate.update(DELETE_SQL + ((AbstractTable) model).getId());

	}

	@Override
	public T insert(T model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T read(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(T model) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<T> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
