package org.learnbyexample.camel.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.PredicateBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.quickfixj.CannotSendException;
import org.apache.camel.component.quickfixj.QuickfixjEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import quickfix.FieldNotFound;
import quickfix.Message;
import quickfix.Session;
import quickfix.SessionID;
import quickfix.field.MsgType;
import quickfix.field.SenderCompID;
import quickfix.field.Symbol;
import quickfix.field.TargetCompID;
import quickfix.fix42.ExecutionReport;


@Component
public class FixRoute extends RouteBuilder {
    UserConfig userConfig;

    @Autowired
    public void setUserConfig(UserConfig userConfig) {
        this.userConfig = userConfig;
    }
    @Override
    public void configure() throws Exception {

        from("seda:main").process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {

                        ExecutionReport message =  exchange.getIn().getBody(ExecutionReport.class);
                        Symbol symbol = message.get(new Symbol());
                        symbol.setValue("NEW Symbol");
                        message.set(symbol);
                        exchange.getIn().setBody(message);

                    }
                })
                .doTry()
                .to("quickfixjComponent:example?sessionID=" + userConfig.getFixIncomingSession())
                .doCatch(CannotSendException.class)
                .to("seda:fixexception")
                .end();

       from("quickfixjComponent:example?sessionID=" + userConfig.getFixIncomingSession() )
       //        .filter(header(QuickfixjEndpoint.EVENT_CATEGORY_KEY).isEqualTo(QuickfixjEventCategory.AppMessageReceived))
           .filter(
                   PredicateBuilder.or(
                   header(QuickfixjEndpoint.MESSAGE_TYPE_KEY).isEqualTo(MsgType.EXECUTION_REPORT), header(QuickfixjEndpoint.MESSAGE_TYPE_KEY).isEqualTo(MsgType.ALLOCATION_INSTRUCTION))
                    ).process(new Processor() {
                   @Override
                   public void process(Exchange exchange) throws Exception {
                       Message message = (quickfix.fix42.Message) exchange.getIn().getBody();
                       SenderCompID senderCompID = new SenderCompID();

                       try {
                           message.getHeader().getField(senderCompID);
                           if (senderCompID.getValue().equalsIgnoreCase("MAC"))
                           {
                               exchange.setRouteStop(true);
                               return;
                           }
                       } catch (FieldNotFound fieldNotFound) {

                           exchange.setRouteStop(true);
                           return;
                       }
                       TargetCompID receivedTargetCompID = new TargetCompID();
                       try {
                           message.getHeader().getField(receivedTargetCompID);
                       } catch (FieldNotFound fieldNotFound) {

                           exchange.setRouteStop(true);
                           return;
                       }

                       final Session session = quickfix.Session.lookupSession(new SessionID(userConfig.getFixIncomingSession()));


                       if (session != null && session.isLoggedOn()) {
                           exchange.getIn().setHeader("FIX_SESSION_STATUS", "true");
                       } else {
                           exchange.getIn().setHeader("FIX_SESSION_STATUS", "false");
                       }
                   }



               })

            .choice()
							.when(header("FIX_SESSION_STATUS").isEqualTo("true")).to("seda:main")
            .when(header("FIX_SESSION_STATUS").isEqualTo("false")).to("seda:fixexception");
               //.to("quickfixjComponent:example?sessionID=" + userConfig.getFixIncomingSession());

    }


}
