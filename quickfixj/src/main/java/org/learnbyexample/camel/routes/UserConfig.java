package org.learnbyexample.camel.routes;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserConfig {

    @Value("${fix.incoming.session}")
    private String fixIncomingSession;

    @Value("${fix.outgoing.session}")
    private String fixOutgoingSession;

    public String getFixIncomingSession() {
        return fixIncomingSession;
    }

    public void setFixIncomingSession(String fixIncomingSession) {
        this.fixIncomingSession = fixIncomingSession;
    }

    public String getFixOutgoingSession() {
        return fixOutgoingSession;
    }

    public void setFixOutgoingSession(String fixOutgoingSession) {
        this.fixOutgoingSession = fixOutgoingSession;
    }
}