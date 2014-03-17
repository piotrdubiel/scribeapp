package io.scribeapp.app;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.ObjectGraph;
import io.scribeapp.connection.Session;
import io.scribeapp.input.MainInputService;
import io.scribeapp.utils.SessionLoader;

/**
 * Created by piotrekd on 12/28/13.
 */
public class ScribeApplication extends Application implements Navigator {
    private ObjectGraph objectGraph;
    private Session session;

    @Inject
    SessionLoader sessionLoader;
    private MainInputService inputServiceInstance;

    public static ScribeApplication from(Context context) {
        return (ScribeApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        objectGraph = ObjectGraph.create(getModules().toArray());
        inject(this);
    }

    public ObjectGraph getObjectGraph() {
        if (objectGraph == null) {
            objectGraph = ObjectGraph.create(getModules().toArray());
        }
        return objectGraph;
    }

    @Override
    public Session getSession() {
        if (session == null) {
            session = sessionLoader.load();
        }
        return session;
    }

    @Override
    public void setSession(Session session) {
        sessionLoader.save(session);
        this.session = session;
    }

    public Application inject(Object object) {
        objectGraph.inject(object);
        return this;
    }

    protected List<Object> getModules() {
        List<Object> modules = new ArrayList<>();
        modules.add(new ApplicationModule(this));
        return modules;
    }

    public MainInputService getInputMethodService() {
        if (inputServiceInstance == null) {
            throw new IllegalStateException("No registered input method.");
        }
        return inputServiceInstance;
    }

    public void registerInputMethodService(MainInputService ims) {
        this.inputServiceInstance = ims;
    }

    public void unregisterInputMethodService() {
        inputServiceInstance = null;
    }

    public static ScribeApplication get(Context context) {
        return (ScribeApplication) context.getApplicationContext();
    }
}

