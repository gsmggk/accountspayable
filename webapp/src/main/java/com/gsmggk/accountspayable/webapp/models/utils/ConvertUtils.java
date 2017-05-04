package com.gsmggk.accountspayable.webapp.models.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utils helper for convertation
 * @author Gena
 *
 */
public class ConvertUtils {
	/**convert date to string
	 * @param date
	 * @return
	 */
	public static  String date2string(Date date){
		if (date == null) {
			
			return "Не установлена";
		}
		DateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
      return dFormat.format(date);
		
	}

	/**
	 * Convert Timestamp to string
	 * @param date Timestamp
	 * @return String
	 */
	public static String timestmp2string(Timestamp date) {
		DateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return dFormat.format(date);
	}
	public static Date string2date(String strDate){
		 SimpleDateFormat sFormat = new SimpleDateFormat("dd/MM/yyyy");
		 Date date;
		try {
			date = sFormat.parse(strDate);
		} catch (ParseException e) {
			date=null;
			
		}
		  
		  return date;
	}
	
}
