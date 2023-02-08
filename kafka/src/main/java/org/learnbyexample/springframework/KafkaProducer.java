package org.learnbyexample.springframework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class KafkaProducer {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaProducer.class);

    private KafkaTemplate<String, String> kafkaTemplate;

    private KafkaTemplate<String, Greeting> kafkaTemplateGreeting;
    @Autowired
    public void setKafkaTemplate(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    @Autowired
    public void setKafkaTemplateGreeting(KafkaTemplate<String, Greeting> kafkaTemplateGreeting) {
        this.kafkaTemplateGreeting = kafkaTemplateGreeting;
    }

    public void send(String topicName , Greeting message){
        LOG.info("sending message='{}' to topic='{}'", message, topicName);
        ListenableFuture<SendResult<String, Greeting>> future = kafkaTemplateGreeting.send(topicName, message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, Greeting>>() {

            @Override
            public void onSuccess(SendResult<String, Greeting> result) {
                System.out.println("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=["
                        + message + "] due to : " + ex.getMessage());
            }
        });
    }
    public void send(String topicName , String message){
        LOG.info("sending message='{}' to topic='{}'", message, topicName);
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=["
                        + message + "] due to : " + ex.getMessage());
            }
        });
    }
}