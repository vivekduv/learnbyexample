package org.learnbyexample.camel;
import org.apache.camel.main.Main;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class KafkaCamelMainApp {
    public static void main(String... args) throws Exception {
        Main main = new Main();
        ApplicationContext applicationContext=
                new ClassPathXmlApplicationContext("appContextCamel.xml");
        //main.configure().addRoutesBuilder(new AddRoutes());
        main.run(args);
    }
}
