package io.scribeapp.app;

import io.scribeapp.connection.Session;

/**
 * Created by piotrekd on 12/29/13.
 */
public interface Navigator {
    Session getSession();
    void setSession(Session session);
}
