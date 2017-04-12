package com.gsmggk.accountspayable.dao4db.impl;

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

import com.gsmggk.accountspayable.dao4api.IActionDao;
import com.gsmggk.accountspayable.datamodel.Action;

@Repository
public class ActionDaoImpl implements IActionDao {
	@Inject
	private JdbcTemplate jdbcTemplate;

	@Override
	public Action insert(Action action) {
		final String INSERT_SQL = "insert into action (action_name,duration) values(?,?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });
				ps.setString(1, action.getActionName());
				if (action.getDuration() == null) {
					ps.setNull(2, java.sql.Types.INTEGER);
				} else {
					ps.setInt(2, action.getDuration());
				}
				return ps;
			}
		}, keyHolder);

		action.setId(keyHolder.getKey().intValue());

		return action;
	}

	@Override
	public Action read(Integer id) {
		try {
			return jdbcTemplate.queryForObject("select * from action where id = ? ", new Object[] { id },
					new BeanPropertyRowMapper<Action>(Action.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public void update(Action action) {
		final String UPDATE_SQL = "update action set action_name=?,duration=? where id=?";

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(UPDATE_SQL, new String[] { "action_name", "id" });
				ps.setString(1, action.getActionName());
				if (action.getDuration() == null) {
					ps.setNull(2, java.sql.Types.INTEGER);
				} else {
					ps.setInt(2, action.getDuration());
				}
				ps.setInt(3, action.getId());
				return ps;
			}
		});

	}

	@Override
	public void delete(Action action) {
		final String DELETE_SQL = "delete from action where id=";
		jdbcTemplate.update(DELETE_SQL + action.getId());

	}

	@Override
	public List<Action> getAll() {
		try {
			List<Action> rs = jdbcTemplate.query("select * from action ",
					new BeanPropertyRowMapper<Action>(Action.class));
			return rs;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

}
