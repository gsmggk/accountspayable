package com.gsmggk.accountspayable.dao4db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.gsmggk.accountspayable.dao4api.modelmap.ClerkRepo;
import com.gsmggk.accountspayable.dao4api.modelmap.DebtorControl;

public class ClerkRepoRowMapper implements RowMapper<ClerkRepo>  {

	@Override
	public ClerkRepo mapRow(ResultSet rs, int rowNum) throws SQLException {
		ClerkRepo model = new ClerkRepo();
		model.setId(rs.getInt(1));
		model.setClerkFullName(rs.getString(2));
		model.setDebtors(rs.getInt(3));
	return model;
	}

}
