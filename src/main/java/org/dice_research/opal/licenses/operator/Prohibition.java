package org.dice_research.opal.licenses.operator;

import java.text.ParseException;

/**
 * Attribute of type prohibition.
 * 
 * @see https://www.w3.org/TR/odrl-model/
 * @see https://www.w3.org/TR/odrl-vocab/
 * @see https://w3c.github.io/odrl/bp/
 * @see https://w3c.github.io/odrl/bp/ §3 How to represent a prohibition
 *
 * @author Adrian Wilke
 */
public class Prohibition extends Attribute {

	public static final String TYPE = Prohibition.class.getSimpleName();

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
	public Prohibition parseBoolean(boolean bool) {
		setValue(bool);
		return this;
	}

	/**
	 * Parses binary value and returns instance.
	 * 
	 * @throws ParseException if not 0 or 1
	 */
	@Override
	public Prohibition parseBinary(int binary) throws ParseException {
		setValue(binaryToBoolean(binary));
		return this;
	}

	/**
	 * Gets type of attribute.
	 */
	@Override
	public String getType() {
		return TYPE;
	}
}