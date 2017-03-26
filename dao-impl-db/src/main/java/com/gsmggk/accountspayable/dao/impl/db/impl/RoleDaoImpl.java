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

import com.gsmggk.accountspayable.dao.impl.db.IRoleDao;
import com.gsmggk.accountspayable.datamodel.Role;

@Repository
public class RoleDaoImpl implements IRoleDao {

	@Inject
	private JdbcTemplate jdbcTemplate;

	@Override
	public Role insert(Role role) {

		final String INSERT_SQL = "insert into role (role_name) values(?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });
				ps.setString(1, role.getRoleName());
				return ps;
			}
		}, keyHolder);

		role.setId(keyHolder.getKey().intValue());

		return role;

	}

	@Override
	public void update(Role role) {
		final String UPDATE_SQL = "update role set role_name=? where id=?";

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(UPDATE_SQL, new String[] { "role_name", "id" });
				ps.setString(1, role.getRoleName());
				ps.setInt(2, role.getId());
				return ps;
			}
		});

	}

	@Override
	public void delete(Role role) {
		final String DELETE_SQL = "delete from role where id=";
		jdbcTemplate.update(DELETE_SQL + role.getId());
	}

	@Override
	public Role read(Integer id) {
		try {
			return jdbcTemplate.queryForObject("select * from role where id = ? ", new Object[] { id },
					new BeanPropertyRowMapper<Role>(Role.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	@Override
	public List<Role> getAll() {
		try {
			List<Role> rs = jdbcTemplate.query("select * from role ", new BeanPropertyRowMapper<Role>(Role.class));
			return rs;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

}