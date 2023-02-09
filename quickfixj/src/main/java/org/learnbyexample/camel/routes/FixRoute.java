package org.learnbyexample.camel.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Produce;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FixRoute extends RouteBuilder {
    UserConfig userConfig;

    @Produce

    @Autowired
    public void setUserConfig(UserConfig userConfig) {
        this.userConfig = userConfig;
    }
    @Override
    public void configure() throws Exception {
        System.out.println("hello");
        //from("timer:foo?period=100").log("JJJJJJ");
        //from("quickfixjComponent:test?sessionID=FIX.4.2:" + userConfig.getSenderId() + "->" + userConfig.getReceiverId())
       from("quickfixjComponent:example?sessionID=FIX.4.2:PTP->STPHUB")
        //from("quickfix:quickfix.cfg?sessionID=FIX.4.2:TW->ARCA")
        //from("quickfix:quickfix.cfg")

            .to("seda:test");

        /*from("timer:simpleTimer?period=100")
                .setBody(simple("Hello from timer at ${header.firedTime}"))
                .log("Rec ${body}");*/
        //from("seda:test").log("Rec ${body}");
        System.out.println("hello2");
    }



}
