package org.dice_research.opal.licenses.operator;

import java.util.List;

/**
 * Combines attributes by by bitwise inclusive OR ('|').
 * 
 * @author Adrian Wilke
 */
public class Operator {

	/**
	 * Combines boolean attributes of the input arrays by bitwise inclusive OR
	 * ('|').
	 * 
	 * @param attributesA Array of boolean attributes.
	 * @param attributesB Array of boolean attributes.
	 * @return OR combination of the input arrays.
	 * @throws RuntimeException if the lengths of input arrays are different.
	 */
	public boolean[] compute(boolean[] attributesA, boolean[] attributesB) {
		if (attributesA.length != attributesB.length) {
			throw new RuntimeException("Different length of arrays.");
		}

		boolean[] result = new boolean[attributesA.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = attributesA[i] | attributesB[i];
		}
		return result;
	}

	/**
	 * Combines boolean attributes of the input arrays by bitwise inclusive OR
	 * ('|').
	 * 
	 * Iteratively calls {@link #compute(boolean[], boolean[])}.
	 * 
	 * @param attributes List of arrays of boolean attributes.
	 * @return OR combination of the input arrays or null if input is empty.
	 */
	public boolean[] compute(List<boolean[]> attributes) {
		if (attributes.isEmpty()) {
			return null;
		}

		boolean[] result = attributes.get(0);
		for (int i = 1; i < attributes.size(); i++) {
			result = compute(attributes.get(i), result);
		}
		return result;
	}

}