package com.gsmggk.accountspayable.dao4db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.gsmggk.accountspayable.datamodel.Oper;

public class OperRowMapper  implements RowMapper<Oper> {

	@Override
	public Oper mapRow(ResultSet rs, int rowNum) throws SQLException {
		Oper model = new Oper();
		model.setId(rs.getInt(1));
		model.setDebtorId(rs.getInt(2));
		model.setClerkId(rs.getInt(3));
		model.setActionId(rs.getInt(4));
		model.setActionDate(rs.getTimestamp(5));
		model.setControlDate(rs.getDate(6));
		model.setOperDesc(rs.getString(7));
		
		
		return model;
	}

}