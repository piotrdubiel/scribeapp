package pl.scribeapp.app;

import android.app.Application;
import android.content.Context;
import android.inputmethodservice.InputMethodService;

import java.util.ArrayList;
import java.util.List;

import dagger.ObjectGraph;
import pl.scribeapp.connection.Session;
import pl.scribeapp.utils.SessionLoader;

/**
 * Created by piotrekd on 12/28/13.
 */
public class ScribeApplication extends Application implements Navigator {
    private ObjectGraph objectGraph;
    private Session session;

    private SessionLoader sessionLoader;
    private InputMethodService currentInputMethodService;

    public static ScribeApplication from(Context context) {
        return (ScribeApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        objectGraph = ObjectGraph.create(getModules().toArray());
        sessionLoader = new SessionLoader();
        inject(sessionLoader);
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

    public InputMethodService getInputMethodService() {
        return currentInputMethodService;
    }

    public void registerInputMethodService(InputMethodService ims) {
        this.currentInputMethodService = ims;

    }
}

