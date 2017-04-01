package com.gsmggk.accountspayable.javafx.control;

import javax.inject.Inject;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.support.incrementer.MySQLMaxValueIncrementer;

import com.gsmggk.accountspayable.datamodel.Clerk;
import com.gsmggk.accountspayable.services.IActionService;
import com.gsmggk.accountspayable.services.IClerkService;
import com.gsmggk.accountspayable.services.IRoleService;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class MainController {
	
	@FXML
	private TextField loginField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Button loginButton;
	@FXML
	private Button cancelButton;

	@FXML
	private void cancelButtonAction() {

		Platform.exit();
		System.exit(0);
	}

	@FXML
	private void loginButtonAction() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("javafx-context.xml");

		IRoleService iRoleService = context.getBean(IRoleService.class);
		IActionService iActionService = context.getBean(IActionService.class);
		IClerkService iClerkService = context.getBean(IClerkService.class);

		
		
		String styleRed = "-fx-background-color:red;";
		String login=loginField.getText();
		String password=passwordField.getText();
		if (login.isEmpty()) {
			loginField.setStyle(styleRed);
		}
		if (password.isEmpty()) {
			passwordField.setStyle(styleRed);
		}
		

		if (iClerkService.loginClerk(login, password)) {
			System.out.println("LOGIN OK");
		}
		;

	}
}
