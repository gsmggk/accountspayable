package com.gsmggk.accountspayable.dao4db.impl;

import java.sql.PreparedStatement;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gsmggk.accountspayable.dao4api.IOperDao;
import com.gsmggk.accountspayable.dao4db.impl.gener.GenericDaoImpl;
import com.gsmggk.accountspayable.dao4db.impl.gener.PropertyDao;
import com.gsmggk.accountspayable.datamodel.Oper;

@Repository
public class OperDaoImpl extends GenericDaoImpl<Oper> implements IOperDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(OperDaoImpl.class);
	
	@Inject
	private JdbcTemplate jdbcTemplate;
	
	private String[] fieldsList = new String[] { "debtor_id", "clerk_id", "action_id" };
	private String readSql = "select * from oper where id = ? ";
	private String deleteSql = "delete from oper where id=";
	private String selectSql = "select * from oper";
	private String insertSql = "insert into oper (%s,%s,%s) values(?,?,?)";
	private String updateSql = "update oper set %s=?, %s=?, %s=? where id=?";

	@Override
	public BeanPropertyRowMapper<Oper> getRowMapper() {
		// FIXME Тут не все в порядке может быть
		BeanPropertyRowMapper<Oper> rowMapper = new BeanPropertyRowMapper<Oper>(Oper.class);
		return rowMapper;
	}

	@Override
	public void getInsertPrepareStatement(PreparedStatement ps, Oper object) {

		try {
			{
				int i = 1;
				ps.setInt(i++, object.getDebtorId());
				ps.setInt(i++, object.getClerkId());
				ps.setInt(i++, object.getActionId());

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
		String insertSql = String.format(this.insertSql, (Object[]) fieldsList);
		prDao.setInsertSql(insertSql);
		String updateSql = String.format(this.updateSql, (Object[]) fieldsList);
		prDao.setUpdateSql(updateSql);
		return prDao;
	}

	@Transactional
	@Override
	public Oper insert(Oper object) {
		try {
			super.insert(object);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Add oper error:", e);
			throw e;
		}
		try {
			insertDetale(object);
		} catch (Exception e) {

			e.printStackTrace();
			LOGGER.error("Add oper detale error:", e);
			throw e;
		}
		return object;

	}

	private void insertDetale(Oper newOper) {
		String sql = String.format("insert into oper_detail (%s,%s,%s,%s) values(?,?,?,?)",
				(Object[]) new String[] { "oper_id", "action_date", "control_date", "oper_desc" });
		jdbcTemplate.update(sql, newOper.getId(), newOper.getActionDate(), newOper.getControlDate(),
				newOper.getOperDesc());

	}

	@Override
	public Boolean checkAllocated(Integer debtorID, Integer clerkId) {
		readSql = "select count(*) from oper as o where o.action_id=11 and o.debtor_id=? and o.clerk_id=? ";
		Integer res = super.read(new Object[] { debtorID, clerkId }, Integer.class);
		if (res == 1) {
			return true;
		} else {
			return false;
		}
	}

	

}
