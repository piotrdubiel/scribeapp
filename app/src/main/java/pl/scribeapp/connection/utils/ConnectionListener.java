package pl.scribeapp.connection.utils;

import pl.scribeapp.connection.Session;

/**
 * Created by piotrekd on 1/1/14.
 */
public interface ConnectionListener {
    void onOK(Session session);

    void onError(String message);
}
