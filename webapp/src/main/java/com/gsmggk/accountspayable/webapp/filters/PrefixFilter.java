package com.gsmggk.accountspayable.webapp.filters;

import java.io.IOException;
import java.util.stream.Stream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Access filter.Check request UID. If UID in path list then allow else denied.
 * @author Gena
 *
 */
public class PrefixFilter implements Filter {
	private String[] pathList;

	@Override
	public void destroy() {

	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		pathList = new String[] { 
				
				"/work/opers/oper",
				"/work/actions/4clerk",
				"/work/debtors/save/",
				"/work/debtors/4clerk",
				"/work/debtors/get/",
				"/work/accounts",
				"/work/opers/4debtor/",
				
				"/admin/opers",
				"/admin/roles",
				"/admin/accounts",
				"/admin/actions",
				"/admin/debtors"
				};
	}

	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;

		String uri=req.getRequestURI();
		boolean contains=
		Stream.of(pathList).anyMatch(x->uri.contains(x));
		if (!contains) {
			res.sendError(404);
           return;
		}
		chain.doFilter(request, response);

	}

}
