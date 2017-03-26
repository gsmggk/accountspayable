package com.gsmggk.accountspayable.dao.impl.db.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gsmggk.accountspayable.dao.impl.db.IClerkDao;
import com.gsmggk.accountspayable.datamodel.Action;
import com.gsmggk.accountspayable.datamodel.Clerk;

@Repository
public class ClerkDaoImpl implements IClerkDao {
	@Inject
	private JdbcTemplate jdbcTemplate;

	@Override
	public Clerk insert(Clerk clerk) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Clerk clerk) {
		final String DELETE_SQL = "delete from clerk where id=";
		jdbcTemplate.update(DELETE_SQL + clerk.getId());

	}

	@Override
	public List<Clerk> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
