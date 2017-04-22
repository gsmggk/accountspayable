package com.gsmggk.accountspayable.dao4db.impl;

import java.sql.PreparedStatement;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.gsmggk.accountspayable.dao4api.IClerkDao;
import com.gsmggk.accountspayable.dao4api.IRoleDao;
import com.gsmggk.accountspayable.dao4api.filter.Criteria;
import com.gsmggk.accountspayable.dao4db.impl.gener.GenericDaoImpl;
import com.gsmggk.accountspayable.dao4db.impl.gener.PropertyDao;
import com.gsmggk.accountspayable.datamodel.Clerk;

@Repository
public class ClerkDaoImpl extends GenericDaoImpl<Clerk> implements IClerkDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(ClerkDaoImpl.class);

	
	@Inject
	private IRoleDao roleDao;
	@Inject
	private JdbcTemplate jdbcTemplate;

	private String[] fieldsList = new String[] { "clerk_login_name", "password", "role_id", "clerk_full_name" };
	private String readSql = "select * from clerk where id = ? ";
	private String deleteSql = "delete from clerk where id=";
	private String selectSql = "select * from clerk";
	private String insertSql = "insert into clerk (%s,%s,%s,%s) values(?,?,?,?)";
	private String updateSql = "update clerk set %s=?, %s=?, %s=?, %s=?  where id=?";

	@Override
	public BeanPropertyRowMapper<Clerk> getRowMapper() {
		BeanPropertyRowMapper<Clerk> rowMapper = new BeanPropertyRowMapper<Clerk>(Clerk.class);
		return rowMapper;
	}

	@Override
	public void getInsertPrepareStatement(PreparedStatement ps, Clerk clerk) {
		try {
			{
				int i = 1;
				ps.setString(i++, clerk.getClerkLoginName());
				ps.setString(i++, clerk.getPassword());
				if (clerk.getRoleId() == null) {
					ps.setNull(i++, java.sql.Types.INTEGER);
				} else {
					ps.setInt(i++, clerk.getRoleId());
				}
				;

				ps.setString(i++, clerk.getClerkFullName());

				ps.setInt(i++, clerk.getId());

			}

		}

		catch (Exception e) {
			// FIXME это не тот уровень исключения хотя и работает
			// e.printStackTrace();
		}

	}

	@Override
	public PropertyDao getPropertyDao() {
		PropertyDao prDao = new PropertyDao();
		prDao.setFieldsList(fieldsList);
		prDao.setReadSql(readSql);
		prDao.setDeleteSql(deleteSql);
		prDao.setSelectSql(selectSql);
		String insertSql = String.format(this.insertSql, (Object[]) fieldsList);
		prDao.setInsertSql(insertSql);
		String updateSql = String.format(this.updateSql, (Object[]) fieldsList);
		prDao.setUpdateSql(updateSql);
		return prDao;

	}

	@Override
	public Clerk loginCheck(String login) {
		try {
			return jdbcTemplate.queryForObject("select * from clerk where clerk_login_name = ? ",
					new Object[] { login }, new BeanPropertyRowMapper<Clerk>(Clerk.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		/*
		readSql="select * from clerk where clerk_login_name = ? ";
		
       Clerk clerk=new Clerk();
       clerk=super.read(new Object[] { login },Clerk.class);
       return clerk;*/
	}

	@Override
	public Boolean checkAction4Clerk(Integer clerkId, Integer actionId) {
		Clerk clerk=read(clerkId);
		
		return roleDao.chekAction2role(actionId, clerk.getRoleId());
	}

	@Override
	public List<Clerk> getClerks4Debtor(Integer debtorId) {
		String sql="select c.id,c.clerk_login_name,c.password,c.role_id,c.clerk_full_name from oper o join clerk c on(o.clerk_id=c.id) where o.action_id=11";
				Criteria criteria=new Criteria();
	  criteria.setSql(sql);
	  criteria.addFilter("o.debtor_id=?", "AND", debtorId);
	  return super.getCriteriaRowMapper(criteria, criteria.getObjects(), getRowMapper());
	}

	

	

}
