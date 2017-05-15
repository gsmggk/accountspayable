package com.gsmggk.accountspayable.services.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gsmggk.accountspayable.services.impl.exceptions.MySerializeException;


public class ObjectUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(ObjectUtils.class);	
	public static Object deserialize(String serializedObject) {
		
		Object obj = null;
		
		 try  {
			 byte b[] = Base64.getDecoder().decode(serializedObject.getBytes()); 
		   
		     ByteArrayInputStream bi = new ByteArrayInputStream(b);
		     ObjectInputStream si = new ObjectInputStream(bi);
		     obj =  si.readObject();
		    
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
	
	public static void serialize2File(Object obj,String fileName) {

		try (ObjectOutputStream oos =
				new ObjectOutputStream(new FileOutputStream(fileName))) {

			oos.writeObject(obj);
		LOGGER.debug("Done serialize2file");

		} catch (Exception e) {
			LOGGER.error("Error serialize2file: {}",e.getMessage());
			e.printStackTrace();
			 throw new MySerializeException("Serialize to file error",e);
		}

	}
	public static Object deSerialize2File(String fileName) {	
		
		try(ObjectInputStream ois= new ObjectInputStream(new FileInputStream(fileName)) ){
			Object obj=ois.readObject();
			LOGGER.debug("Done deserialize2file");
			return obj;
		} catch (Exception e) {
			LOGGER.error("Error deSerialize2File: {}",e.getMessage());
			e.printStackTrace();
			 throw new MySerializeException("Deserialize from file error",e);
		}
	}
	
}
