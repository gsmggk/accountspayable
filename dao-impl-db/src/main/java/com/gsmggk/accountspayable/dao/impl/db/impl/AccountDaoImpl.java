package com.gsmggk.accountspayable.dao.impl.db.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gsmggk.accountspayable.dao.impl.db.IAccountDao;
import com.gsmggk.accountspayable.datamodel.Account;
import com.gsmggk.accountspayable.datamodel.Role;

@Repository
public class AccountDaoImpl implements IAccountDao {
	@Inject
	private JdbcTemplate jdbcTemplate;

	@Override
	public Account insert(Account account) {

		return null;
	}

	@Override
	public Account read(Integer id) {
		try {
			return jdbcTemplate.queryForObject("select * from account where id = ? ", new Object[] { id },
					new BeanPropertyRowMapper<Account>(Account.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public void update(Account account) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Account account) {
		final String DELETE_SQL = "delete from account where id=";
		jdbcTemplate.update(DELETE_SQL + account.getId());

	}

	@Override
	public List<Account> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
