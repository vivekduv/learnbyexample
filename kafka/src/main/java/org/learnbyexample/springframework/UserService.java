package org.learnbyexample.springframework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    @Value("${unknown.param:some default}")
    private String someDefault;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private KafkaConsumer kafkaConsumer;


    public String getSomeDefault() {
        return someDefault;
    }

    public void Send(String topic, String message) {
        kafkaProducer.send(topic, message);
    }
    public void Send(String topic, Greeting message) {
        kafkaProducer.send(topic, message);
    }

    public void setSomeDefault(String someDefault) {
        this.someDefault = someDefault;
    }
}