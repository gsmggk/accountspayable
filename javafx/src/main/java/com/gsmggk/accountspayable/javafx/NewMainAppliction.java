package com.gsmggk.accountspayable.javafx;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;

import com.gsmggk.accountspayable.javafx.control.ConfigurationControllers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

@Lazy
@SpringBootApplication
public class NewMainAppliction extends AbstractJavaFxApplicationSupport {
	  @Value("${ui.title:JavaFX приложение}")//
	    private String windowTitle;
	
	  
	  @Qualifier("mainView")
	    @Autowired
	    private ConfigurationControllers.View view;

	@Override
	public void start(Stage stage) {
		 stage.setTitle(windowTitle);
	        stage.setScene(new Scene(view.getView()));
	        stage.setResizable(true);
	        stage.centerOnScreen();
	        stage.show();
	}

	public static void main(String[] args) {
		launchApp(NewMainAppliction.class, args);
	}
}
