package com.gsmggk.accountspayable.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class MainApplication extends Application {

	private static final String title = "Система учета должников";
	
	
	
	

	/*@Override
	public void start(Stage primaryStage) {
		
		
		
		try {
			
			String fxmlFile = "LoginView.fxml";
			Parent root = (Parent) FXMLLoader.load(getClass().getResource(fxmlFile));
			// BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 350, 120);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle(title);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	public static void main(String[] args) {
		launch(args);
	}


private static final SpringFxmlLoader loader=new SpringFxmlLoader();
private static final String fxmlFile = "LoginView.fxml";

	@Override
	public void start(Stage primaryStage) {
		Parent root=(Parent) loader.load(fxmlFile);
		
	}
}
