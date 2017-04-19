package com.gsmggk.accountspayable.dao4db.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gsmggk.accountspayable.dao4api.IDebtorDao;
import com.gsmggk.accountspayable.dao4api.IOperDao;
import com.gsmggk.accountspayable.dao4api.filter.Criteria;
import com.gsmggk.accountspayable.dao4db.impl.gener.GenericDaoImpl;
import com.gsmggk.accountspayable.dao4db.impl.gener.PropertyDao;
import com.gsmggk.accountspayable.datamodel.Debtor;
import com.gsmggk.accountspayable.datamodel.Oper;

@Repository
public class DebtorDaoImpl extends GenericDaoImpl<Debtor> implements IDebtorDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(DebtorDaoImpl.class);
	@Inject
	private IOperDao operDao;
	
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

	

	@Transactional
	@Override
	public Debtor creareDebtor(Debtor debtor, Oper oper) {
		try {
			super.insert(debtor);
		} catch (DuplicateKeyException e) {
			LOGGER.error("Create Debtor error:",e);
			throw e;
		}
		
		oper.setDebtorId(debtor.getId());
		
		operDao.insert(oper);	
		
		return debtor;
	}

	@Override
	protected RowMapper<Debtor> getSearchRowMapper() {
		RowMapper<Debtor> mapRow=new RowMapper<Debtor>() {

			@Override
			public Debtor mapRow(ResultSet rs, int rowNum) throws SQLException {
				Debtor debtor = new Debtor();
				debtor.setAddress(rs.);
				return null;
			}
		};
		
		return null;
	}

	
	
	
	
@Override
	public List<Debtor> search(Criteria criteria) {
		
		String searchSelectSql="select" +
" debtor_id,short_name,full_name,max(control_date) as control "+
"from oper as o join debtor as d on (o.debtor_id=d.id) join oper_detail as od on (od.oper_id=o.id)"
+ "where  "
+ " EXISTS(select o1.id from oper as o1 where o1.debtor_id=o.debtor_id and o1.action_id=11)"
+ " and "
+ " not EXISTS(select o1.id from oper as o1 where o1.debtor_id=o.debtor_id and o1.action_id=1)"
+ " AND"
+ "o.clerk_id=91"
+ "and "
+ "d.full_name like '%ров%'"
+ "group by debtor_id,short_name,full_name"
+ "order by control";
		
		
		
		return null;
	}

}
