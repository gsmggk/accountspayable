package com.gsmggk.accountspayable.javafx.control;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import com.gsmggk.accountspayable.services.IClerkService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class MainController {
	@Autowired
	private IClerkService service;
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

		String styleRed = "-fx-background-color:red;";
		String login = loginField.getText();
		String password = passwordField.getText();
		if (login.isEmpty()) {
			loginField.setStyle(styleRed);
		}
		if (password.isEmpty()) {
			passwordField.setStyle(styleRed);
		}

		service.loginCheck(login, password);

	}

	@FXML
	public void initialize() {
	}

	/**
	 * На этом этапе уже произведены все возможные инъекции.
	 */
	@PostConstruct
	public void init() {
		// service.getAll();
	}
}
