package com.gsmggk.accountspayable.dao4db.impl;

import java.sql.PreparedStatement;
import java.util.List;

import javax.inject.Inject;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gsmggk.accountspayable.dao4api.IDebtorDao;
import com.gsmggk.accountspayable.dao4db.impl.gener.GenericDaoImpl;
import com.gsmggk.accountspayable.dao4db.impl.gener.PropertyDao;
import com.gsmggk.accountspayable.datamodel.Debtor;

@Repository
public class DebtorDaoImpl extends GenericDaoImpl<Debtor> implements IDebtorDao {

	@Override
	public BeanPropertyRowMapper<Debtor> getRowMapper() {
		BeanPropertyRowMapper<Debtor> rowMapper = new BeanPropertyRowMapper<Debtor>(Debtor.class);
		return rowMapper;

	}

	@Override
	public void getInsertPrepareStatement(PreparedStatement ps, Debtor debtor) {
		try {
			{
				int i = 1;
				ps.setString(i++, debtor.getShortName());
				ps.setString(i++, debtor.getShortName());
				ps.setString(i++, debtor.getAddress());
				ps.setString(i++, debtor.getPhones());
				ps.setString(i++, debtor.getJobe());
				ps.setString(i++, debtor.getFamily());
				ps.setString(i++, debtor.getOther());
				
				
				ps.setInt(i++, debtor.getId());
				
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
		String[] fieldsList = new String[] { "short_name", "full_name", "address","phones","jobe","family","other" };
		prDao.setFieldsList(fieldsList);
		prDao.setReadSql("select * from debtor where id = ? ");
		prDao.setDeleteSql("delete from debtor where id=");
		prDao.setSelectSql("select * from debtor");
		String insertSql = String.format("insert into debtor (%s,%s,%s,%s,%s,%s,%s) values(?,?,?,?,?,?,?)", (Object[]) fieldsList);
		prDao.setInsertSql(insertSql);
		String updateSql = String.format("update debtor set %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?  where id=?", (Object[]) fieldsList);
		prDao.setUpdateSql(updateSql);
		return prDao;

	}
	

}
