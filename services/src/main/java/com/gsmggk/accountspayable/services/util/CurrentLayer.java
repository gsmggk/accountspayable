package com.gsmggk.accountspayable.services.util;

import javax.inject.Inject;

import com.gsmggk.accountspayable.datamodel.Clerk;
import com.gsmggk.accountspayable.services.IClerkService;

public  class CurrentLayer {
private static  Integer clerkId;
private static  String clerkFullName;


public static Integer getClerkId() {
	return clerkId;
}

public static void setClerkId(Integer clerkId) {
	CurrentLayer.clerkId = clerkId;
}

public static String getClerkFullName() {
	return clerkFullName;
}

public static void setClerkFullName(String clerkFullName) {
	CurrentLayer.clerkFullName = clerkFullName;
}



}
