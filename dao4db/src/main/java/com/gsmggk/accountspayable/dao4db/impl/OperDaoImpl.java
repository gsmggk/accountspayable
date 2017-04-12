package com.gsmggk.accountspayable.dao4db.impl;

import java.sql.PreparedStatement;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.gsmggk.accountspayable.dao4api.IOperDao;
import com.gsmggk.accountspayable.dao4db.impl.gener.GenericDaoImpl;
import com.gsmggk.accountspayable.dao4db.impl.gener.PropertyDao;
import com.gsmggk.accountspayable.datamodel.Oper;

@Repository
public class OperDaoImpl extends GenericDaoImpl<Oper> implements IOperDao {

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

		}

		catch (Exception e) {
			// FIXME это не тот уровень исключения хотя и работает
			// e.printStackTrace();
		}

	}

	@Override
	public PropertyDao getPropertyDao() {
		PropertyDao prDao = new PropertyDao();
		String[] fieldsList = new String[] { "debtor_id", "clerk_id", "action_id" };
		prDao.setFieldsList(fieldsList);
		prDao.setReadSql("select * from oper where id = ? ");
		prDao.setDeleteSql("delete from oper where id=");
		prDao.setSelectSql("select * from oper");
		String insertSql = String.format("insert into oper (%s,%s,%s) values(?,?,?)", (Object[]) fieldsList);
		prDao.setInsertSql(insertSql);
		String updateSql = String.format("update oper set %s=?, %s=?, %s=? where id=?", (Object[]) fieldsList);
		prDao.setUpdateSql(updateSql);
		return prDao;
	}

	@Override
	public void insertDetal(Oper oper) {
		//TODO тут надо набить детали операции операции
	}

}
