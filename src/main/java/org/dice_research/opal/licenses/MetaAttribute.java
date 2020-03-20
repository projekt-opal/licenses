package org.dice_research.opal.licenses;

/**
 * Handles special meanings of attributes.
 * 
 * Flags mean the type of attribute, not the value.
 * 
 * @author Adrian Wilke
 */
public abstract class MetaAttribute {

	private boolean isPermissionOfDerivates = false;
	private boolean isRequirementShareAlike = false;

	public boolean isMetaAttribute() {
		return isPermissionOfDerivates || isRequirementShareAlike;
	}

	public boolean isPermissionOfDerivates() {
		return isPermissionOfDerivates;
	}

	public boolean isRequirementShareAlike() {
		return isRequirementShareAlike;
	}

	public MetaAttribute setIsPermissionOfDerivates(boolean isPermissionOfDerivates) {
		this.isPermissionOfDerivates = isPermissionOfDerivates;
		return this;
	}

	public MetaAttribute setIsRequirementShareAlike(boolean isRequirementShareAlike) {
		this.isRequirementShareAlike = isRequirementShareAlike;
		return this;
	}

}