package com.gsmggk.accountspayable.webapp.controllers;

import javax.inject.Inject;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.gsmggk.accountspayable.services.IClerkService;
import com.gsmggk.accountspayable.services.impl.UserSessionStorage;

@RestController
@RequestMapping("/login")
public class LoginController {

	@Inject
	private ApplicationContext appContext;
	@Inject
	private IClerkService service;
	
	@Value("${maxAge}")
	private Integer maxAge;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getLogin() {
		return new ResponseEntity<>( HttpStatus.OK);
	}	
	
	/*@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getLog(ServletResponse response) {
		   // "/" is relative to the context root (your web-app name)
        RequestDispatcher view = req.getRequestDispatcher("/path/to/file.html");
        // don't add your web-app name to the path

        view.forward(req, resp);    
		return new ResponseEntity<>( HttpStatus.OK);
	}	*/
	
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<?> login(HttpSession sess,ServletResponse response) {
		
        UserSessionStorage storage = appContext.getBean(UserSessionStorage.class);
     
	      
      Cookie loginCookie=new Cookie("SESSIONID",  sess.getId());
      loginCookie.setMaxAge(maxAge);
       ((HttpServletResponse) response).addCookie(loginCookie);
       String bodyOfResponse = "{\"login\":\"Ok.\"}";
       service.addSession(storage.getId(),sess.getId());
	
       
		return new ResponseEntity<String>(bodyOfResponse,HttpStatus.OK);
	}
}
