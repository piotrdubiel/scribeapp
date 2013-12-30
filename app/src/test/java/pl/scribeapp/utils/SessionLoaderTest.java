package pl.scribeapp.utils;

import android.content.Context;
import android.os.Environment;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.shadows.ShadowContext;
import org.robolectric.shadows.ShadowEnvironment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.inject.Inject;

import pl.scribeapp.connection.Session;
import pl.scribeapp.test.RobolectricGradleTestRunner;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.when;
import static pl.scribeapp.test.TestInject.injectMocks;

@RunWith(RobolectricGradleTestRunner.class)
public class SessionLoaderTest {
    @Inject
    SessionLoader sessionLoader;

    @Before
    public void before() {
        injectMocks(this);
        ShadowEnvironment.setExternalStorageState(Environment.MEDIA_MOUNTED);
        assertNotNull(sessionLoader);
    }

    @After
    public void after() {
        ShadowEnvironment.setExternalStorageState(Environment.MEDIA_UNMOUNTED);

    }

    @Test
    public void shouldInjectContext() {
        assertNotNull(sessionLoader.context);
    }

    @Test
    public void shouldReturnNullForNoFile() throws IOException {
        when(sessionLoader.context.openFileInput(Config.SESSION_FILENAME))
                .thenThrow(FileNotFoundException.class);
        assertNull(sessionLoader.load());
    }

    @Test
    public void shouldLoadSession() throws IOException {
        when(sessionLoader.context.openFileInput(Config.SESSION_FILENAME))
                .thenReturn(new FileInputStream(prepareFile(new String[]{"username", "token"})));
        Session session = sessionLoader.load();
        assertNotNull(session);
        assertEquals("username", session.username);
        assertEquals("token", session.token);
    }

    @Test
    public void shouldSaveSessionOnExisting() throws IOException {
        when(sessionLoader.context.openFileOutput(Config.SESSION_FILENAME, Context.MODE_PRIVATE))
                .thenReturn(new FileOutputStream(prepareFile(new String[]{"username", "token"})));
        Session session = new Session("test", "abc");
        sessionLoader.save(session);

        File file = new File(ShadowEnvironment.getExternalStorageDirectory(), Config.SESSION_FILENAME);
        assertTrue(file.exists());

    }

    private File prepareFile(String[] lines) throws IOException {
        File file = new File(ShadowEnvironment.getExternalStorageDirectory(), Config.SESSION_FILENAME);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        if (lines != null)
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        writer.close();
        return file;
    }
}
