package org.dice_research.opal.licenses.operator;

import java.text.ParseException;

/**
 * Attribute of type permission.
 * 
 * @see https://www.w3.org/TR/odrl-model/
 * @see https://www.w3.org/TR/odrl-vocab/
 * @see https://w3c.github.io/odrl/bp/
 * @see https://w3c.github.io/odrl/bp/ §1 How to Represent a General Permission
 *
 * @author Adrian Wilke
 */
public class Permission extends Attribute {

	/**
	 * Maps attribute value to boolean representation.
	 * 
	 * @throws NullPointerException if not set
	 */
	@Override
	public boolean mapToBoolean() throws NullPointerException {
		if (getValue() == null) {
			throw new NullPointerException();
		} else {
			return !getValue();
		}
	}

	/**
	 * Maps attribute value to binary representation.
	 * 
	 * @throws NullPointerException if not set
	 */
	@Override
	public int mapToBinary() throws NullPointerException {
		if (getValue() == null) {
			throw new NullPointerException();
		} else {
			return booleanToBinary(!getValue());
		}
	}

	/**
	 * Parses boolean value and returns instance.
	 */
	@Override
	public Permission parseBoolean(boolean bool) {
		setValue(!bool);
		return this;
	}

	/**
	 * Parses binary value and returns instance.
	 * 
	 * @throws ParseException if not 0 or 1
	 */
	@Override
	public Permission parseBinary(int binary) throws ParseException {
		setValue(!binaryToBoolean(binary));
		return this;
	}

}