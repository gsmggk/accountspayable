package com.gsmggk.accountspayable.javafx;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringFxmlLoader {
private static final ApplicationContext appContext=
new AnnotationConfigApplicationContext(AppConfig.class);
}
