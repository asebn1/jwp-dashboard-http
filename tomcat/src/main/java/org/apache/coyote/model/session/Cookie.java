package org.apache.coyote.model.session;

import java.util.UUID;

public class Cookie {

    public static final String JSESSIONID = "JSESSIONID";
    private final UUID uuid;

    public Cookie() {
        this.uuid = UUID.randomUUID();
    }

    @Override
    public String toString() {
        return JSESSIONID + "=" + uuid.toString();
    }
}
