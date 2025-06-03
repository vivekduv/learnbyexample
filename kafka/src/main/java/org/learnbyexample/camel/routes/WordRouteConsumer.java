package org.learnbyexample.camel.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spi.IdempotentRepository;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WordRouteConsumer extends RouteBuilder {
    UserConfig userConfig;

    @Value("${file.input-directory}")
    private String inputDirectory;

    @Value("${file.processed-directory}")
    private String processedDirectory;

    @Autowired
    private IdempotentRepository idempotentRepository;


    @Autowired
    public void setUserConfig(UserConfig userConfig) {
        this.userConfig = userConfig;
    }

    @Override
    public void configure() throws Exception {


        onException(Exception.class)
                .log("Error occurred: ${exception.message}")
                .handled(true)
                .stop(); // Stop processing current exchange on error



        from("file://" + inputDirectory + "?noop=true&include=.*\\.csv")
                .routeId("CsvToKafkaRoute")
                .log("Starting to process file: ${header.CamelFileName}")
                .split(body().tokenize("\n")).streaming() // Process line by line
                .process(exchange -> {
                    String line = exchange.getIn().getBody(String.class);
                    String fileName = exchange.getIn().getHeader("CamelFileName", String.class);

                    // Create unique key using filename + line number + content hash
                    String uniqueKey = fileName + ":" + line;
                    exchange.getIn().setHeader("UniqueKey", uniqueKey);
                })
                //.filter(simple("${body} != '' ")) // Skip empty lines and comments
                .idempotentConsumer(header("UniqueKey"), idempotentRepository) // Use file-based idempotent repository
                .process(exchange -> {
                    String csvLine = exchange.getIn().getBody(String.class);
                    exchange.getMessage().setBody(csvLine);

                })

                .log("Publishing to Kafka: ${body}")
                //.to("kafka:" + kafkaTopic + "?brokers={{kafka.bootstrap-servers}}")
                .to("kafkaComponent:demo_java")

                .end()
                .end()
                //.log("Completed processing file: ${header.CamelFileName}")
                .to("file://" + processedDirectory + "?fileName=${header.CamelFileName}.processed");



//        from("kafkaComponent:demo_java")
//                .log("Component Message received from Kafka : ${body}")
//                .log("    on the topic ${headers[kafka.TOPIC]}")
//                .log("    on the partition ${headers[kafka.PARTITION]}")
//                .log("    with the offset ${headers[kafka.OFFSET]}")
//                .log("    with the key ${headers[kafka.KEY]}");

        /*   from( userConfig.getKafkaConsumeUrl())
                .log("Message received from Kafka : ${body}")
                .log("    on the topic ${headers[kafka.TOPIC]}")
                .log("    on the partition ${headers[kafka.PARTITION]}")
                .log("    with the offset ${headers[kafka.OFFSET]}")
                .log("    with the key ${headers[kafka.KEY]}");
    */
    }
}
