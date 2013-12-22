package pl.scribeapp.input.handwriting;

import android.gesture.Gesture;
import android.gesture.GestureOverlayView;
import android.os.AsyncTask;
import android.util.Log;
import android.view.MotionEvent;

import pl.scribeapp.classifier.ClassificationHandler;
import pl.scribeapp.classifier.remote.RemoteClassifier;

/**
 * Created by piotrek on 27.11.13.
 */
public class WordRecognizer implements GestureOverlayView.OnGestureListener {

    private static final String TAG = "WordRecognizer";
    private CommitTextTask current_task;
    private HandwritingInputMethod inputMethod;
    private String current_result;
    private Object recognition_lock = new Object();

    private ClassificationHandler classificationHandler;

    public WordRecognizer(HandwritingInputMethod inputMethod) {
        this.inputMethod = inputMethod;
        classificationHandler = new ClassificationHandler(inputMethod.service);
    }

    public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {
        Log.d(TAG, "GESTURE STARTED " + System.currentTimeMillis());

        inputMethod.setButtonsState(false);

        synchronized (recognition_lock) {
            if (current_task != null) overlay.removeCallbacks(current_task);
        }
    }

    public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
        Log.d(TAG, "GESTURE ENDED " + System.currentTimeMillis());

        inputMethod.setButtonsState(true);

        new RecognitionTask().execute(overlay.getGesture());

        synchronized (recognition_lock) {
            overlay.removeCallbacks(current_task);
            current_task = new CommitTextTask();
            overlay.postDelayed(current_task, inputMethod.gestureInterval);
        }
    }

    public void onGesture(GestureOverlayView overlay, MotionEvent event) {}

    public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {}

    private class RecognitionTask extends AsyncTask<Gesture, Void, String> {

        @Override
        protected String doInBackground(Gesture... gestures) {
            return classificationHandler.classify(gestures[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            synchronized (recognition_lock) {
                current_result = result;
            }
        }
    }

    private class CommitTextTask implements Runnable {
        @Override
        public void run() {
            Log.d(TAG, "Commit text");
            synchronized (recognition_lock) {
                inputMethod.service.enterWord(current_result);
                current_result = null;
                current_task = null;
                inputMethod.gestureView.clear(false);
            }
        }
    }
}
