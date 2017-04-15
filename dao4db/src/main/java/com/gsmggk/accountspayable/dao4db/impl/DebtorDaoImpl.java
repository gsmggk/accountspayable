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
	private String[] fieldsList = new String[] { "short_name", "full_name", "address", "phones", "jobe", "family",
			"other" };
	private String readSql = "select * from debtor where id = ? ";
	private String deleteSql = "delete from debtor where id=";
	private String selectSql = "select * from debtor";
	private String insertSql = "insert into debtor (%s,%s,%s,%s,%s,%s,%s) values(?,?,?,?,?,?,?)";
	private String updateSql = "update debtor set %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?  where id=?";

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

	@Override
	public List<Debtor> getAllocatedDebtor(Boolean allocated)

	{
		selectSql = "select "
				+ "d.id,d.short_name,d.full_name,d.address,d.phones,d.jobe,d.family,d.other from oper as o "
				+ "join debtor as d on(o.debtor_id=d.id) " + "WHERE o.action_id=9 and %s EXISTS"
				+ "(select o1.id from oper as o1 where o1.debtor_id=o.debtor_id and o1.action_id=11)";
		String prefix = "";
		if (!allocated) {
			prefix = "NOT";
		}
		selectSql = String.format(selectSql, prefix);
		return super.getAll();
	}

}
