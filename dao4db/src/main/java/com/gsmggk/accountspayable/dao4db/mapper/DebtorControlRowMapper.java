package com.gsmggk.accountspayable.dao4db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.gsmggk.accountspayable.dao4api.maps.DebtorControl;

public class DebtorControlRowMapper implements RowMapper<DebtorControl> {

	@Override
	public DebtorControl mapRow(ResultSet rs, int rowNum) throws SQLException {
		DebtorControl model = new DebtorControl();
		model.setDebtorId(rs.getInt(1));
		model.setShortName(rs.getString(2));
		model.setFullName(rs.getString(3));
		model.setControl(rs.getDate(4));
		
		return model;
	}

}