package org.example.licenses;

/**
 * Attribute of type permission.
 * 
 * @see https://www.w3.org/TR/odrl-model/
 * @see https://www.w3.org/TR/odrl-vocab/
 * @see https://w3c.github.io/odrl/bp/
 * @see https://w3c.github.io/odrl/bp/ §1 How to Represent a General Permission
 *
 * @author 33a1cc8d616a72f953d8e15274194bcd5aac2b78fbe6b4a4d1a911e0f2ef00cd
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