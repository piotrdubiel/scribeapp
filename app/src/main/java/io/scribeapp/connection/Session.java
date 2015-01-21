package io.scribeapp.connection;

public final class Session {
    public final String username;
    public final String token;

    public Session(String username, String token) {
        this.username = username;
        this.token = token;
    }
}
