package com.gsmggk.accountspayable.services.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request")
public class UserSessionStorage {
	 private Integer id;

	    public Integer getId() {
	        return id;
	    }

	    public void setId(Integer id) {
	        this.id = id;
	    }

	    @Override
	    public String toString() {
	        return "UserAuthStorage [id=" + id + "]";
	    }
	}
