package org.example.licenses;

/**
 * Attribute of type requirement.
 * 
 * @see https://www.w3.org/TR/odrl-model/
 * @see https://www.w3.org/TR/odrl-vocab/
 * @see https://w3c.github.io/odrl/bp/
 * @see https://w3c.github.io/odrl/bp/ §2 How to represent an obligation
 *
 * @author 33a1cc8d616a72f953d8e15274194bcd5aac2b78fbe6b4a4d1a911e0f2ef00cd
 */
public class Requirement extends Attribute {

	public static final String TYPE = Requirement.class.getSimpleName();

	@Override
	public String getShortForm() {
		if (isMetaAttribute()) {
			return "R";
		} else {
			return "r";
		}
	}

	/**
	 * Gets type of attribute.
	 */
	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public boolean invertForComputation() {
		return false;
	}

}