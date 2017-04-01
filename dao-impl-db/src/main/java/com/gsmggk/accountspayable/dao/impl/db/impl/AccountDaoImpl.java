package com.gsmggk.accountspayable.dao.impl.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.gsmggk.accountspayable.dao.impl.db.IAccountDao;
import com.gsmggk.accountspayable.datamodel.Account;

@Repository
public class AccountDaoImpl implements IAccountDao {
	@Inject
	private JdbcTemplate jdbcTemplate;

	private BeanPropertyRowMapper<Account> rowMapper = new BeanPropertyRowMapper<Account>(Account.class);

	@Override
	public Account insert(Account account) {
		final String INSERT_SQL = "insert into account (" + "account_name," + "summ," + "debtor_id" + ") values(?,?,?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });

				ps.setString(1, account.getAccountName());
				ps.setBigDecimal(2, account.getMoney());
				ps.setInt(3, account.getDebtorId());
				return ps;
			}
		}, keyHolder);

		account.setId(keyHolder.getKey().intValue());

		return account;
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

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(UPDATE_SQL,
						new String[] { "action_name", "summ", "debtor_id" });

				ps.setString(1, account.getAccountName());
				ps.setBigDecimal(2, account.getMoney());
				ps.setInt(3, account.getDebtorId());
				ps.setInt(4, account.getId());

				return ps;
			}
		});
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
