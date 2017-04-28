package com.gsmggk.accountspayable.dao4db.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.gsmggk.accountspayable.dao4api.modelmap.DebtorControl;
import com.gsmggk.accountspayable.dao4api.modelmap.DebtorRepo;
import com.gsmggk.accountspayable.dao4api.modelmap.DebtorState;
import com.gsmggk.accountspayable.dao4api.params.ParamsDebtor;
import com.gsmggk.accountspayable.dao4api.params.ParamsDebtors4Boss;
import com.gsmggk.accountspayable.dao4api.params.ParamsDebtors4Clerk;
import com.gsmggk.accountspayable.dao4db.impl.gener.GenericDaoImpl;
import com.gsmggk.accountspayable.dao4db.impl.gener.PropertyDao;
import com.gsmggk.accountspayable.dao4db.mapper.DebtorControlRowMapper;
import com.gsmggk.accountspayable.dao4db.mapper.DebtorRepoRowMapper;
import com.gsmggk.accountspayable.dao4db.mapper.DebtorStateRowMapper;
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
	public List<Debtor> getAllocatedDebtors(Boolean allocated,ParamsDebtor params)
	{

		 String sql="select "
				+ "d.id,d.short_name,d.full_name,d.address,d.phones,d.jobe,d.family,d.other from oper as o "
				+ "join debtor as d on(o.debtor_id=d.id) " + "WHERE o.action_id=9 and %s EXISTS"
				+ "(select o1.id from oper as o1 where o1.debtor_id=o.debtor_id and o1.action_id=11)";
		String prefix = "";
		if (!allocated) {
			prefix = "NOT";
		}
		sql = String.format(sql, prefix);
		
		Criteria criteria = new Criteria();
		criteria.setSql(sql);
		setCriteria4Debtor(params, criteria);

		LOGGER.debug("getAllocatedDebtor sql:{}", criteria.getCriteriaSql());

		
		Object[] newObj = criteria.getObjects();
		return getCriteriaRowMapper(criteria, newObj, getRowMapper());
		
		
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

	@Transactional
	@Override
	public void updateDebtor(Debtor debtor, Oper oper) {

		super.update(debtor);

		oper.setDebtorId(debtor.getId());

		operDao.insert(oper);

	}

	@Override
	public List<DebtorControl> getDebtors4Clerk(Integer clerkId, ParamsDebtors4Clerk params) {
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

		criteria.addFilter("o.clerk_id=?", "AND", clerkId);

		Date equal2Date = params.getEqual2Date();
		if (equal2Date != null) {
			criteria.addFilter("od.control_date = ?", "AND", equal2Date);
		}

		Boolean sortControl = params.getSortControl();
		if (sortControl != null) {
			switch (sortControl.toString()) {
			case "true":
				criteria.addSort("control", "asc");
				break;
			case "false":
				criteria.addSort("control", "desc");
				break;
			default:
				break;
			}
		}
		setCriteria4Debtor(params, criteria);
		criteria.addFilter("debtor_id, short_name, full_name", "group by", null);
		LOGGER.debug("getDebtors4Clerk sql:{}", criteria.getCriteriaSql());

		DebtorControlRowMapper rm = new DebtorControlRowMapper();
		Object[] newObj = criteria.getObjects();
		return getCriteriaRowMapper(criteria, newObj, rm);
	}

	@Override
	public List<DebtorState> getDebtors4Boss(ParamsDebtors4Boss params) {
		final String sql = "select d.id as debtor_id,d.short_name,d.full_name," + " case "
				+ "when o.action_id=9 then true" + " when o.action_id=1 then FALSE" + " end as active " + "from oper o"
				+ " join debtor d on(o.debtor_id=d.id) " + "where (o.action_id=1 or o.action_id=9)";
		Criteria criteria = new Criteria();
		criteria.setSql(sql);

		if (params.getSortActive() != null) {
			switch (params.getSortActive().toString()) {
			case "true":
				criteria.addSort("active", "asc");
				break;
			case "false":
				criteria.addSort("active", "desc");
				break;
			default:
				break;
			}
		}
		setCriteria4Debtor(params, criteria);

		LOGGER.debug("getDebtors4Boss sql:{}", criteria.getCriteriaSql());

		DebtorStateRowMapper rm = new DebtorStateRowMapper();
		Object[] newObj = criteria.getObjects();
		return getCriteriaRowMapper(criteria, newObj, rm);
	}

	private <R extends ParamsDebtor> void setCriteria4Debtor(R params, Criteria criteria) {
		String searchShotName = params.getSearchShortName();
		if (searchShotName != null && !searchShotName.isEmpty()) {
			criteria.addFilter("d.short_name LIKE ?", "AND", searchShotName);
		}
		String searchFullName = params.getSeachFullName();
		if (searchFullName != null && !searchFullName.isEmpty()) {
			criteria.addFilter("d.short_name LIKE ?", "AND", searchFullName);
		}
		if (params.getSortShortName() != null) {
			switch (params.getSortShortName().toString()) {
			case "true":
				criteria.addSort("short_name", "asc");
				break;
			case "false":
				criteria.addSort("short_name", "desc");
				break;
			default:
				break;
			}
		}
		if (params.getSortFullName() != null) {
			switch (params.getSortFullName().toString()) {
			case "true":
				criteria.addSort("full_name", "asc");
				break;
			case "false":
				criteria.addSort("full_name", "desc");
				break;
			default:
				break;
			}
		}
		if (params.getLimit() != null && params.getLimit() > 0) {
			criteria.setLimit(params.getLimit());
		}
		if (params.getOffset() != null && params.getOffset() > 0) {
			criteria.setOffset(params.getOffset());
		}
	}

	@Override
	public List<DebtorRepo> getDebtorRepo(Date from, Date to, ParamsDebtor params) {
		final String sql = "select  d.id,d.short_name,d.full_name,count(o1.action_id) from debtor d "
				+ "join oper o on (o.debtor_id=d.id and o.action_id=9 ) "
				+ "join oper o1 on(o.debtor_id=o1.debtor_id ) "
				+ "join oper_detail od on(od.oper_id=o1.id) "
				+ "where od.action_date  "
				;
		Criteria criteria = new Criteria();
		criteria.setSql(sql);
		
	
		criteria.addFilter("?", "BETWEEN", from);
		criteria.addFilter("?", "AND", to);
		criteria.addSort("count", "desc");
		
		setCriteria4Debtor(params, criteria);
		criteria.addFilter("d.id", "group by", null);
		LOGGER.debug("getDebtorRepo sql:{}", criteria.getCriteriaSql());

		DebtorRepoRowMapper rm = new DebtorRepoRowMapper();
		Object[] newObj = criteria.getObjects();
		return getCriteriaRowMapper(criteria, newObj, rm);
	}

}
