package pl.scribeapp.input.classificator;

import java.io.IOException;

import pl.scribeapp.R;
import pl.scribeapp.input.Utils;
import pl.scribeapp.input.ann.Network;
import pl.scribeapp.input.ann.NetworkImpl;
import pl.scribeapp.input.classificator.ClassificationResult.Label;
import android.content.Context;
import android.gesture.Gesture;
import android.graphics.Bitmap;
import android.util.Log;

public class MetaClassificator implements Classificator {
	private static final String TAG = "MetaClassificator";

	private Network small_net;
	private Network capital_net;
	private Network digit_net;

	private GestureLibraryClassificator small_library;
	private GestureLibraryClassificator capital_library;
	private GestureLibraryClassificator number_library;
	private PCA pca;
	private Context context;

	public MetaClassificator(Context c) {
		context = c;
		try {
			pca = new PCA(c);

			small_net = NetworkImpl.createFromInputStream(context.getAssets().open("pl_small_net"), Classificator.SMALL_ALPHA);
			capital_net = NetworkImpl.createFromInputStream(context.getAssets().open("pl_capital_net"), Classificator.CAPITAL_ALPHA);
			digit_net = NetworkImpl.createFromInputStream(context.getAssets().open("digit_net"), Classificator.DIGIT);
			
			small_library = new GestureLibraryClassificator(c, GestureLibraryClassificator.USER_SMALL_FILENAME);
			if (!small_library.isValid()) {
				Log.d(TAG, "Default small lib");
				small_library = new GestureLibraryClassificator(c, R.raw.default_alpha_lib);
			}

			capital_library = new GestureLibraryClassificator(c, GestureLibraryClassificator.USER_CAPITAL_FILENAME);
			if (!capital_library.isValid()) {
				capital_library = new GestureLibraryClassificator(c, R.raw.default_alpha_lib);
				Log.d(TAG, "Default capital lib");
			}

			number_library = new GestureLibraryClassificator(c, GestureLibraryClassificator.USER_DIGIT_FILENAME);
			if (!number_library.isValid()) {
				number_library = new GestureLibraryClassificator(c, R.raw.default_number_lib);
				Log.d(TAG, "Default number lib");
			}

		}
		catch (IOException e) {
			e.printStackTrace();
		}

		Log.i(TAG, "Classificator loaded");
	}

	/** (non-Javadoc)
	 * Zwraca dla gestu o danym typie listę znaków, które ten gest może przedstawiać, zawartą w obiekcie ClassificationResult.
	 * @see pl.scribeapp.input.classificator.Classificator#classify(android.gesture.Gesture, int)
	 */
	@Override
	public ClassificationResult classify(Gesture gesture, int type) {
		float[] sample = prepare(gesture);
		if (sample == null) return null;
		ClassificationResult result = null;
		
		float threshold = 0.0000001f;

		if (type == Classificator.SMALL_ALPHA) {
			ClassificationResult small_net_result = small_net.classify(sample);
			ClassificationResult small_lib_result = small_library.classify(gesture, Classificator.SMALL_ALPHA);
			
			ClassificationResult pairs = small_net_result.pairsWith(small_lib_result);

			if (pairs.result.size() > 0)  {
				result = pairs;
			}
			else {
				result = small_net_result.filter(threshold);
			}
		}
		else if (type == Classificator.CAPITAL_ALPHA) {
			ClassificationResult capital_net_result = capital_net.classify(sample);
			ClassificationResult capital_lib_result = capital_library.classify(gesture, Classificator.CAPITAL_ALPHA);

			ClassificationResult pairs = capital_net_result.pairsWith(capital_lib_result);
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
		else if (type == Classificator.DIGIT) {
			ClassificationResult digit_net_result = digit_net.classify(sample);
			ClassificationResult digit_lib_result = number_library.classify(gesture, Classificator.DIGIT);
			
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
	 * @see pl.scribeapp.input.classificator.Classificator#classify(float[])
	 */
	@Override
	public ClassificationResult classify(float[] sample) {
		return null;
	}

	/**
	 * Nieużywane, zawsze zwraca null
	 * @see pl.scribeapp.input.classificator.Classificator#classifyRaw(float[])
	 */
	@Override
	public float[] classifyRaw(float[] sample) {
		return null;
	}
}
