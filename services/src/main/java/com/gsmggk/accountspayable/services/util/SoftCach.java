package com.gsmggk.accountspayable.services.util;

import java.lang.ref.SoftReference;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.gsmggk.accountspayable.services.util.cach.SubKey;

@Component
public class SoftCach {
	@SuppressWarnings("rawtypes")
	private HashMap map = new HashMap();

	@Value("${file}")
	private String fileName;

	@SuppressWarnings("rawtypes")
	private Object get(Object key) {
		SoftReference softRef = (SoftReference) map.get(key);
		if (softRef == null)
			return null;
		return softRef.get();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object put(Object key, Object value) {
		SoftReference softRef = (SoftReference) map.put(key, new SoftReference(value));
		if (softRef == null)
			return null;
		Object oldValue = softRef.get();
		softRef.clear();
		return oldValue;
	}

	@SuppressWarnings("rawtypes")
	private Object remove(Object key) {
		SoftReference softRef = (SoftReference) map.remove(key);

		if (softRef == null)
			return null;
		Object oldValue = softRef.get();
		softRef.clear();
		return oldValue;
	}

	/**
	 * Get object from cache. If data object expire data clear.
	 * 
	 * @param key
	 *            object
	 * @return Object from cache (value)
	 */
	public Object getCache(Object key) {
		String sKey = ObjectUtils.serialize(key);
		String sSubKey = (String) get(sKey);

		if (sSubKey == null)
			return null;
		SubKey subKey = (SubKey) ObjectUtils.deserialize(sSubKey);

		Date now = new Date();
		Date expire = subKey.getExpire();
		if (now.getTime() > expire.getTime()) {
			remove(sSubKey);
			remove(sKey);
			return null;
		}
		Object result = get(sSubKey);
		return result;
	}

	/**
	 * Put data into cache.
	 * 
	 * @param key
	 *            object.
	 * @param sec
	 *            Expire period in seconds.
	 * @param value
	 *            object
	 */
	public void putCache(Object key, Integer sec, Object value) {
		String sKey = ObjectUtils.serialize(key);
		SubKey sub = new SubKey(sec, key);
		String sSub = ObjectUtils.serialize(sub);
		put(sKey, sSub);
		put(sSub, value);
	}

	public void removeCache(Object key) {
		String sKey = ObjectUtils.serialize(key);
		String sub = (String) get(sKey);
		remove(sub);
		remove(sKey);
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void cache2disk() {
		HashMap convert =new HashMap();

		map.forEach((k,v)->{
		
			SoftReference softRef=(SoftReference) v;
			
			convert.put(k, softRef.get());
		});		
		
	
       ObjectUtils.serialize2File(convert, fileName);
	}

	@SuppressWarnings( { "rawtypes", "unchecked" } )
	public void disk2cache() {
		HashMap convert	=  (HashMap) ObjectUtils.deSerialize2File(fileName);	
        convert.forEach((k,v)->{
        	put(k, v);
        });
	}

	public void flush(){
		 map.clear();
	}
}
