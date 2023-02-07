package org.learnbyexample.camel.routes;


import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaComponent;
import org.apache.camel.component.kafka.KafkaConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component

public class WordRoute extends RouteBuilder {


KafkaComponent kafkaComponent;

@Autowired
    public void setKafkaComponent(KafkaComponent kafkaComponent) {
        this.kafkaComponent = kafkaComponent;
    }

    KafkaConfiguration kafkaConfiguration;

    @Autowired
    public void setKafkaConfiguration(KafkaConfiguration kafkaConfiguration) {
        this.kafkaConfiguration = kafkaConfiguration;
    }

    UserService2 userService;

    @Autowired
    public void setUserService(UserService2 userService) {
        this.userService = userService;
    }



    @Override

    public void configure() throws Exception {
       System.out.println("SFWEEWRWERWER" + userService.getSomeDefault());
        System.out.println("kafaka" + kafkaConfiguration.getBrokers());
        System.out.println("kafakaCCCCC" + kafkaComponent.getConfiguration().getBrokers());

//from("kafka:kafkaC?topicIsPattern=true")
           from( "kafka:demo_java?brokers=localhost:9092" + "&topicIsPattern=true")

        //from("kafka:demo_java?brokers=localhost:9092&topicIsPattern=true")
                .log("Message received from Kafka : ${body}")
                .log("    on the topic ${headers[kafka.TOPIC]}")
                .log("    on the partition ${headers[kafka.PARTITION]}")
                .log("    with the offset ${headers[kafka.OFFSET]}")
                .log("    with the key ${headers[kafka.KEY]}");

//        from("timer:foo?period=100")
//                .bean(new WordBean())
//                .to("kafka:demo_java")
//                .to("log:words?groupInterval=1000");
    }
}
