package com.gsmggk.accountspayable.webapp.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PasswordModel {
private String value;
@NotNull(message="Password can't be null")
@Size(min=5,message = "Password need more then 5 character")
private String criptValue;

public String getValue() {

	value=criptValue;// encript password :-)
	return value;
}

public String getCriptValue() {
	return criptValue;
}
public void setCriptValue(String criptValue) {
	this.criptValue = criptValue;
}



}
