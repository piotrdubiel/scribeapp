package io.scribe.settings.registration.async;

import android.os.AsyncTask;

import io.scribe.connection.ServiceConnector;
import io.scribe.connection.Session;
import io.scribe.connection.utils.ConnectionListener;

/**
 * Created by piotrekd on 10/02/14.
 */
public class RegisterAsyncTask extends AsyncTask<Void, Void, Session> {
    private String username;
    private String password;
    private ConnectionListener connectionListener;

    ServiceConnector serviceConnector;

    public RegisterAsyncTask(ServiceConnector serviceConnector, String username, String password, ConnectionListener connectionListener) {
        super();

        this.serviceConnector = serviceConnector;
        this.username = username;
        this.password = password;
        this.connectionListener = connectionListener;
    }

    @Override
    protected Session doInBackground(Void... params) {
        try {
            return serviceConnector.register(username, password);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Session session) {
        if (session != null) {
            super.onPostExecute(session);
            connectionListener.onOK(session);
        }
        else {
            connectionListener.onError("Unable to register");
        }
    }

}
