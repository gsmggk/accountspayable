package com.gsmggk.accountspayable.dao4db.impl;

import java.sql.PreparedStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.gsmggk.accountspayable.dao4api.IActionDao;
import com.gsmggk.accountspayable.dao4db.impl.gener.GenericDaoImpl;
import com.gsmggk.accountspayable.dao4db.impl.gener.PropertyDao;
import com.gsmggk.accountspayable.datamodel.Action;

@Repository
public class ActionDaoImpl extends GenericDaoImpl<Action> implements IActionDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(ActionDaoImpl.class);	
	
	private String[] fieldsList = new String[] { "action_name", "duration" };
	private String readSql = "select * from action where id = ? ";
	private String deleteSql = "delete from action where id=";
	private String selectSql = "select * from action";
	private String insertSql = "insert into action (%s,%s) values(?,?)";
	private String updateSql = "update action set %s=?, %s=? where id=?";

	@Override
	public BeanPropertyRowMapper<Action> getRowMapper() {
		// FIXME Тут не все в порядке может быть
		BeanPropertyRowMapper<Action> rowMapper = new BeanPropertyRowMapper<Action>(Action.class);
		return rowMapper;
	}

	@Override
	public void getInsertPrepareStatement(PreparedStatement ps, Action object) {

		try {
			{
				int i = 1;
				ps.setString(i++, object.getActionName());
				if (object.getDuration() == null) {
					ps.setNull(i++, java.sql.Types.INTEGER);
				} else {
					ps.setInt(i++, object.getDuration());
				};
			
				
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
	


	



}
