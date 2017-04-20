package com.gsmggk.accountspayable.dao4db.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import com.gsmggk.accountspayable.dao4api.maps.DebtorControl;
import com.gsmggk.accountspayable.dao4db.impl.gener.GenericDaoImpl;
import com.gsmggk.accountspayable.dao4db.impl.gener.PropertyDao;
import com.gsmggk.accountspayable.dao4db.mapper.DebtorControlRowMapper;
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
				ps.setString(i++, debtor.getFullName());
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
			LOGGER.error("Create Debtor error:", e);
			throw e;
		}

		oper.setDebtorId(debtor.getId());

		operDao.insert(oper);

		return debtor;
	}

	@Override
	public List<DebtorControl> getDebtors4Clerk(Integer clerkId, String searchShotName, String searchFullName,
			Date equal2Date, Boolean sortControl, Boolean sortShortName, Boolean sortFullName,Integer limit
			,Integer offset) {

		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("select ");
		sqlBuilder.append("o.debtor_id,d.short_name,d.full_name,max(control_date) as control ");
		sqlBuilder.append("from oper as o ");
		sqlBuilder.append("join debtor as d on (o.debtor_id=d.id) ");
		sqlBuilder.append("join oper_detail as od on (od.oper_id=o.id)");
		sqlBuilder.append("where ");
		sqlBuilder.append("EXISTS(select o1.id from oper as o1 where o1.debtor_id=o.debtor_id and o1.action_id=11)");
		sqlBuilder.append("and ");
		sqlBuilder.append("not EXISTS(select o1.id from oper as o1 where o1.debtor_id=o.debtor_id and o1.action_id=1)");

		Criteria criteria = new Criteria();
		criteria.setSql(sqlBuilder.toString());

		criteria.addFilter("o.clerk_id=?", "AND", null);
		Object[] newObj = new Object[] { clerkId };
		
		if (searchShotName != null && !searchShotName.isEmpty()) {
			criteria.addFilter("d.short_name LIKE ?", "AND", null);
		      newObj = appendValue(newObj,searchShotName );
		}
		if (searchFullName != null && !searchFullName.isEmpty()) {
			criteria.addFilter("d.full_name LIKE ?", "AND", null);
			 newObj = appendValue(newObj,searchFullName );
		}
		if (equal2Date != null) {
			criteria.addFilter("od.control_date = ?", "AND", null);
			 newObj = appendValue(newObj,equal2Date );
		}
		criteria.addFilter("debtor_id, short_name, full_name", "group by", null);

		if (sortControl!=null){
		switch (sortControl.toString()) {
		case "true":
			criteria.addSort("control", "asc");
			break;
		case "false":
			criteria.addSort("control", "desc");
			break;
		default:
			break;
		}}
		if (sortShortName!=null){
			switch (sortShortName.toString()) {
			case "true":
				criteria.addSort("short_name", "asc");
				break;
			case "false":
				criteria.addSort("short_name", "desc");
				break;
			default:
				break;
			}}
		if (sortFullName!=null){
			switch (sortFullName.toString()) {
			case "true":
				criteria.addSort("full_name", "asc");
				break;
			case "false":
				criteria.addSort("full_name", "desc");
				break;
			default:
				break;
			}}
		if(limit!=null&&limit>0){
			criteria.setLimit(limit);
			}
		if (offset!=null&&offset>0){
			criteria.setOffset(offset);
			}
		String sql = criteria.getCriteriaSql();
		System.out.println(sql);

		DebtorControlRowMapper rm = new DebtorControlRowMapper();
		return getCriteriaRowMapper(criteria, newObj, rm);
	}

	private Object[] appendValue(Object[] obj, Object newObj) {
		ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList(obj));
		temp.add(newObj);
		return temp.toArray();
	}

}
