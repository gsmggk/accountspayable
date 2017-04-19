package com.gsmggk.accountspayable.dao4db.impl.gener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gsmggk.accountspayable.dao4api.filter.Criteria;
import com.gsmggk.accountspayable.dao4api.filter.Criteria.Field;
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
	@Override
	public <R> R read(Object[] objects,Class<R> clazzz) {
		PropertyDao prDao = getPropertyDao();
		final String READ_SQL = prDao.getReadSql();
		try {
			return jdbcTemplate.queryForObject(READ_SQL, objects,clazzz);
		} catch (EmptyResultDataAccessException e) {
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

	/*@Override
	public <R> R search1(Criteria criteria) {
		PropertyDao prDao = getPropertyDao();
		final String SELECT_SEARCH_SQL = prDao.getSelectSql();
		
	List<Field> fields=	criteria.getFields();
		
	ColumnMapRowMapper rowMap=new ColumnMapRowMapper();
	rowMap=getColumnMapRowMapper();
	 Object rs = jdbcTemplate.query(SELECT_SEARCH_SQL,rowMap);
		//TODO make this
		String SELECT_SQL = parseSql(criteria.getSql());
		 * 
		try {
			List<T> rs = jdbcTemplate.query(SELECT_SQL, getSearchRowMapper());
			return rs;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		
		NamedParameterJdbcTemplate npjdbc=new NamedParameterJdbcTemplate(jdbcTemplate);
		
		String sql;
		ColumnMapRowMapper cmrm=new ColumnMapRowMapper();
	
		npjdbc.query(sql, cmrm);
	
		return (R) rs;
		
	}*/

//	protected abstract ColumnMapRowMapper getColumnMapRowMapper();

//	protected abstract RowMapper<T> getSearchRowMapper();
 
	
	

}
