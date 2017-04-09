package com.gsmggk.accountspayable.dao.impl.db.impl.gener;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.gsmggk.accountspayable.dao.impl.db.gener.IGenericDao;
import com.gsmggk.accountspayable.datamodel.AbstractTable;
import com.gsmggk.accountspayable.datamodel.Account;

@Repository
public abstract class GenericDaoImpl<T extends AbstractTable, PK extends Serializable> implements IGenericDao<T, PK> {
	@Inject
	private JdbcTemplate jdbcTemplate;

	public abstract String getReadSql();

	public abstract String getDeleteSql();

	public abstract String getSelectAllSql();

	public abstract String getInsertSql();

	public abstract String getUpdateSql();

	public abstract BeanPropertyRowMapper<T> getRowMapper();

	public abstract void getInsertPrepareStatement(PreparedStatement ps, T object);

	public abstract String[] getUpdateFields();

	@Override
	public T readR(Integer id) {
		final String READ_SQL = getReadSql();
		try {
			return jdbcTemplate.queryForObject(READ_SQL, new Object[] { id }, getRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public void deleteR(T object) {
		final String DELETE_SQL = getDeleteSql();
		jdbcTemplate.update(DELETE_SQL + object.getId());
	}

	@Override
	public List<T> getAllR() {
		final String SELECT_ALL_SQL = getSelectAllSql();
		try {
			List<T> rs = jdbcTemplate.query(SELECT_ALL_SQL, getRowMapper());
			return rs;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public T insertR(T object) {
		final String INSERT_SQL = getInsertSql();

		KeyHolder keyHolder = new GeneratedKeyHolder();

		PreparedStatementCreator psc = new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });
				getInsertPrepareStatement(ps, object);
		
				return ps;

			}

		};

		jdbcTemplate.update(psc, keyHolder);

		object.setId(keyHolder.getKey().intValue());

		return object;

	}

	@Override
	public void updateR(T object) {
		final String UPDATE_SQL = getUpdateSql();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(UPDATE_SQL, getUpdateFields());
				getInsertPrepareStatement(ps, object);
			
				return ps;
			}
		});

	}

}
