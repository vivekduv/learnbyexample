package org.learnbyexample.camel.routes;


import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component

public class WordRouteProducer extends RouteBuilder {
    UserConfig userConfig;
    @Autowired
    public void setUserConfig(UserConfig userConfig) {
        this.userConfig = userConfig;
    }
    @Override

    public void configure() throws Exception {

            from("timer:foo?period=100")
                    .bean(new WordBean())
                    .to(userConfig.getProduceUrl())
                    .to("log:words?groupInterval=1000");
        }

}
