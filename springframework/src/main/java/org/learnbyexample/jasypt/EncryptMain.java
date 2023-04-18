package org.learnbyexample.jasypt;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;

public class EncryptMain {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext;
        applicationContext = new ClassPathXmlApplicationContext("spring-jasypt.xml");

        EnvironmentStringPBEConfig s=(EnvironmentStringPBEConfig) applicationContext.getBean("environmentVariablesConfiguration");
        System.out.println(s.getAlgorithm());

        Props p=(Props)applicationContext.getBean(Props.class);
        System.out.println(p.getPassword());
    }
}
