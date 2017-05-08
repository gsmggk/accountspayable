package com.gsmggk.accountspayable.webapp.filters;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.gsmggk.accountspayable.datamodel.Clerk;
import com.gsmggk.accountspayable.datamodel.Role;
import com.gsmggk.accountspayable.services.IClerkService;
import com.gsmggk.accountspayable.services.IRoleService;
import com.gsmggk.accountspayable.services.impl.exceptions.MyBadLoginNameException;
import com.gsmggk.accountspayable.services.impl.exceptions.MyBadPasswordException;
import com.gsmggk.accountspayable.services.util.MemClient;
import com.gsmggk.accountspayable.services.util.UserSessionStorage;

public class SessionFilter implements Filter {
	private static final Logger LOGGER = LoggerFactory.getLogger(SessionFilter.class);
	private IRoleService roleService;
	private IClerkService clerkService;
	private MemClient client;

	private ApplicationContext appContext;

	

	@Override
	public void init(FilterConfig config) throws ServletException {

		WebApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(config.getServletContext());
		clerkService = context.getBean(IClerkService.class);
		roleService = context.getBean(IRoleService.class);
		client = context.getBean(MemClient.class);
		appContext = context;

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws java.io.IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;

		if (!isAuthRequired(req)) {
			chain.doFilter(request, response);
			return;
		}

		String[] credentials = resolveCredentials((HttpServletRequest) request);

		boolean isCredentialsResolved = credentials != null && credentials.length == 2;
		if (!isCredentialsResolved) {
			res.sendError(401);
			return;
		}
		String username = credentials[0];
		String password = credentials[1];
		// ==========================================
		// try get storage from memcached
		//
		// ==========================================
		String base64 = resolveBase64(req);

		UserSessionStorage storageFromCache = client.getCach(base64);

		if (storageFromCache != null) {
			if (!chekUser2LayerAccess(req, storageFromCache.getLayer())) {
				

				res.sendError(401);
				return;
			}
			UserSessionStorage storage = appContext.getBean(UserSessionStorage.class);
				storage.setId(storageFromCache.getId());
				storage.setLayer(storageFromCache.getLayer());
			
			chain.doFilter(request, res);
			return;
		}

		Clerk clerk = new Clerk();
		try {
			clerk = clerkService.loginCheck(username, password);
		} catch (MyBadLoginNameException e) {
			res.sendError(401);
			return;
		}

		catch (MyBadPasswordException e) {
			res.sendError(401);
			return;
		}

		Integer roleId = clerk.getRoleId();
		Role role = new Role();
		role = roleService.get(roleId);
		String layer = role.getLayer();

		// TODO get prefix if
		if (!chekUser2LayerAccess(req, layer)) {
			res.sendError(401);
			return;
		}
		UserSessionStorage storageDb = appContext.getBean(UserSessionStorage.class);
		storageDb.setId(clerk.getId());
		storageDb.setLayer(layer);
		// ==========================================
		// load storage to memcached
		// load cross link to memcached
		// ==========================================

		client.setCach(base64, storageDb);

		chain.doFilter(request, res);

	}

	private boolean chekUser2LayerAccess(HttpServletRequest req, String layer) {
		if (req.getRequestURI().toLowerCase().equals("/login")) {
			return true;
		}
		if (req.getRequestURI().toLowerCase().startsWith("/" + layer.toLowerCase())) {
			return true;
		}

		return false;
	}

	private boolean isAuthRequired(HttpServletRequest req) {

		if (req.getMethod().toUpperCase().equals("GET") && req.getRequestURI().toLowerCase().equals("/login")) {
			return false;
		}
		// TODO other variants
		return true;
	}

	private String[] resolveCredentials(HttpServletRequest req) {
		try {
			String base64Credentials = resolveBase64(req);
			String credentials = new String(Base64.getDecoder().decode(base64Credentials), Charset.forName("UTF-8"));
			return credentials.split(":", 2);
		} catch (Exception e) {
			return null;
		}
	}

	private String resolveBase64(HttpServletRequest req) {
		Enumeration<String> headers = req.getHeaders("Authorization");
		String nextElement = headers.nextElement();
		String base64Credentials = nextElement.substring("Basic".length()).trim();
		return base64Credentials;
	}

	@Override
	public void destroy() {
	}

}
