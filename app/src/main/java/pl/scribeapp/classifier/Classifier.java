package pl.scribeapp.classifier;

import android.gesture.Gesture;

import pl.scribeapp.classifier.artifacts.ClassificationResult;
import pl.scribeapp.classifier.artifacts.LabelClassificationResult;
import pl.scribeapp.connection.exceptions.RecognitionException;

public interface Classifier {
    public static final int SMALL_ALPHA = 1;
    public static final int CAPITAL_ALPHA = 2;
    public static final int DIGIT = 3;
    public static final int GROUP = 4;

	/**
	 * Klasyfikacja gestu i
	 * opakowanie wyniku w obiekt LabelClassificationResult
	 * @param gesture wprowadzony gest do klasyfikacji
	 * @return rezultat klasyfikacji w obiekcie LabelClassificationResult
	 */
	public ClassificationResult classify(Gesture gesture) throws RecognitionException;

	/**
	 * Klasyfikacja wektora cech zapisanego jako tablica wartości float 
	 * i opakowanie wyniku w obiekt LabelClassificationResult
	 * @param sample tablica z wartościami cech
	 * @return rezultat klasyfikacji w obiekcie LabelClassificationResult
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
