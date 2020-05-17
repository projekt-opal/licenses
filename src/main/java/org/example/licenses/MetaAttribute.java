package org.example.licenses;

/**
 * Handles special meanings of attributes.
 * 
 * Flags mean the type of attribute, not the value.
 * 
 * @author 33a1cc8d616a72f953d8e15274194bcd5aac2b78fbe6b4a4d1a911e0f2ef00cd
 */
public abstract class MetaAttribute {

	private boolean isTypePermissionOfDerivates = false;
	private boolean isTypeRequirementShareAlike = false;

	public boolean isMetaAttribute() {
		return isTypePermissionOfDerivates || isTypeRequirementShareAlike;
	}

	public boolean isTypePermissionOfDerivates() {
		return isTypePermissionOfDerivates;
	}

	public boolean isTypeRequirementShareAlike() {
		return isTypeRequirementShareAlike;
	}

	public MetaAttribute setIsTypePermissionOfDerivates(boolean isPermissionOfDerivates) {
		this.isTypePermissionOfDerivates = isPermissionOfDerivates;
		return this;
	}

	public MetaAttribute setIsTypeRequirementShareAlike(boolean isRequirementShareAlike) {
		this.isTypeRequirementShareAlike = isRequirementShareAlike;
		return this;
	}

}