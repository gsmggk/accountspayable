package com.gsmggk.accountspayable.javafx;


import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;

import com.gsmggk.accountspayable.javafx.control.ConfigurationControllers;

@Lazy
@SpringBootApplication
public class NewMainApplication extends AbstractJavaFxApplicationSupport {

   // @Value("${ui.title:JavaFX приложение}")
    private String windowTitle="123";

    @Qualifier("mainView")
    @Autowired
    private ConfigurationControllers.View view;

    @Override
    public void start(Stage stage) throws Exception {
       stage.setTitle(windowTitle);
        stage.setScene(new Scene(view.getView()));
        stage.setResizable(true);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
     //   launchApp(NewMainApplication.class, args);
    	launch(args);
    }

}