package pl.scribeapp.classifier;

import android.gesture.Gesture;

public interface Classifier {
    public static final int SMALL_ALPHA = 1;
    public static final int CAPITAL_ALPHA = 2;
    public static final int DIGIT = 3;
    public static final int GROUP = 4;

	/**
	 * Klasyfikacja gestu o danym typie i
	 * opakowanie wyniku w obiekt ClassificationResult
	 * @param gesture gest do klasyfikacji
	 * @param type typ klasyfikowanego znaku
	 * @return rezultat klasyfikacji w obiekcie ClassificationResult
	 */
	public ClassificationResult classify(Gesture gesture, int type);
	/**
	 * Klasyfikacja wektora cech zapisanego jako tablica wartości float 
	 * i opakowanie wyniku w obiekt ClassificationResult
	 * @param sample tablica z wartościami cech
	 * @return rezultat klasyfikacji w obiekcie ClassificationResult
	 */
	public ClassificationResult classify(float[] sample);
	
	/**
	 * Klasyfikacja wektora cech jako tablica wartości float 
	 * i  przekazanie wyniku jako wektor również w tablicy wartości  float.
	 * @param sample tablica z wartościami cech
	 * @return  rezultat klasyfikacji
	 */
	public float[] classifyRaw(float[] sample);
	
}
