package com.gsmggk.accountspayable.dao.impl.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.gsmggk.accountspayable.dao.impl.db.IrAccountDao;
import com.gsmggk.accountspayable.dao.impl.db.impl.gener.GenericDaoImpl;
import com.gsmggk.accountspayable.datamodel.Account;

@Repository
public class AccountDaoImplR extends GenericDaoImpl<Account, Integer> implements IrAccountDao {

	@Override
	public BeanPropertyRowMapper<Account> getRowMapper() {
		BeanPropertyRowMapper<Account> rowMapper = new BeanPropertyRowMapper<Account>(Account.class);
		return rowMapper;
	}

	@Override
	public String getReadSql() {
		return "select * from account where id = ? ";
	}

	@Override
	public String getDeleteSql() {

		return "delete from account where id=";
	}

	@Override
	public String getSelectAllSql() {

		return "select * from account";
	}

	@Override
	public String getInsertSql() {
		
		return "insert into account (account_name,money,debtor_id) values(?,?,?)";
	}

	@Override
	public String getUpdateSql() {
		
		return "update account set account_name=?, money=?,debtor_id=?  where id=?";
	}
	
	@Override
	public String[] getUpdateFields() {
		return new String[] { "account_name", "money", "debtor_id" };
	}


	@Override
	public void getInsertPrepareStatement(PreparedStatement ps, Account account)  {
		 try {
			{
				int i =1;
				  ps.setString(i++, account.getAccountName());
				  ps.setBigDecimal(i++, account.getMoney()); 
				  ps.setInt(i++,account.getDebtorId()); 
				  ps.setInt(i++, account.getId());
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	
	

	
	

	

}
