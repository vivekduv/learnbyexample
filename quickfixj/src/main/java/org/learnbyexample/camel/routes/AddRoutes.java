package org.learnbyexample.camel.routes;

import org.apache.camel.CamelContext;
import org.apache.camel.Component;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.component.quickfixj.QuickfixjComponent;
import org.apache.camel.impl.DefaultCamelContext;
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
        setApplicationContext(new ClassPathXmlApplicationContext("quickfix-spring.xml"));
        //context=applicationContext.getBean("camel",CamelContext.class);
        //context=new DefaultCamelContext();
      //  Component a = context.getComponent("quickfix");
        //context.addComponent("quickfix",new QuickfixjComponent());
       // FixRoute fixRoute = applicationContext.getBean(FixRoute.class);
      // context.addRoutes(fixRoute);
    }

    @Override
    public Set<String> updateRoutesToCamelContext(CamelContext context) throws Exception {
        return null;
    }

//    @Override
//    public Set<String> updateRoutesToCamelContext(CamelContext context) throws Exception {
//        return null;
//    }

}
