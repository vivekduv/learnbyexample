package org.learnbyexample.springframework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageService {
    private KafkaProducer kafkaProducer;
     private KafkaConsumer kafkaConsumer;
    @Autowired
    public void setKafkaProducer(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }
    @Autowired
    public void setKafkaConsumer(KafkaConsumer kafkaConsumer) {
        this.kafkaConsumer = kafkaConsumer;
    }

    public void Send(String topic, String message) {
        kafkaProducer.send(topic, message);
    }
    public void Send(String topic, Greeting message) {
        kafkaProducer.send(topic, message);
    }

}