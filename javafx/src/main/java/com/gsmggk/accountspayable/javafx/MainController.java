package com.gsmggk.accountspayable.javafx;



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
		String styleRed = "-fx-background-color:red;";
		if (loginField.getText().isEmpty()) {
			loginField.setStyle(styleRed);
		}
		if (passwordField.getText().isEmpty()) {
			passwordField.setStyle(styleRed);
		}

	}
}
