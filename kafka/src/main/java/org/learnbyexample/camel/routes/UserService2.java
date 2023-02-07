package org.learnbyexample.camel.routes;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;


//@Service


@Component
public class UserService2 {
    @Value("${unknown.param:some default}")
    private String someDefault;

    public UserService2(String someDefault) {
        this.someDefault = someDefault;
    }
    public UserService2() {

    }


    public String getSomeDefault() {
        return someDefault;
    }


}