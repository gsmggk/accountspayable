package com.gsmggk.accountspayable.dao4db.impl.gener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.dao.DuplicateKeyException;
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
import com.gsmggk.accountspayable.dao4db.impl.exeption.MyDuplicateKeyException;
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
	 * Read one field of model
	 *<b> DON'T USE WITH FOR DATA OBJECTS</b>
	 * @param objects
	 *            - field array
	 * @param clazzz
	 *            - f.e.  <i>Integer.Class</i> <i>String.Class</i>
	 * @return property value
	 */
	public <R> R readField(String sql,Object[] objects, Class<R> clazzz) {
	
		try {
			return jdbcTemplate.queryForObject(sql, objects, clazzz);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * Read one row of database object f.e. <i>clerk</i> <i>account</i>
	 * @param sql  select sql string for read one 
	 *
	 * @param objects
	 *            - field array
	 * @param clazzz
	
	 * @return model 
	 */
	public T read(String sql, Object[] objects, Class<T> clazzz) {
		
		try {
			return jdbcTemplate.queryForObject(sql, objects, getRowMapper());
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
       try {
		jdbcTemplate.update(psc, keyHolder);
	} catch (DuplicateKeyException e) {
		throw new MyDuplicateKeyException(e.getMessage(),e);
	} 
		

		object.setId(keyHolder.getKey().intValue());

		return object;

	}

	@Transactional
	@Override
	public void update(T object) {
		PropertyDao prDao = getPropertyDao();
		final String UPDATE_SQL = prDao.getUpdateSql();
		  try {
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(UPDATE_SQL, prDao.getFieldsList());
				getInsertPrepareStatement(ps, object);

				return ps;
			}
		});
		  } catch (DuplicateKeyException e) {
				throw new MyDuplicateKeyException(e.getMessage(),e);
			} 
				
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
