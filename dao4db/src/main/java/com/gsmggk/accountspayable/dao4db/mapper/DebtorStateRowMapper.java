package com.gsmggk.accountspayable.dao4db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.gsmggk.accountspayable.dao4api.modelmap.DebtorState;

public class DebtorStateRowMapper implements RowMapper<DebtorState> {

	@Override
	public DebtorState mapRow(ResultSet rs, int rowNum) throws SQLException {
		DebtorState model = new DebtorState();
		model.setDebtorId(rs.getInt(1));
		model.setShortName(rs.getString(2));
		model.setFullName(rs.getString(3));
		model.setActive(rs.getBoolean(4));
		
		return model;
	}

}