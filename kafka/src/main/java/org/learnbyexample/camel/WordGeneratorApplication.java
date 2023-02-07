package org.learnbyexample.camel;


import org.apache.camel.main.Main;
import org.learnbyexample.camel.routes.AddRoutes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Configuration
@ComponentScan(basePackages = "org.learnbyexample.camel.routes")
@Component
public class WordGeneratorApplication {
    private ApplicationContext applicationContext;

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public WordGeneratorApplication(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static void main(String... args) throws Exception {


        ClassPathXmlApplicationContext appcontext =
                new ClassPathXmlApplicationContext("appContextCamel.xml");


        WordGeneratorApplication wordGeneratorApplication = new WordGeneratorApplication(appcontext);
        for (String beanName : appcontext.getBeanDefinitionNames()) {
            System.out.println("BeanName: " + beanName);
        }

//        ApplicationContext context1 =
//                new AnnotationConfigApplicationContext(WordGeneratorApplication.class);
//
//        for (String beanName : context1.getBeanDefinitionNames()) {
//            System.out.println("BeanName2: " + beanName);
//        }

     /*   final CamelContext camelContext = wordGeneratorApplication.getApplicationContext().getBean("camel",CamelContext.class);

        camelContext.addRoutes(wordRoute);

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    camelContext.stop();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }));*/
    //    wordRoute =context.getBean(WordRoute.class);
        Main main = new Main();
        main.configure().addRoutesBuilder(new AddRoutes());
      /*main.configure().addRoutesBuilder(new RoutesBuilder() {
          @Override
          public void addRoutesToCamelContext(CamelContext context) throws Exception {
              WordRoute wordRoute = appcontext.getBean(WordRoute.class);
              WordRoute2 wordRoute2 = appcontext.getBean(WordRoute2.class);
              context.addRoutes(wordRoute);
              context.addRoutes(wordRoute2);
          }

          @Override
          public Set<String> updateRoutesToCamelContext(CamelContext context) throws Exception {
              return null;
          }
      });*/

        main.run(args);

        /* CamelContext ctx = applicationContext.getBean("camel", CamelContext.class);
//CamelContext ctx= new DefaultCamelContext();
        try {

            ctx.addRoutes(wordRoute);
            ctx.start();
            Thread.sleep(5 * 60 * 1000);
            ctx.stop();
        }
        catch (Exception e) {

        }*/
    }

}
