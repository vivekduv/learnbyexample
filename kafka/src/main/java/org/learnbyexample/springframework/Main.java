package org.learnbyexample.springframework;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {



    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = new ClassPathXmlApplicationContext("appContext.xml");
        UserService userService = context.getBean(UserService.class);
        System.out.println(userService.getSomeDefault());
        Greeting greeting = new Greeting();
        greeting.setMsg("Hi How are you ?");
        greeting.setName("Vivek");

        userService.Send("demo_java","vivek2");
        userService.Send("demo_java1",greeting);
        Thread.sleep(10000);

    }

}
