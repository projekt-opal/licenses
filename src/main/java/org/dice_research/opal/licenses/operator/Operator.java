package org.dice_research.opal.licenses.operator;

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
	 * @throws RuntimeException if the lengths of input arrays are different or if
	 *                          the arrays are empty.
	 */
	public boolean[] compute(boolean[] attributesA, boolean[] attributesB) {
		if (attributesA.length != attributesB.length) {
			throw new RuntimeException("Different length of arrays.");
		} else if (attributesA.length == 0) {
			throw new RuntimeException("No values given.");
		}

		boolean[] attributesC = new boolean[attributesA.length];
		for (int i = 0; i < attributesC.length; i++) {
			attributesC[i] = attributesA[i] | attributesB[i];
		}
		return attributesC;
	}

}