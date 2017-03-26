package com.gsmggk.accountspayable.dao.impl.db.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gsmggk.accountspayable.dao.impl.db.IAccountDao;
import com.gsmggk.accountspayable.datamodel.Account;
import com.gsmggk.accountspayable.datamodel.Action;
import com.gsmggk.accountspayable.datamodel.Role;

@Repository
public class AccountDaoImpl implements IAccountDao {
	@Inject
	private JdbcTemplate jdbcTemplate;

	private BeanPropertyRowMapper<Account> rowMapper = new BeanPropertyRowMapper<Account>(Account.class);

	private String psc;

	@Override
	public Account insert(Account account) {

		return null;
	}

	@Override
	public Account read(Integer id) {
		try {
			return jdbcTemplate.queryForObject("select * from account where id = ? ", new Object[] { id }, rowMapper);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public void update(Account account) {
		final String UPDATE_SQL = "update account set account_name=?, summ=?,debtor_id=?  where id=?";

		jdbcTemplate.update(psc);
	}

	@Override
	public void delete(Account account) {
		final String DELETE_SQL = "delete from account where id=";
		jdbcTemplate.update(DELETE_SQL + account.getId());

	}

	@Override
	public List<Account> getAll() {
		try {
			List<Account> rs = jdbcTemplate.query("select * from action ", rowMapper);
			return rs;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}
