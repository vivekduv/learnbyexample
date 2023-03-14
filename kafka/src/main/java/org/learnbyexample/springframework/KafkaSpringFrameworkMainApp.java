package org.learnbyexample.springframework;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class KafkaSpringFrameworkMainApp {



    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = new ClassPathXmlApplicationContext("appContext.xml");
        MessageService userService = context.getBean(MessageService.class);
         Greeting greeting = new Greeting();
        greeting.setMsg("demo java2");
        greeting.setName("Vivek");

        userService.Send("demo_java1","demo_java1");
        userService.Send("demo_java2",greeting);
        Thread.sleep(10000);

    }

}
