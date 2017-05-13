package com.gsmggk.accountspayable.webapp.filters;

import java.io.IOException;
import java.util.Locale;
import java.util.stream.Stream;

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

import com.gsmggk.accountspayable.dao4api.language.LanguageContainer;
import com.gsmggk.accountspayable.services.IClerkService;
import com.gsmggk.accountspayable.services.util.UserSessionStorage;

public class LocaleFilter implements Filter{
	private static final Logger LOGGER = LoggerFactory.getLogger(LocaleFilter.class);
	
	private String[] localeList;
	private ApplicationContext appContext;
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void  doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;
 	Locale locale=	req.getLocale();
 	String language= checkLocale(locale.getLanguage());
	UserSessionStorage storage = appContext.getBean(UserSessionStorage.class);
		storage.setLanguage(language);
		 LanguageContainer.setLanguage(language);
	//	LOGGER.info("You language is:{}",language);
		
		chain.doFilter(req, res);
	}

	private String checkLocale(String inputLanguage) {
	
		
		String res=   Stream.of(localeList)
				.filter(x->inputLanguage.equals(x))
				.findAny().orElse("ru")	;
				
		return res;	
				
		}

	@Override
	public void init(FilterConfig config) throws ServletException {
	    localeList = new String[] { "en","ru","pl"};

		WebApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(config.getServletContext());
		
		appContext = context;
	}

}
