package com.gsmggk.accountspayable.services.util;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request")
public class UserSessionStorage implements Serializable {
	private Integer id;
	private String layer;
	private String language;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLayer() {
		return layer;
	}

	public void setLayer(String layer) {
		this.layer = layer;
	}

	

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Override
	public String toString() {
		return "UserSessionStorage [id=" + id + ", layer=" + layer + ", language=" + language + "]";
	}
}
