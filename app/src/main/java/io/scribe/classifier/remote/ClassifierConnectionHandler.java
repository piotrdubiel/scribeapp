package io.scribe.classifier.remote;

import de.tavendo.autobahn.WebSocketConnectionHandler;

public class ClassifierConnectionHandler extends WebSocketConnectionHandler {
    @Override
    public void onOpen() {
        super.onOpen();
    }

    @Override
    public void onClose(int code, String reason) {
        super.onClose(code, reason);
    }

    @Override
    public void onTextMessage(String payload) {
        super.onTextMessage(payload);
    }

    @Override
    public void onRawTextMessage(byte[] payload) {
        super.onRawTextMessage(payload);
    }

    @Override
    public void onBinaryMessage(byte[] payload) {
        super.onBinaryMessage(payload);
    }
}
