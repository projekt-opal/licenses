package org.example.licenses;

/**
 * Handles special meanings of attributes.
 * 
 * Flags mean the type of attribute, not the value.
 * 
 * @author Adrian Wilke
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