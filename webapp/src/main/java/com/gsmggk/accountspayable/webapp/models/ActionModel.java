package com.gsmggk.accountspayable.webapp.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class ActionModel {
	@NotNull(message = "Action name  must be not null")
	@NotEmpty(message = "Action name must be not empty")
	@Size(min=3,max=30,message = "Action name must be on 5 to 30")
	private String actionName;
	@Max(value = 365,message = "Duration is so big")
	private Integer duration;
	private Integer Id;

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	
}
