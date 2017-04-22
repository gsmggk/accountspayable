package com.gsmggk.accountspayable.dao4db.impl.gener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import com.gsmggk.accountspayable.dao4api.filter.Criteria;
import com.gsmggk.accountspayable.dao4api.generic.IGenericDao;
import com.gsmggk.accountspayable.datamodel.AbstractTable;

public abstract class GenericDaoImpl<T extends AbstractTable> implements IGenericDao<T> {

	@Inject
	private JdbcTemplate jdbcTemplate;

	protected abstract BeanPropertyRowMapper<T> getRowMapper();

	protected abstract void getInsertPrepareStatement(PreparedStatement ps, T object);

	protected abstract PropertyDao getPropertyDao();

	@Override
	public T read(Integer id) {
		PropertyDao prDao = getPropertyDao();
		final String READ_SQL = prDao.getReadSql();
		try {
			return jdbcTemplate.queryForObject(READ_SQL, new Object[] { id }, getRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	/**
	 * Read one property of model
	 *<b> DON'T USE WITH FOR DATA OBJECTS</b>
	 * @param objects
	 *            - field array
	 * @param clazzz
	 *            - f.e. <i>Integer.Class</i>
	 * @return property value
	 */
	public <R> R read(Object[] objects, Class<R> clazzz) {
		PropertyDao prDao = getPropertyDao();
		final String READ_SQL = prDao.getReadSql();
		try {
			return jdbcTemplate.queryForObject(READ_SQL, objects, clazzz);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * Read one row of model
	 *
	 * @param objects
	 *            - field array
	
	 * @return model
	 */
	public T read(Object[] objects) {
		PropertyDao prDao = getPropertyDao();
		final String READ_SQL = prDao.getReadSql();
		try {
			return jdbcTemplate.queryForObject(READ_SQL, objects, getRowMapper());
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	@Transactional
	@Override
	public void delete(Integer id) {
		final String DELETE_SQL = getPropertyDao().getDeleteSql();
		jdbcTemplate.update(DELETE_SQL + id);
	}

	@Override
	public List<T> getAll() {
		final String SELECT_ALL_SQL = getPropertyDao().getSelectSql();
		try {
			List<T> rs = jdbcTemplate.query(SELECT_ALL_SQL, getRowMapper());
			return rs;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Transactional
	@Override
	public T insert(T object) {
		final String INSERT_SQL = getPropertyDao().getInsertSql();

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

	@Transactional
	@Override
	public void update(T object) {
		PropertyDao prDao = getPropertyDao();
		final String UPDATE_SQL = prDao.getUpdateSql();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(UPDATE_SQL, prDao.getFieldsList());
				getInsertPrepareStatement(ps, object);

				return ps;
			}
		});

	}

	/**
	 * Select table with: order, limit, offset criteria
	 * 
	 * @param criteria
	 * @return List
	 */
	public <R> List<R> getCriteriaRowMapper(Criteria criteria, Object[] objects, RowMapper<R> rm) {
		String sql = criteria.getCriteriaSql();
		try {
			return jdbcTemplate.query(sql, objects, rm);

		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}
