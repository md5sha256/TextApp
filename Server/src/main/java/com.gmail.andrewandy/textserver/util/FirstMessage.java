package com.gmail.andrewandy.textserver.util;

import java.util.UUID;

public class FirstMessage {
    UUID clientID = UUID.randomUUID();

    public UUID getClientID() {
        return clientID;
    }

    public void setClientID(UUID clientID) {
        this.clientID = clientID;
    }
}
