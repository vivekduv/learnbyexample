package org.learnbyexample.springframework;

import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@EnableKafka
public class KafkaConsumer {
    @KafkaListener
            (topics = "#{'${spring.kafka.listener.topic.1}'}",
                    properties = "{'${spring.kafka.bootstrap-servers}'}"
                    , groupId="#{'${spring.kafka.groupId}'}"
            )
    public void consume(String quote) {
        System.out.println("Received Quote= " + quote);
    }

   @KafkaListener(topics = "#{'${spring.kafka.listener.topic.2}'}",
            properties = "{'${spring.kafka.bootstrap-servers}'}")
    public void listenWithHeaders(
            @Payload String message,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
        System.out.println(
                "Received Message: " + message + "from partition: " + partition);
    }

  /*  @KafkaListener(
            topics = "demo_java",
            containerFactory = "filterKafkaListenerContainerFactory")
    public void listenWithFilter(String message) {
        System.out.println("Received Message in filtered listener: " + message);
    }*/
}
