package pl.scribeapp.settings.login.async;

import android.os.AsyncTask;

/**
 * Created by piotrekd on 12/27/13.
 */
public class LoginAsyncTask extends AsyncTask<Void, Void, String> {

        private String username;
        private String password;

        public LoginAsyncTask(String username, String password) {
            super();
            this.username = username;
            this.password = password;
        }

    @Override
    protected String doInBackground(Void... params) {
        return null;
    }
}
