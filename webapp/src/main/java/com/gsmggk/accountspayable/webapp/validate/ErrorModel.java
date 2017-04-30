package com.gsmggk.accountspayable.webapp.validate;

public class ErrorModel {
	public ErrorModel() {
		super();
		this.error = "error";
	}
	private  String error;
	private String object;
	private String field;
	private String message;
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
	
	

}
