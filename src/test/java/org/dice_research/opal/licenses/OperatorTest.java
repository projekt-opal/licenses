package org.dice_research.opal.licenses;

import org.dice_research.opal.licenses.operator.Operator;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests {@link Operator}.
 * 
 * @author Adrian Wilke
 */
public class OperatorTest {

	@Test
	public void testOr() {
		boolean attributesA[] = { true, true, false, false };
		boolean attributesB[] = { true, false, true, false };
		boolean actuals[] = new Operator().compute(attributesA, attributesB);
		boolean expected[] = { true, true, true, false };
		Assert.assertArrayEquals(expected, actuals);
	}

	@Test
	public void testSame() {
		boolean attributesA[] = { true, false };
		boolean attributesB[] = attributesA.clone();
		boolean actuals[] = new Operator().compute(attributesA, attributesB);
		boolean expected[] = { true, false };
		Assert.assertArrayEquals(expected, actuals);
	}

	@Test
	public void testCombination() {
		boolean attributesA[] = { true, false, false };
		boolean attributesB[] = { false, true, false };
		boolean attributesC[] = { false, false, true };

		boolean actuals1[] = new Operator().compute(attributesA, new Operator().compute(attributesB, attributesC));
		boolean expected1[] = { true, true, true };
		Assert.assertArrayEquals(expected1, actuals1);

		boolean actuals2[] = new Operator().compute(new Operator().compute(attributesA, attributesB), attributesC);
		boolean expected2[] = { true, true, true };
		Assert.assertArrayEquals(expected2, actuals2);
	}

}
