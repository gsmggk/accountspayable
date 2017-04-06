package com.gsmggk.accountspayable.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class MainApplication extends Application {

	private static final String title = "Система учета должников";
	 private static final MySpringFxmlLoader loader = new MySpringFxmlLoader();

	
	public static void main(String[] args) {
		launch(args);
	}
	

	@Override
	public void start(Stage primaryStage) {
		
		
		
		try {
			
			String fxmlFile = "LoginView.fxml";
			Parent root = (Parent) loader.load("LoginView.fxml");
			// BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 350, 120);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle(title);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}}

	
/*

private static final SpringFXMLLoader loader=new SpringFXMLLoader();
private static final String fxmlFile = "LoginView.fxml";

	@Override
	public void start(Stage primaryStage) {
	//	Parent root=(Parent) loader.load(fxmlFile);
		 MainController controller = (MainController) SpringFXMLLoader.load(fxmlFile);
	        Scene scene = new Scene((Parent) controller.getView(), 300, 275);
	        primaryStage.setTitle("Todolist");
	        primaryStage.setScene(scene);
	        primaryStage.show();
	}
}*/
