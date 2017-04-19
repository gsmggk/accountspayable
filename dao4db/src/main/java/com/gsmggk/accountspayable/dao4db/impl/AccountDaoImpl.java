package com.gsmggk.accountspayable.dao4db.impl;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.gsmggk.accountspayable.dao4api.IAccountDao;
import com.gsmggk.accountspayable.dao4api.filter.Criteria;
import com.gsmggk.accountspayable.dao4db.impl.gener.GenericDaoImpl;
import com.gsmggk.accountspayable.dao4db.impl.gener.PropertyDao;
import com.gsmggk.accountspayable.datamodel.Account;

@Repository
public class AccountDaoImpl extends GenericDaoImpl<Account> implements IAccountDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountDaoImpl.class);	

	private String[] fieldsList = new String[] { "account_name", "summ", "debtor_id" };
	private String readSql = "select * from account where id = ? ";
	private String deleteSql = "delete from account where id=";
	private String selectSql = "select * from account";
	private String insertSql = "insert into account (%s,%s,%s) values(?,?,?)";
	private String updateSql = "update account set %s=?, %s=?,%s=? where id=?";
	
	private ColumnMapRowMapper rowMapper;
	
	@Override
	public BeanPropertyRowMapper<Account> getRowMapper() {
		// FIXME Тут не все в порядке может быть
		BeanPropertyRowMapper<Account> rowMapper = new BeanPropertyRowMapper<Account>(Account.class);
		return rowMapper;
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
	public void getInsertPrepareStatement(PreparedStatement ps, Account object) {

		try {
			{
				int i = 1;
				ps.setString(i++, object.getAccountName());
				ps.setBigDecimal(i++, object.getSumm());
				
				ps.setInt(i++, object.getDebtorId());
				
				ps.setInt(i++, object.getId());
			}
		} catch (Exception e) {
			// FIXME это не тот уровень исключения хотя и работает
			// e.printStackTrace();
		}

	}

	

	
	@Inject
	private JdbcTemplate jdbcTemplate;


	@Override
	public List<Map<String, Object>> search(Integer id) {
		
		final String SELECT_ALL_SQL = "select * from account as a where a.debtor_id=?";
		ColumnMapRowMapper rowMap=new ColumnMapRowMapper();
	//	rowMap.mapRow(rs, rowNum)
		
		try {			
			List<Map<String,Object>> rs = jdbcTemplate.query(SELECT_ALL_SQL,new Object[]{ id},rowMap);
			
			return rs;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		
		
	}

	
	

	
	
}
