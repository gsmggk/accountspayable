package com.gsmggk.accountspayable.dao4db.impl;

import java.sql.PreparedStatement;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gsmggk.accountspayable.dao4api.IClerkDao;
import com.gsmggk.accountspayable.dao4api.IRoleDao;
import com.gsmggk.accountspayable.dao4api.filter.Criteria;
import com.gsmggk.accountspayable.dao4api.modelmap.ClerkRepo;
import com.gsmggk.accountspayable.dao4api.modelmap.SessionModel;
import com.gsmggk.accountspayable.dao4db.impl.gener.GenericDaoImpl;
import com.gsmggk.accountspayable.dao4db.impl.gener.PropertyDao;
import com.gsmggk.accountspayable.dao4db.mapper.ClerkRepoRowMapper;
import com.gsmggk.accountspayable.datamodel.Clerk;

@Repository
public class ClerkDaoImpl extends GenericDaoImpl<Clerk> implements IClerkDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(ClerkDaoImpl.class);

	@Inject
	private IRoleDao roleDao;
	@Inject
	private JdbcTemplate jdbcTemplate;

	private static final String[] fieldsList = new String[] { "clerk_login_name", "password", "role_id",
			"clerk_full_name" };
	private static final String readSql = "select * from clerk where id = ? ";
	private static final String deleteSql = "delete from clerk where id=";
	private static final String selectSql = "select * from clerk";
	private static final String insertSql = "insert into clerk (%s,%s,%s,%s) values(?,?,?,?)";
	private static final String updateSql = "update clerk set %s=?, %s=?, %s=?, %s=?  where id=?";

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
		}

	}

	@Override
	public PropertyDao getPropertyDao() {
		PropertyDao prDao = new PropertyDao();
		prDao.setFieldsList(fieldsList);
		prDao.setReadSql(readSql);
		prDao.setDeleteSql(deleteSql);
		prDao.setSelectSql(selectSql);
		String insertSql = String.format(ClerkDaoImpl.insertSql, (Object[]) fieldsList);
		prDao.setInsertSql(insertSql);
		String updateSql = String.format(ClerkDaoImpl.updateSql, (Object[]) fieldsList);
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

	}

	@Override
	public Boolean chekDebtor4Clerk(Integer clerkId, Integer debtorId) {
		final String sql = "select count(*) from oper o where o.action_id=11 and o.clerk_id=? and o.debtor_id=?";
		Object[] objects = new Object[] { clerkId, debtorId };
		Integer rs = readField(sql, objects, Integer.class);

		if (rs == 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public Boolean checkAction4Clerk(Integer clerkId, Integer actionId) {
		Clerk clerk = read(clerkId);

		return roleDao.chekAction2role(actionId, clerk.getRoleId());
	}

	@Override
	public List<Clerk> getClerks4Debtor(Integer debtorId) {
		String sql = "select c.id,c.clerk_login_name,c.password,c.role_id,c.clerk_full_name from oper o join clerk c on(o.clerk_id=c.id) where o.action_id=11";
		Criteria criteria = new Criteria();
		criteria.setSql(sql);
		criteria.addFilter("o.debtor_id=?", "AND", debtorId);
		return super.getCriteriaRowMapper(criteria, criteria.getObjects(), getRowMapper());
	}

	@Override
	public List<ClerkRepo> getClerkRepo() {
		String sql = "select c.id,c.clerk_full_name,count(clerk_id) as debtors from clerk c join oper o on (o.clerk_id=c.id and o.action_id=11) group by c.id,clerk_login_name,clerk_full_name";
		Criteria criteria = new Criteria();
		criteria.setSql(sql);
		ClerkRepoRowMapper rm = new ClerkRepoRowMapper();

		return getCriteriaRowMapper(criteria, null, rm);

	}

	@Override
	public SessionModel readSession(Integer clerkId) {
		try {
			return jdbcTemplate.queryForObject("select * from session where id = ? ", new Object[] { clerkId },
					new BeanPropertyRowMapper<SessionModel>(SessionModel.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	@Transactional
	public void insertSession(SessionModel session) {
		String sql = String.format("insert into session (%s,%s) values(?,?)",
				(Object[]) new String[] { "id", "value" });
		jdbcTemplate.update(sql, session.getId(), session.getValue());

	}

	@Override
	@Transactional
	public void updateSession(SessionModel session) {
		String sql = String.format("update session set  %s=?  where id=?", (Object[]) new String[] { "value", "id" });
		jdbcTemplate.update(sql, session.getValue(), session.getId());

	}

}
