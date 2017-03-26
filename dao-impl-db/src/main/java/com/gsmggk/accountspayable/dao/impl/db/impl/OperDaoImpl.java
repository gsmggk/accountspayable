package com.gsmggk.accountspayable.dao.impl.db.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gsmggk.accountspayable.dao.impl.db.IOperDao;
import com.gsmggk.accountspayable.datamodel.Debtor;
import com.gsmggk.accountspayable.datamodel.Oper;

@Repository
public class OperDaoImpl implements IOperDao {
	@Inject
	private JdbcTemplate jdbcTemplate;

	@Override
	public Oper insert(Oper oper) {
		// TODO не все так просто
		return null;
	}

	@Override
	public Oper read(Integer id) {
		try {
			return jdbcTemplate.queryForObject("select * from oper where id = ? ", new Object[] { id },
					new BeanPropertyRowMapper<Oper>(Oper.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public void update(Oper oper) {
		// TODO не все так просто
	}

	@Override
	public void delete(Oper oper) {
		// TODO Удаление не все так просто
		
		final String DELETE_SQL = "delete from oper where id=";
		jdbcTemplate.update(DELETE_SQL + oper.getId());

	}

	@Override
	public List<Oper> getAll() {
		// TODO не все так просто
		return null;
	}

}
