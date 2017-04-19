package com.gsmggk.accountspayable.dao4api.filter;


import java.util.List;

public class Criteria {

public Criteria() {
		super();
	//	this.filters=new List<Filter>();
		
	}
public void addField(String field,String operator,String value){
	Field f=new Field();
	f.setField(field);
	f.setOperator(operator);
	f.setValue(value);
	fields.add(f);
}
private List<Filter> filters;
private List<SortData> sorts;
private List<Field> fields;
public class Field {
	private String field;
	private String operator;
	private String value;
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}

private String sql; 
public List<Filter> getFilters() {
	return filters;
}
public void setFilters(List<Filter> filters) {
	this.filters = filters;
}
public List<SortData> getSorts() {
	return sorts;
}
public void setSorts(List<SortData> sorts) {
	this.sorts = sorts;
}
public String getSql() {
	return sql;
}
public void setSql(String sql) {
	this.sql = sql;
}
public List<Field> getFields() {
	return fields;
}
public void setFields(List<Field> fields) {
	this.fields = fields;
}



}
