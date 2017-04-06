package com.gsmggk.accountspayable.javafx;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gsmggk.accountspayable.javafx.control.ConfigurationControllers;
import com.gsmggk.accountspayable.javafx.control.Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.util.Callback;


public class SpringFXMLLoader  {
private static final ApplicationContext context=
new AnnotationConfigApplicationContext(ConfigurationControllers.class);
private static final Logger LOGGER = LoggerFactory.getLogger(SpringFXMLLoader.class);
//static ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("javafx-context.xml");
public static Controller load(String url) {
    InputStream fxmlStream = null;
    try {
        fxmlStream = SpringFXMLLoader.class.getResourceAsStream(url);
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> aClass) {
                return context.getBean(aClass);
            }
        });

        Node view = (Node) loader.load(fxmlStream);
        Controller controller = loader.getController();
        controller.setView(view);

        return controller;
    } catch (IOException e) {
        LOGGER.error("Can't load resource", e);
        throw new RuntimeException(e);
    } finally {
        if (fxmlStream != null) {
            try {
                fxmlStream.close();
            } catch (IOException e) {
            	LOGGER.error("Can't close stream", e);
            }
        }
    }
}
}
