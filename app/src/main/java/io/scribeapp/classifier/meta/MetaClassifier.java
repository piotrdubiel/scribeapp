package io.scribeapp.classifier.meta;

import java.io.IOException;

import io.scribeapp.R;
import io.scribeapp.classifier.Classifier;
import io.scribeapp.classifier.ann.Network;
import io.scribeapp.classifier.ann.NetworkImpl;
import io.scribeapp.classifier.artifacts.ClassificationResult;
import io.scribeapp.classifier.artifacts.LabelClassificationResult;
import io.scribeapp.classifier.artifacts.LabelClassificationResult.Label;
import io.scribeapp.classifier.gesture.GestureLibraryClassifier;
import io.scribeapp.classifier.utils.PCA;
import io.scribeapp.classifier.utils.Utils;
import io.scribeapp.utils.inject.ForContext;

import android.content.Context;
import android.gesture.Gesture;
import android.graphics.Bitmap;
import android.util.Log;

import javax.inject.Inject;

public class MetaClassifier implements Classifier {
	private static final String TAG = "MetaClassifier";

	private Network small_net;
	private Network capital_net;
	private Network digit_net;

	private GestureLibraryClassifier small_library;
	private GestureLibraryClassifier capital_library;
	private GestureLibraryClassifier number_library;
	private PCA pca;

    @Inject
	public MetaClassifier(Context context) {
		try {
			pca = new PCA(context);

			small_net = NetworkImpl.createFromInputStream(context.getAssets().open("pl_small_net"), SMALL_ALPHA);
			capital_net = NetworkImpl.createFromInputStream(context.getAssets().open("pl_capital_net"), CAPITAL_ALPHA);
			digit_net = NetworkImpl.createFromInputStream(context.getAssets().open("digit_net"), DIGIT);
			
			small_library = new GestureLibraryClassifier(context, GestureLibraryClassifier.USER_SMALL_FILENAME);
			if (!small_library.isValid()) {
				Log.d(TAG, "Default small lib");
				small_library = new GestureLibraryClassifier(context, R.raw.default_small_lib);
			}

			capital_library = new GestureLibraryClassifier(context, GestureLibraryClassifier.USER_CAPITAL_FILENAME);
			if (!capital_library.isValid()) {
				capital_library = new GestureLibraryClassifier(context, R.raw.default_capital_lib);
				Log.d(TAG, "Default capital lib");
			}

			number_library = new GestureLibraryClassifier(context, GestureLibraryClassifier.USER_DIGIT_FILENAME);
			if (!number_library.isValid()) {
				number_library = new GestureLibraryClassifier(context, R.raw.default_digit_lib);
				Log.d(TAG, "Default number lib");
			}

		}
		catch (IOException e) {
			e.printStackTrace();
		}

		Log.i(TAG, "Classifier loaded");
	}

	public ClassificationResult classify(Gesture gesture, int type) {
		float[] sample = prepare(gesture);
		if (sample == null) return null;
		LabelClassificationResult result = null;
		
		float threshold = 0.0000001f;

		if (type == SMALL_ALPHA) {
			LabelClassificationResult small_net_result = (LabelClassificationResult) small_net.classify(sample);
			LabelClassificationResult small_lib_result = small_library.classify(gesture, SMALL_ALPHA);
			
			LabelClassificationResult pairs = small_net_result.pairsWith(small_lib_result);

			if (pairs.result.size() > 0)  {
				result = pairs;
			}
			else {
				result = small_net_result.filter(threshold);
			}
		}
		else if (type == CAPITAL_ALPHA) {
			LabelClassificationResult capital_net_result = (LabelClassificationResult) capital_net.classify(sample);
			LabelClassificationResult capital_lib_result = capital_library.classify(gesture, CAPITAL_ALPHA);

			LabelClassificationResult pairs = capital_net_result.pairsWith(capital_lib_result);
			if (pairs.result.size() > 0) {
				result = pairs;
			}
			else {
				result = capital_net_result.filter(threshold);
				Label[] labelsWithBelief = result.getLabelsWithBelief();
				if (labelsWithBelief.length > 0)
				Log.d(TAG, "Best from net: "+labelsWithBelief[0].belief+ " "+labelsWithBelief[0].label);
			}
		}
		else if (type == DIGIT) {
			LabelClassificationResult digit_net_result = (LabelClassificationResult) digit_net.classify(sample);
			LabelClassificationResult digit_lib_result = number_library.classify(gesture, DIGIT);
			
			result = digit_net_result.combine(digit_lib_result).filter(threshold);
		}

		if (result.isEmpty()) result = null;

//		if (result != null) {
//			for (Label l : result.result)
//				Log.i(TAG, "RESULT: " + l.label + " " + l.belief);
//
//			Character c = result.getLabels()[0];
//			try {
//				Utils.saveBitmap(bitmap, "P-" + System.currentTimeMillis() + "-" + c);
//			}
//			catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
		

		return result;
	}

    @Override
    public ClassificationResult classify(Gesture gesture) {
        return null;
    }

    /**
	 * Konwertuje podany gest na wektor zapisany w tablicy wartości float.
	 * Wektor ten jest tworzony zgodnie z formatem MNIST, a podlega analizie głównych składowych (PCA).
	 * @param gesture
	 * @return
	 */
	private float[] prepare(Gesture gesture) {
		Bitmap in = Utils.getBitmapFromGesture(gesture);
		if (in == null) return null;
		if (in.getWidth() > in.getHeight()) {
			in = Bitmap.createScaledBitmap(in, 20, Math.max(20 * in.getHeight() / in.getWidth(), 1), true);
		}
		else {
			in = Bitmap.createScaledBitmap(in, Math.max(20 * in.getWidth() / in.getHeight(), 1), 20, true);
		}

		float[] sample = Utils.getVectorFromBitmap(in);

		return pca.applyPCA(sample);
	}

	/**
	 * Nieużywane, zawsze zwraca null
	 * @see Classifier#classify(float[])
	 */
	@Override
	public LabelClassificationResult classify(float[] sample) {
		return null;
	}

	/**
	 * Nieużywane, zawsze zwraca null
	 * @see Classifier#classifyRaw(float[])
	 */
	@Override
	public float[] classifyRaw(float[] sample) {
		return null;
	}
}
