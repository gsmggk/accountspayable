package com.gsmggk.accountspayable.dao.impl.db.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gsmggk.accountspayable.dao.impl.db.IDebtorDao;
import com.gsmggk.accountspayable.datamodel.Debtor;

@Repository
public class DebtorDaoImpl implements IDebtorDao {
	@Inject
	private JdbcTemplate jdbcTemplate;

	@Override
	public Debtor insert(Debtor bedtor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Debtor read(Integer id) {
		try {
			return jdbcTemplate.queryForObject("select * from debtor where id = ? ", new Object[] { id },
					new BeanPropertyRowMapper<Debtor>(Debtor.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public void update(Debtor bedtor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Debtor bedtor) {
		final String DELETE_SQL = "delete from bedtor where id=";
		jdbcTemplate.update(DELETE_SQL + bedtor.getId());

	}

	@Override
	public List<Debtor> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
