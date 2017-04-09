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

import com.gsmggk.accountspayable.dao.impl.db.IClerkDao;
import com.gsmggk.accountspayable.datamodel.Clerk;

@Repository
public class ClerkDaoImpl implements IClerkDao {
	@Inject
	private JdbcTemplate jdbcTemplate;

	@Override
	public Clerk insert(Clerk clerk) {

		final String INSERT_SQL = "insert into clerk (clerk_login_name,password,role_id,clerk_full_name) values(?,?,?,?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });
				ps.setString(1, clerk.getClerkLoginName());
				ps.setString(2, clerk.getPassword());
		// FIXME roleId can be not null
				ps.setNull(3, java.sql.Types.INTEGER);
				ps.setString(4, clerk.getClerkFullName());
				return ps;
			}
		}, keyHolder);

		clerk.setId(keyHolder.getKey().intValue());

		return clerk;
	}

	@Override
	public Clerk read(Integer id) {
		try {
			return jdbcTemplate.queryForObject("select * from clerk where id = ? ", new Object[] { id },
					new BeanPropertyRowMapper<Clerk>(Clerk.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public void update(Clerk clerk) {

		final String UPDATE_SQL = "update clerk set" + " clerk_login_name=?," + "password=?," + "role_id=?,"
				+ "clerk_full_name=?" + " where id=?";

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(UPDATE_SQL,
						new String[] { "clerk_login_name", "password", "role_id", "clerk_full_name", "id" });
				ps.setString(1, clerk.getClerkLoginName());
				ps.setString(2, clerk.getPassword());
				ps.setInt(3, clerk.getRoleId());
				ps.setString(4, clerk.getClerkFullName());
				ps.setInt(5, clerk.getId());
				return ps;
			}
		});

	}

	@Override
	public void delete(Clerk clerk) {
		final String DELETE_SQL = "delete from clerk where id=";
		jdbcTemplate.update(DELETE_SQL + clerk.getId());

	}

	@Override
	public List<Clerk> getAll() {
		try {
			List<Clerk> rs = jdbcTemplate.query("select * from clerk ", new BeanPropertyRowMapper<Clerk>(Clerk.class));
			return rs;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Clerk loginCheck(String login) {
		try {
			return jdbcTemplate.queryForObject("select * from clerk where clerk_login_name = ? ",
					new Object[] { login }, new BeanPropertyRowMapper<Clerk>(Clerk.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}
