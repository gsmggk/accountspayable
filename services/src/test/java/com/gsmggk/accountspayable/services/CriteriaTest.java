package com.gsmggk.accountspayable.services;

import org.junit.Test;
import org.springframework.util.Assert;

import com.gsmggk.accountspayable.dao4api.filter.Criteria;

public class CriteriaTest {
	

	
	@Test
	public void addCriteriaTest(){
		String insertSql="select * from oper where clerk_id=?  And  oper_id=?  order by clerk_id   , oper_id  desc limit 5 offset 10";
		Criteria cr=new Criteria();
		cr.setSql("select * from oper");
		cr.addFilter("clerk_id=?", "where", "");
		cr.addFilter("oper_id=?", "And ", "");
		cr.addSort("clerk_id", "");
		cr.addSort("oper_id", "desc");
		cr.setLimit(5);
		cr.setOffset(10);
		
	String sql=cr.getCriteriaSql();
		System.out.println(cr.getCriteriaSql());
		//accountDao.readCriteria(cr);
		Assert.isTrue(sql.equals(insertSql),"String must dee same");
	}
}
