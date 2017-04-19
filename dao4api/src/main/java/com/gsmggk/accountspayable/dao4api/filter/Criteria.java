package com.gsmggk.accountspayable.dao4api.filter;

import java.util.ArrayList;
import java.util.List;

public class Criteria {

	public Criteria() {
		super();
		
		this.filters = new ArrayList<Filter>();

		this.sorts = new ArrayList<SortData>();
	}

	public void addFilter(String column, String operator, String value) {
		Filter f = new Filter();
		f.setField(column);
		f.setOperator(operator);
		f.setValue(value);
		this.filters.add(f);
	}

	public void addSort(String column, String order) {
		SortData s = new SortData();
		s.setColumn(column);
		s.setOrder(order);

		this.sorts.add(s);
	}

	public String getCriteriaSql() {
		if (sql==null||sql.isEmpty()) {
			throw new IllegalArgumentException("Criteria sql string must containd entrys");
		}
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append(sql);

		if (!filters.isEmpty()) {
			setFilters(sBuilder);
		}
		if (!sorts.isEmpty()) {
			setSorts(sBuilder);
		}
		
		if(limit!=null&&limit>0){
			sBuilder.append(String.format(" limit %s", limit));
		}
		if(offset!=null){
			sBuilder.append(String.format(" offset %s", offset));
		}
		return sBuilder.toString();
	}

	private void setSorts(StringBuilder sBuilder) {
		sBuilder.append(" order by");
		for (int i = 0; i < sorts.size(); i++) {
			if (i != 0) {
				sBuilder.append(" ,");
			}
			sBuilder.append(String.format(" %s  %s", sorts.get(i).getColumn(), sorts.get(i).getOrder()));

		}
	}

	private void setFilters(StringBuilder sBuilder) {
		for (int i = 0; i < filters.size(); i++) {
			sBuilder.append(String.format(" %s", filters.get(i).getOperator()));
			sBuilder.append(String.format(" %s ", filters.get(i).getField()));

		}
	}

	private List<Filter> filters;
	private List<SortData> sorts;
	private String sql;
	private Integer limit;
	private Integer offset;

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

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

}
