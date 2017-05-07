package com.gsmggk.accountspayable.dao4db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.gsmggk.accountspayable.dao4api.modelmap.DebtorRepo;

public class DebtorRepoRowMapper implements RowMapper<DebtorRepo> {

	@Override
	public DebtorRepo mapRow(ResultSet rs, int rowNum) throws SQLException {
		DebtorRepo model = new DebtorRepo();
		model.setDebtorId(rs.getInt(1));
		model.setShortName(rs.getString(2));
		model.setFullName(rs.getString(3));
		model.setCount(rs.getInt(4));
		
		return model;
	}

}