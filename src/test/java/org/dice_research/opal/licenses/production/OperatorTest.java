package org.dice_research.opal.licenses.production;

import java.util.LinkedList;
import java.util.List;

import org.dice_research.opal.licenses.Operator;
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
		boolean actual[] = new Operator().compute(attributesA, attributesB);
		boolean expected[] = { true, true, true, false };
		Assert.assertArrayEquals(expected, actual);
	}

	@Test
	public void testEmpty() {
		boolean attributesA[] = {};
		boolean attributesB[] = {};
		boolean actual[] = new Operator().compute(attributesA, attributesB);
		boolean expected[] = {};
		Assert.assertArrayEquals(expected, actual);
	}

	@Test
	public void testList() {
		List<boolean[]> list = new LinkedList<>();
		Assert.assertNull(new Operator().compute(list));

		boolean attributesA[] = { true, true, false, false, false };
		boolean attributesB[] = { true, false, true, false, false };
		boolean attributesC[] = { true, false, false, true, false };
		list.add(attributesA);
		list.add(attributesB);
		list.add(attributesC);
		boolean actual[] = new Operator().compute(list);
		boolean expected[] = { true, true, true, true, false };
		Assert.assertArrayEquals(expected, actual);
	}

	@Test
	public void testIdempotency() {
		boolean attributesA[] = { true, false };
		boolean attributesB[] = attributesA.clone();
		boolean actual[] = new Operator().compute(attributesA, attributesB);
		boolean expected[] = { true, false };
		Assert.assertArrayEquals(expected, actual);
	}

	@Test
	public void testAssociativity() {
		boolean attributesA[] = { true, true, false, false, false };
		boolean attributesB[] = { true, false, true, false, false };
		boolean attributesC[] = { true, false, false, true, false };

		boolean actual1[] = new Operator().compute(attributesA, new Operator().compute(attributesB, attributesC));
		boolean expected1[] = { true, true, true, true, false };
		Assert.assertArrayEquals(expected1, actual1);

		boolean actual2[] = new Operator().compute(new Operator().compute(attributesA, attributesB), attributesC);
		boolean expected2[] = { true, true, true, true, false };
		Assert.assertArrayEquals(expected2, actual2);
	}

	@Test
	public void testCommutativity() {
		boolean attributesA[] = { true, true, false, false };
		boolean attributesB[] = { true, false, true, false };
		boolean actual[] = new Operator().compute(attributesA, attributesB);
		boolean expected[] = new Operator().compute(attributesB, attributesA);
		Assert.assertArrayEquals(expected, actual);
	}

}