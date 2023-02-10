package org.learnbyexample.camel;

import org.apache.camel.main.Main;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class QuickFixCamelMainApp {
    public static void main(String... args) throws Exception {
        ApplicationContext applicationContext= new ClassPathXmlApplicationContext("fixAppContext.xml");
        Main main = new Main();
        main.run(args);
    }
}
