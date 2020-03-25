package org.dice_research.opal.licenses;

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

	@Override
	public String getShortForm() {
		if (isMetaAttribute()) {
			return "H";
		} else {
			return "h";
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