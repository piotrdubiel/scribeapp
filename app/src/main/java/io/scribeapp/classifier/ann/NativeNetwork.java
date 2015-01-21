package io.scribeapp.classifier.ann;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import io.scribeapp.classifier.model.LabelClassificationResult;
import android.gesture.Gesture;

public class NativeNetwork implements Network {
	static {
		System.loadLibrary("ann");
	}

	private int network;

	@Override
	public List<List<Vector>> train(float[] in, int label) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Network load(InputStream file_input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(OutputStream output) {
		// TODO Auto-generated method stub

	}

	@Override
	public int[] getTopology() {
		// TODO Auto-generated method stub
		return null;
	}

	public LabelClassificationResult classify(Gesture gesture, int type) {
		return null;
	}

    @Override
    public io.scribeapp.classifier.model.ClassificationResult classify(Gesture gesture) {
        return null;
    }

    @Override
	public LabelClassificationResult classify(float[] sample) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float[] classifyRaw(float[] sample) {
		// TODO Auto-generated method stub
		return null;
	}
}
