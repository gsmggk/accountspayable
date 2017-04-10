package com.gsmggk.accountspayable.dao4db.impl.gener;

import org.springframework.jdbc.core.RowMapper;

public class PropertyDao {
	private String[] fieldsList;
	private String readSql;
	private String deleteSql;
	private String selectSql;
	private String insertSql;
	private String updateSql;
	 
	
	public String[] getFieldsList() {
		return fieldsList;
	}
	public void setFieldsList(String[] fieldsList) {
		this.fieldsList = fieldsList;
	}
	public String getReadSql() {
		return readSql;
	}
	public void setReadSql(String readSql) {
		this.readSql = readSql;
	}
	public String getDeleteSql() {
		return deleteSql;
	}
	public void setDeleteSql(String deleteSql) {
		this.deleteSql = deleteSql;
	}
	public String getSelectSql() {
		return selectSql;
	}
	public void setSelectSql(String selectSql) {
		this.selectSql = selectSql;
	}
	public String getInsertSql() {
		return insertSql;
	}
	public void setInsertSql(String insertSql) {
		this.insertSql = insertSql;
	}
	public String getUpdateSql() {
		return updateSql;
	}
	public void setUpdateSql(String updateSql) {
		this.updateSql = updateSql;
	}

}
