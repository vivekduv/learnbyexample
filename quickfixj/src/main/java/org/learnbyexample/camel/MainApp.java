package org.learnbyexample.camel;
import org.apache.camel.main.Main;
import org.learnbyexample.camel.routes.AddRoutes;
import org.learnbyexample.camel.routes.FixRoute;

public class MainApp {
    public static void main(String... args) throws Exception {
        Main main = new Main();
       main.configure().addRoutesBuilder(new AddRoutes());
       // main.addRouteBuilder(new FixRoute());
        main.run(args);


    }
}
