package io.scribeapp.settings.account.async;

import android.os.AsyncTask;

import javax.security.auth.login.LoginException;

import io.scribeapp.connection.ServiceConnector;
import io.scribeapp.connection.Session;
import io.scribeapp.connection.utils.ConnectionListener;

public class LoginAsyncTask extends AsyncTask<Void, Void, Session> {
    private String username;
    private String password;
    private ConnectionListener connectionListener;

    ServiceConnector serviceConnector;

    public LoginAsyncTask(ServiceConnector connector, String username, String password, ConnectionListener connectionListener) {
        super();
        serviceConnector = connector;
        this.username = username;
        this.password = password;
        this.connectionListener = connectionListener;
    }

    @Override
    protected Session doInBackground(Void... params) {
        try {
            return serviceConnector.login(username, password);
        } catch (LoginException e) {
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
            connectionListener.onError("");
        }
    }
}
