package org.dice_research.opal.licenses;

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

	public static final String TYPE = Permission.class.getSimpleName();

	@Override
	public String getShortForm() {
		if (isMetaAttribute()) {
			return "M";
		} else {
			return "m";
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
		return true;
	}

}