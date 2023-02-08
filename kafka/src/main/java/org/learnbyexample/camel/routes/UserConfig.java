package org.learnbyexample.camel.routes;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserConfig {

    @Value("${kafka.consume.url}")
    private String kafkaConsumeUrl;
    @Value("${kafka.produce.url}")
    private String produceUrl;

    public String getProduceUrl() {
        return produceUrl;
    }

    public void setProduceUrl(String produceUrl) {
        this.produceUrl = produceUrl;
    }

    public String getKafkaConsumeUrl() {
        return kafkaConsumeUrl;
    }

    public void setKafkaConsumeUrl(String kafkaConsumeUrl) {
        this.kafkaConsumeUrl = kafkaConsumeUrl;
    }
}