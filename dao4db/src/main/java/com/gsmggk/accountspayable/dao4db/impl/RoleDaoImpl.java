package com.gsmggk.accountspayable.dao4db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.gsmggk.accountspayable.dao4api.IRoleDao;
import com.gsmggk.accountspayable.dao4api.filter.Criteria;
import com.gsmggk.accountspayable.datamodel.Action;
import com.gsmggk.accountspayable.datamodel.Role;

@Repository
public class RoleDaoImpl implements IRoleDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(RoleDaoImpl.class);
//TODO refactor to generik
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
	public void delete(Integer id) {
		final String DELETE_SQL = "delete from role where id=";
		jdbcTemplate.update(DELETE_SQL + id);
	}

	@Override
	public Role read(Integer id) {
		try {
			return jdbcTemplate.queryForObject("select * from role where id = ? ", new Object[] { id },
					new BeanPropertyRowMapper<Role>(Role.class));
		} catch (EmptyResultDataAccessException e) {
			//LOGGER.debug("read role.id={} get empty result", id, e);
			return null;
		}

	}

	@Override
	public List<Role> getAll() {
		try {
			List<Role> rs = jdbcTemplate.query("select * from role ", new BeanPropertyRowMapper<Role>(Role.class));
			return rs;
		} catch (EmptyResultDataAccessException e) {
		//	LOGGER.debug("Table role empty result", e);
			return null;
		}

	}

	@Override
	public List<Action> getActions4Role(Integer roleId) {
		final String SELECT_SQL = "select a.id,a.action_name " + "from role2action as ra "
				+ "JOIN action as a ON (ra.action_id=a.id)" + "where ra.role_id=?";
		try {

			List<Action> rs = jdbcTemplate.query(SELECT_SQL, 
					new Object[] {roleId},
					new BeanPropertyRowMapper<Action>(Action.class));
			return rs;
		} catch (EmptyResultDataAccessException e) {
			LOGGER.debug("getActions4Role .id={} empty result", roleId, e);

			return null;
		}
	}

	@Override
	public Boolean chekAction2role(Integer actionId, Integer roleId) {
		final String SELECT_SQL = "select count(*) from role2action as ra" + " where ra.action_id=? and ra.role_id=?";
		Integer rs = jdbcTemplate.queryForObject(SELECT_SQL, Integer.class, new Object[] { actionId, roleId });
		LOGGER.debug("find {} link, role.id={} action.id={}", rs, roleId, actionId);
		if (rs == 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void addAction2Role(Integer actionId, Integer roleId) {
		final String INSERT_SQL = "insert INTO role2action VALUES(?,?)";

		jdbcTemplate.update(INSERT_SQL, new Object[] { roleId,actionId  });

	}

	@Override
	public void deleteAction2Role(Integer actionId, Integer roleId) {
		final String DELETE_SQL = "delete from role2action as ra where ra.action_id=? and ra.role_id=?";

		jdbcTemplate.update(DELETE_SQL, new Object[] { actionId, roleId });

	}

	@Override
	public <R> R read(Object[] objects, Class<R> clazzz) {
		// TODO Auto-generated method stub
		return null;
	}

	
	

	
}
