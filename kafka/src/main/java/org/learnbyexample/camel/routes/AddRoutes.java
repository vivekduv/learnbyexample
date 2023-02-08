package org.learnbyexample.camel.routes;

import org.apache.camel.CamelContext;
import org.apache.camel.RoutesBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Set;

public class AddRoutes implements RoutesBuilder {

    ApplicationContext applicationContext ;

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void addRoutesToCamelContext(CamelContext context) throws Exception {
        setApplicationContext(new ClassPathXmlApplicationContext("appContextCamel.xml"));
        WordRouteConsumer wordRouteConsumer = applicationContext.getBean(WordRouteConsumer.class);
        WordRouteProducer wordRouteProducer = applicationContext.getBean(WordRouteProducer.class);
        context.addRoutes(wordRouteConsumer);
        context.addRoutes(wordRouteProducer);
    }

    @Override
    public Set<String> updateRoutesToCamelContext(CamelContext context) throws Exception {
        return null;
    }
}
