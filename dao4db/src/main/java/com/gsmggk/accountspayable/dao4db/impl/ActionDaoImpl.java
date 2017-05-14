package com.gsmggk.accountspayable.dao4db.impl;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.gsmggk.accountspayable.dao4api.IActionDao;
import com.gsmggk.accountspayable.dao4api.language.LanguageContainer;
import com.gsmggk.accountspayable.dao4db.impl.gener.GenericDaoImpl;
import com.gsmggk.accountspayable.dao4db.impl.gener.PropertyDao;
import com.gsmggk.accountspayable.datamodel.Action;

@Repository
public class ActionDaoImpl extends GenericDaoImpl<Action> implements IActionDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(ActionDaoImpl.class);

	private static final String[] fieldsList = new String[] { "action_name_".concat(LanguageContainer.LNG_PREFIX),
			"action_desc_".concat(LanguageContainer.LNG_PREFIX), "duration" };

	private static final String selectSql = "select "
			.concat(Action.ACTION_TABLE_ALIAS + "." + Action.ACTION_ID_FIELD_NAME + ", ")
			.concat(Action.ACTION_TABLE_ALIAS + "." + Action.ACTION_ACTION_NAME_FIELD_NAME + "_")
			.concat(LanguageContainer.LNG_PREFIX).concat(" AS " + Action.ACTION_ACTION_NAME_FIELD_NAME + ", ")
			.concat(Action.ACTION_TABLE_ALIAS + "." + Action.ACTION_ACTION_DESC_FIELD_NAME + "_")
			.concat(LanguageContainer.LNG_PREFIX).concat(" AS " + Action.ACTION_ACTION_DESC_FIELD_NAME + ", ")
			.concat(Action.ACTION_TABLE_ALIAS + "." + Action.ACTION_DURATION_FIELD_NAME + " ")
			.concat("from " + Action.ACTION_TABLE_NAME + " " + Action.ACTION_TABLE_ALIAS);
	private static final String readSql = selectSql.concat(" where id = ? ");
	private static final String deleteSql = "delete from action where id=";
	private static final String insertSql = "insert into action (%s,%s,%s) values(?,?,?)";
	private static final String updateSql = "update action set %s=?, %s=?, %s=? where id=?";

	@Override
	public BeanPropertyRowMapper<Action> getRowMapper() {
		BeanPropertyRowMapper<Action> rowMapper = new BeanPropertyRowMapper<Action>(Action.class);
		return rowMapper;
	}

	@Override
	public void getInsertPrepareStatement(PreparedStatement ps, Action object) {

		try {
			{
				int i = 1;
				ps.setString(i++, object.getActionName());
				ps.setString(i++, object.getActionDesc());

				if (object.getDuration() == null) {
					ps.setNull(i++, java.sql.Types.INTEGER);
				} else {
					ps.setInt(i++, object.getDuration());
				}
				;

				ps.setInt(i++, object.getId());
			}
		} catch (Exception e) {

		}

	}

	@Override
	public PropertyDao getPropertyDao() {
		String[] list = fieldsList;
		String language = LanguageContainer.getLanguage();

		PropertyDao prDao = new PropertyDao();
		List<String> convFieldsList = new ArrayList<>();
		for (String str : list) {
			convFieldsList.add(str.replaceAll(LanguageContainer.LNG_PREFIX, language));
		}
		list = convFieldsList.toArray(new String[convFieldsList.size()]);

		prDao.setFieldsList(list);
		prDao.setReadSql(readSql.replaceAll(LanguageContainer.LNG_PREFIX, language));
		prDao.setDeleteSql(deleteSql);
		prDao.setSelectSql(selectSql.replaceAll(LanguageContainer.LNG_PREFIX, language));

		String updateSql = String.format(ActionDaoImpl.updateSql, (Object[]) list);
		prDao.setUpdateSql(updateSql);

		String insertSql = String.format(ActionDaoImpl.insertSql, (Object[]) list);
		prDao.setInsertSql(insertSql);

		return prDao;
	}

	

}
