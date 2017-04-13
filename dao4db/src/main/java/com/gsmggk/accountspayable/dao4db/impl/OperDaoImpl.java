package com.gsmggk.accountspayable.dao4db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.inject.Inject;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gsmggk.accountspayable.dao4api.IOperDao;
import com.gsmggk.accountspayable.dao4db.impl.gener.GenericDaoImpl;
import com.gsmggk.accountspayable.dao4db.impl.gener.PropertyDao;
import com.gsmggk.accountspayable.datamodel.Oper;

@Repository
public class OperDaoImpl extends GenericDaoImpl<Oper> implements IOperDao {

	@Inject
	private JdbcTemplate jdbcTemplate;
	private  String[] fieldsList = new String[] { "debtor_id", "clerk_id", "action_id" };
	private  String readSql="select * from oper where id = ? ";
	private  String deleteSql="delete from oper where id=";
	private  String selectSql="select * from oper";
	private  String insertSql="insert into oper (%s,%s,%s) values(?,?,?)";
	private  String updateSql="update oper set %s=?, %s=?, %s=? where id=?";
	
	
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
		
		super.insert(object);
		
		insertDetale(object);
		
		
		return object;
	}

	private void insertDetale(Oper newOper) {
     String sql=String.format("insert into oper_detail (%s,%s,%s,%s) values(?,?,?,?)",(Object[])new String[] {"oper_id","action_date","control_date","oper_desc"});
	jdbcTemplate.update(sql, newOper.getId(),newOper.getActionDate(),newOper.getControlDate(),newOper.getOperDesc());
		
	}

}
