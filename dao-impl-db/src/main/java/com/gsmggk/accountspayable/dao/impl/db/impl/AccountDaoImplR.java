package com.gsmggk.accountspayable.dao.impl.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.gsmggk.accountspayable.dao.impl.db.IrAccountDao;
import com.gsmggk.accountspayable.dao.impl.db.impl.gener.GenericDaoImpl;
import com.gsmggk.accountspayable.dao.impl.db.impl.gener.PropertyDao;
import com.gsmggk.accountspayable.datamodel.Account;

@Repository
public class AccountDaoImplR extends GenericDaoImpl<Account> implements IrAccountDao {

	@Override
	public BeanPropertyRowMapper<Account> getRowMapper() {
		BeanPropertyRowMapper<Account> rowMapper = new BeanPropertyRowMapper<Account>(Account.class);
		return rowMapper;
	}

	

	@Override
	public void getInsertPrepareStatement(PreparedStatement ps, Account account) {
		try {
			{
				int i = 1;
				ps.setString(i++, account.getAccountName());
				ps.setBigDecimal(i++, account.getMoney());
				ps.setInt(i++, account.getDebtorId());
				ps.setInt(i++, account.getId());
			}

		}

		catch (Exception e) {
			// FIXME это не тот уровень исключения хотя и работает
	//		e.printStackTrace();
		}
	}

	@Override
	public PropertyDao getPropertyDao() {
		PropertyDao prDao = new PropertyDao();
		String[] fieldsList = new String[] { "account_name", "money", "debtor_id" };
		prDao.setFieldsList(fieldsList);
		prDao.setReadSql("select * from account where id = ? ");
		prDao.setDeleteSql("delete from account where id=");
		prDao.setSelectSql("select * from account");
		String insertSql = String.format("insert into account (%s,%s,%s) values(?,?,?)", (Object[]) fieldsList);
		prDao.setInsertSql(insertSql);
		String updateSql = String.format("update account set %s=?, %s=?,%s=?  where id=?", (Object[]) fieldsList);
		prDao.setUpdateSql(updateSql);
		return prDao;
	}

}
