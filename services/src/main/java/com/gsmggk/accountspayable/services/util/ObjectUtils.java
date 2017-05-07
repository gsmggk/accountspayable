package com.gsmggk.accountspayable.services.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

import com.gsmggk.accountspayable.services.impl.exceptions.MySerializeException;
import com.gsmggk.accountspayable.services.util.cach.SubKey;

public class ObjectUtils {
	
	public static Object deserialize(String serializedObject) {
		Object obj = null;
		 try {
			 byte b[] = Base64.getDecoder().decode(serializedObject.getBytes()); 
		   
		     ByteArrayInputStream bi = new ByteArrayInputStream(b);
		     ObjectInputStream si = new ObjectInputStream(bi);
		     obj = (SubKey) si.readObject();
		 } catch (Exception e) {
		   e.printStackTrace();
		   throw new MySerializeException("Deserialize from string error",e);
		 }
		
		return obj;
	}
	public static String serialize(Object key) {
		String serializedObject = "";
		 try {
		     ByteArrayOutputStream bo = new ByteArrayOutputStream();
		     ObjectOutputStream so = new ObjectOutputStream(bo);
		     so.writeObject(key);
		     so.flush();
		     serializedObject  = new String(Base64.getEncoder().encode(bo.toByteArray())); 
		     
		 } catch (Exception e) {
		   e.printStackTrace();
		   throw new MySerializeException("Serialize to string error",e);
		 }
		return serializedObject;
	}
	
}
