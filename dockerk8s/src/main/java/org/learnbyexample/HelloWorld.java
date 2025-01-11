package org.learnbyexample;

public class HelloWorld {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello world2!");

        while (true) {
            Thread.sleep(20000L);
            System.out.println("sleeping..222");
        }
    }
}
