package pl.scribeapp.connection;

/**
 * Created by piotrekd on 12/28/13.
 */
public final class Session {
    public final String username;
    public final String token;

    public Session(String username, String token) {
        this.username = username;
        this.token = token;
    }
}
