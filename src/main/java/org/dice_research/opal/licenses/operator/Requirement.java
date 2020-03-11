package org.dice_research.opal.licenses.operator;

import java.text.ParseException;

/**
 * Attribute of type requirement.
 * 
 * @see https://www.w3.org/TR/odrl-model/
 * @see https://www.w3.org/TR/odrl-vocab/
 * @see https://w3c.github.io/odrl/bp/
 *
 * @author Adrian Wilke
 */
public class Requirement extends Attribute {

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
			return getValue();
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
			return booleanToBinary(getValue());
		}
	}

	/**
	 * Parses boolean value and returns instance.
	 */
	@Override
	public Requirement parseBoolean(boolean bool) {
		setValue(bool);
		return this;
	}

	/**
	 * Parses binary value and returns instance.
	 * 
	 * @throws ParseException if not 0 or 1
	 */
	@Override
	public Requirement parseBinary(int binary) throws ParseException {
		setValue(binaryToBoolean(binary));
		return this;
	}
	
}