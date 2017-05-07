package com.gsmggk.accountspayable.services.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


import org.springframework.stereotype.Component;

import com.gsmggk.accountspayable.services.util.cach.SubKey;
@Component
public class SoftCach {
	private HashMap map = new HashMap();

	private Object get(Object key) {
		SoftReference softRef = (SoftReference) map.get(key);
		if (softRef == null)
			return null;
		return softRef.get();
	}
	private Object put(Object key,Object value){
		SoftReference softRef = (SoftReference) map.put(key,new SoftReference(value));
		if (softRef == null)
		return null;
		Object oldValue=softRef.get();
		softRef.clear();
		return oldValue;
	}
	private Object remove(Object key){
		SoftReference softRef = (SoftReference) map.remove(key);
		
		if (softRef == null)
			return null;
			Object oldValue=softRef.get();
			softRef.clear();
			return oldValue;
	}
	
	/**
	 * Get object from cache. If data object expire data clear.
	 * @param key object
	 * @return Object from cache (value)
	 */
	public Object getCache(Object key){
		String sKey=ObjectUtils.serialize(key);
		String sSubKey=(String) get(sKey);
		
		if (sSubKey==null) return null;
		SubKey subKey=(SubKey) ObjectUtils.deserialize(sSubKey);
		
		Date now = new Date();
		Date  expire=subKey.getExpire();
		if(now.getTime()>expire.getTime()){
			remove(sSubKey);
			remove(sKey);
			return null;} 
		Object result=get(sSubKey);
		return result;
	}
	
	
	/**
	 * Put data into cache.
	 * @param key  object.
	 * @param sec Expire period in seconds.
	 * @param value  object
	 */
	public void putCache(Object key,Integer sec,Object value){
		String sKey=ObjectUtils.serialize(key);
		SubKey sub=new SubKey(sec,key);
		String sSub=ObjectUtils.serialize(sub);
		put(sKey,sSub);
		put(sSub,value);
	}
	
	public void removeCache(Object key){
		String sKey=ObjectUtils.serialize(key);
		String sub=(String) get(sKey);
		remove(sub);
		remove(sKey);
	}
	public void cache2disk() {
		// TODO Auto-generated method stub
		
	}
	public void disk2cache() {
		// TODO Auto-generated method stub
		
	}

}

