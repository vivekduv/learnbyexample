package org.learnbyexample;

public class HelloWorld {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello world3333333333!");
        String profile=System.getProperty("spring.profiles.active");
        System.out.println("profile="+profile);
        for (String arg : args) {
            System.out.println("arg" + arg); // prints "arg1", "arg2", "value1"
        }
       /* while (true) {
            Thread.sleep(20000L);
            System.out.println("sleeping..222");
        }*/
    }
}
