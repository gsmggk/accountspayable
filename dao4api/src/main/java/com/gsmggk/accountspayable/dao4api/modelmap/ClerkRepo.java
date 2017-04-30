package com.gsmggk.accountspayable.dao4api.modelmap;

public class ClerkRepo {
private Integer id;
private String clerkFullName;
private Integer debtors;
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getClerkFullName() {
	return clerkFullName;
}
public void setClerkFullName(String clerkFullName) {
	this.clerkFullName = clerkFullName;
}
public Integer getDebtors() {
	return debtors;
}
public void setDebtors(Integer debtors) {
	this.debtors = debtors;
}
@Override
public String toString() {
	return "ClerkRepo [id=" + id + ", clerkFullName=" + clerkFullName + ", debtors=" + debtors + "]";
}

	
}
