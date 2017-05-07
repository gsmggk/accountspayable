package com.gsmggk.accountspayable.services.util.cach;

import java.io.Serializable;
import java.util.Date;

import com.gsmggk.accountspayable.dao4api.params.ParamsDebtor;

/**
 * Class Key for SodftCach. Associate uniq request data to uniq key.
 * @author Gena
 *
 */
public class CachKey implements Serializable {
	private Date from;
	private Date to;
	private ParamsDebtor params ;
	public Date getFrom() {
		return from;
	}
	public void setFrom(Date from) {
		this.from = from;
	}
	public Date getTo() {
		return to;
	}
	public void setTo(Date to) {
		this.to = to;
	}
	public ParamsDebtor getParams() {
		return params;
	}
	public void setParams(ParamsDebtor params) {
		this.params = params;
	}
	@Override
	public String toString() {
		return "CachKey [from=" + from + ", to=" + to + ", params=" + params + ", toString()=" + super.toString() + "]";
	}

	
	

}
