package com.gsmggk.accountspayable.dao4db.impl;

import java.sql.PreparedStatement;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gsmggk.accountspayable.dao4api.IOperDao;
import com.gsmggk.accountspayable.dao4api.filter.Criteria;
import com.gsmggk.accountspayable.dao4db.impl.gener.GenericDaoImpl;
import com.gsmggk.accountspayable.dao4db.impl.gener.PropertyDao;
import com.gsmggk.accountspayable.dao4db.mapper.OperRowMapper;
import com.gsmggk.accountspayable.datamodel.Oper;

@Repository
public class OperDaoImpl extends GenericDaoImpl<Oper> implements IOperDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(OperDaoImpl.class);

	@Inject
	private JdbcTemplate jdbcTemplate;

	private static final String[] fieldsList = new String[] { "debtor_id", "clerk_id", "action_id" };
	private static final String readSql = "select * from oper where id = ? ";
	private static final String deleteSql = "delete from oper where id=";
	private static final String selectSql = "select * from oper";
	private static final String insertSql = "insert into oper (%s,%s,%s) values(?,?,?)";
	private static final String updateSql = "update oper set %s=?, %s=?, %s=? where id=?";

	@Override
	public BeanPropertyRowMapper<Oper> getRowMapper() {
	
		BeanPropertyRowMapper<Oper> rowMapper = new BeanPropertyRowMapper<Oper>(Oper.class);
		return rowMapper;
	}

	@Override
	public void getInsertPrepareStatement(PreparedStatement ps, Oper object) {

		try {
			{
				int i = 1;
				ps.setInt(i++, object.getDebtorId());
				ps.setInt(i++, object.getClerkId());
				ps.setInt(i++, object.getActionId());

				ps.setInt(i++, object.getId());
			}
		} catch (Exception e) {
			
		}

	}

	@Override
	public PropertyDao getPropertyDao() {
		PropertyDao prDao = new PropertyDao();
		prDao.setFieldsList(fieldsList);
		prDao.setReadSql(readSql);
		prDao.setDeleteSql(deleteSql);
		prDao.setSelectSql(selectSql);
		String insertSql = String.format(OperDaoImpl.insertSql, (Object[]) fieldsList);
		prDao.setInsertSql(insertSql);
		String updateSql = String.format(OperDaoImpl.updateSql, (Object[]) fieldsList);
		prDao.setUpdateSql(updateSql);
		return prDao;
	}

//	@Transactional
	@Override
	public Oper insert(Oper oper) {
		try {
			super.insert(oper);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Add oper error:", e);
			throw e;
		}
		try {
			insertDetale(oper);
		} catch (Exception e) {

			e.printStackTrace();
			LOGGER.error("Add oper detale error:", e);
			throw e;
		}
		return oper;

	}

	private void insertDetale(Oper newOper) {
		String sql = String.format("insert into oper_detail (%s,%s,%s,%s) values(?,?,?,?)",
				(Object[]) new String[] { "oper_id", "action_date", "control_date", "oper_desc" });
	//	jdbcTemplate.update(sql, newOper.getId(), newOper.getActionDate(), newOper.getControlDate(),newOper.getOperDesc());
super.executeUpdate(sql, new Object[] {newOper.getId(), newOper.getActionDate(), newOper.getControlDate(),newOper.getOperDesc()});
	}

	@Override
	public Oper checkAllocated(Integer debtorID, Integer clerkId) {
		final String sql = "select o.id,o.debtor_id,o.clerk_id,o.action_id,od.action_date,od.control_date,od.oper_desc from oper as o join oper_detail as od ON(od.oper_id=o.id)where (o.action_id=11 or o.action_id=2) and o.debtor_id=? and o.clerk_id=? ";
				Object[] objects = new Object[] { debtorID, clerkId };
		Oper oper = super.read(sql,objects,Oper.class);
		
			return oper;
		}
	

	@Override
	public Oper getOper(Integer operId) {
		String sql = "select o.id,o.debtor_id,o.clerk_id,o.action_id,od.action_date,od.control_date,od.oper_desc from oper as o join oper_detail as od ON(od.oper_id=o.id)where o.id=?";

		Object[] objects = new Object[] { operId };

		Criteria cr = new Criteria();
		cr.setSql(sql);

		OperRowMapper rm = new OperRowMapper();
		List<Oper> opers = null;

		opers = getCriteriaRowMapper(cr, objects, rm);

		if (opers.size() > 1) {
			//TODO change exception type
			throw new RuntimeException("Can't have more then one row in answer");
		}

		Oper oper = null;
		try {
			oper = opers.get(0);
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}

		return oper;
	}

	@Override
//	@Transactional
	public void update(Oper object) {
		try {
			super.update(object);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Update oper error:", e);
			throw e;
		}
		try {
			updateDetale(object);
		} catch (Exception e) {

			e.printStackTrace();
			LOGGER.error("Update oper detale error:", e);
			throw e;
		}
		
	}

	private void updateDetale(Oper newOper) {
		String sql = String.format("update oper_detail set %s=? , %s=? , %s=? where oper_id=?",
				(Object[]) new String[] {  "action_date", "control_date", "oper_desc","oper_id" });
	//	jdbcTemplate.update(sql,  newOper.getActionDate(), newOper.getControlDate(),newOper.getOperDesc(),newOper.getId());
		super.executeUpdate(sql, new Object[]{newOper.getActionDate(), newOper.getControlDate(),newOper.getOperDesc(),newOper.getId()});
	}

	@Override
//	@Transactional
	public void delete(Integer operId) {
		try {
			deleteDetale(operId);
		} catch (Exception e) {

			e.printStackTrace();
			LOGGER.error("Delete oper detale error:", e);
			throw e;
		}
		
		
		try {
			super.delete(operId);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Delete oper error:", e);
			throw e;
		}
		
		
	}

	private void deleteDetale(Integer operId) {
		final String sql ="delete from oper_detail where oper_id=?";
			super.executeUpdate(sql,new Object[] {operId} );
	}
	
	
	@Override
	public List<Oper> getOpers4Debtor(Integer debtorId) {
		String sql = "select "
				+ "o.id,o.debtor_id,o.clerk_id,o.action_id,od.action_date,od.control_date,od.oper_desc "
				+ "from oper as o "
				+ "join oper_detail as od ON(od.oper_id=o.id) "
				+ "join debtor as d on (d.id=o.debtor_id) ";


		Criteria cr = new Criteria();
		cr.setSql(sql);
        cr.addFilter(" o.debtor_id=?", "where", debtorId);
        cr.addFilter(" od.action_date ", "order by", null);
		OperRowMapper rm = new OperRowMapper();
		List<Oper> opers = getCriteriaRowMapper(cr, cr.getObjects(), rm);


		return opers;}
	
	@Override
	public Oper getDebtorStateOper(Integer debtorId) {
		
		final String sql="select id,debtor_id,clerk_id,action_id,action_date,control_date,oper_desc from oper o join oper_detail op on (o.id=op.oper_id) where (o.action_id=9 or o.action_id=1) and o.debtor_id =?";
		 
		return  super.read(sql,new Object[] { debtorId}, Oper.class);
	}
}
