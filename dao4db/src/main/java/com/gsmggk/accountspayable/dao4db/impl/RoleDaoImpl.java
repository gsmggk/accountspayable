package com.gsmggk.accountspayable.dao4db.impl;

import java.sql.PreparedStatement;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gsmggk.accountspayable.dao4api.IRoleDao;
import com.gsmggk.accountspayable.dao4api.filter.Criteria;
import com.gsmggk.accountspayable.dao4db.impl.exeption.MyNotFoundException;
import com.gsmggk.accountspayable.dao4db.impl.gener.GenericDaoImpl;
import com.gsmggk.accountspayable.dao4db.impl.gener.PropertyDao;
import com.gsmggk.accountspayable.datamodel.Action;
import com.gsmggk.accountspayable.datamodel.Role;

@Repository
public class RoleDaoImpl extends GenericDaoImpl<Role> implements IRoleDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(RoleDaoImpl.class);

	private static final String[] fieldsList = new String[] { "role_name","layer", "id" };
	private static final String readSql = "select * from role where id = ? ";
	private static final String deleteSql = "delete from role where id=";
	private static final String selectSql = "select * from role";
	private static final String insertSql = "insert into role (%s,%s) values(?,?)";
	private static final String updateSql = "update role set %s=?,%s=? where id=?";

	@Override
	public BeanPropertyRowMapper<Role> getRowMapper() {
		// FIXME Тут не все в порядке может быть
		BeanPropertyRowMapper<Role> rowMapper = new BeanPropertyRowMapper<Role>(Role.class);
		return rowMapper;
	}

	@Override
	public void getInsertPrepareStatement(PreparedStatement ps, Role object) {

		try {
			{
				int i = 1;
				ps.setString(i++, object.getRoleName());
				ps.setString(i++, object.getLayer());
				
				ps.setInt(i++, object.getId());
			}
		} catch (Exception e) {
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
		String insertSql = String.format(RoleDaoImpl.insertSql, (Object[]) fieldsList);
		prDao.setInsertSql(insertSql);
		String updateSql = String.format(RoleDaoImpl.updateSql, (Object[]) fieldsList);
		prDao.setUpdateSql(updateSql);
		return prDao;
	}

	@Override
	public List<Action> getActions4Role(Integer roleId) {
		final String SELECT_SQL = "select a.id,a.action_name,a.duration " + "from role2action as ra "
				+ "JOIN action as a ON (ra.action_id=a.id)";// + "where
															// ra.role_id=?";
		Criteria cr = new Criteria();
		cr.setSql(SELECT_SQL);
		cr.addFilter("ra.role_id=?", "where", null);

		List<Action> rs = super.getCriteriaRowMapper(cr, new Object[] { roleId },
				new BeanPropertyRowMapper<Action>(Action.class));
		return rs;

	}

	@Override
	public Boolean chekAction2role(Integer actionId, Integer roleId) {
		
	
		
		String sql = "select count(*) from role2action as ra" + " where ra.action_id=? and ra.role_id=?";
		Object[] objects = new Object[] { actionId, roleId };
		Integer rs = super.readField(sql,objects, Integer.class);

		if (rs == 0) {
			return false;
		} else {
			return true;
		}
	}

	
		
	
	

	@Inject
	private JdbcTemplate jdbcTemplate;

	@Override
	@Transactional
	public void addAction2Role(Integer actionId, Integer roleId) {
		final String INSERT_SQL = "insert INTO role2action VALUES(?,?)";
		jdbcTemplate.update(INSERT_SQL, new Object[] { roleId, actionId });
		LOGGER.info("Action:{} ADD to Role:{}", actionId, roleId);
	}

	@Override
	@Transactional
	public void deleteAction2Role(Integer actionId, Integer roleId) {
		final String DELETE_SQL = "delete from role2action as ra where ra.action_id=? and ra.role_id=?";

		jdbcTemplate.update(DELETE_SQL, new Object[] { actionId, roleId });
		LOGGER.warn("Action:{} DELETE from Role:{}", actionId, roleId);

	}

}
