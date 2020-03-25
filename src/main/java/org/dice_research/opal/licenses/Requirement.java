package org.dice_research.opal.licenses;

/**
 * Attribute of type requirement.
 * 
 * @see https://www.w3.org/TR/odrl-model/
 * @see https://www.w3.org/TR/odrl-vocab/
 * @see https://w3c.github.io/odrl/bp/
 * @see https://w3c.github.io/odrl/bp/ §2 How to represent an obligation
 *
 * @author Adrian Wilke
 */
public class Requirement extends Attribute {

	public static final String TYPE = Requirement.class.getSimpleName();

	@Override
	public String getShortForm() {
		if (isMetaAttribute()) {
			return "Q";
		} else {
			return "q";
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