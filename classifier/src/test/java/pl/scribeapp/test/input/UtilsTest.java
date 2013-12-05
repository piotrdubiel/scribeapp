package pl.scribedroid.input;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import pl.scribedroid.ScribeDroidTestRunner;
import pl.scribedroid.input.classificator.Classificator;
import android.gesture.Gesture;
import android.gesture.GesturePoint;
import android.gesture.GestureStroke;

/**
 * @author Piotr Dubiel
 * 
 */

@RunWith(RobolectricGradleTestRunner.class)
public class UtilsTest {
	@Before
	public void setUp() {
	}

	@Test
	public void testPossibleGestures() {
		// given
		Gesture gesture = new Gesture();
		ArrayList<GesturePoint> points = new ArrayList<GesturePoint>();
		gesture.addStroke(new GestureStroke(points));
		gesture.addStroke(new GestureStroke(points));
		gesture.addStroke(new GestureStroke(points));
		gesture.addStroke(new GestureStroke(points));
		gesture.addStroke(new GestureStroke(points));

		// when
		List<Gesture> gestures = Utils.getAllPossibleGestures(gesture);

		// then
		Assert.assertEquals(11, gestures.size());
	}
}