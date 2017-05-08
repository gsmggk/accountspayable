package com.gsmggk.accountspayable.services.util.cach;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class SubKey  implements Serializable{
	
	public SubKey(Integer expirePeriod, Object key) {
		super();
		this.key = key;
		Calendar now=Calendar.getInstance();
		now.add(Calendar.SECOND, expirePeriod);
		this.expire = now.getTime();
		
	}
	public SubKey() {
		super();
	}
	private Date expire;
	private Object key;
	public Date getExpire() {
		return expire;
	}
	public void setExpire(Date expire) {
		this.expire = expire;
	}
	public Object getKey() {
		return key;
	}
	public void setKey(Object key) {
		this.key = key;
	}
	@Override
	public String toString() {
		return "SubKey [expire=" + expire + ", key=" + key + "]";
	}
	
}

