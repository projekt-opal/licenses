package org.dice_research.opal.licenses;


/**
 * 
 * @author Jochen Vothknecht
 */
public interface LicenseAttribute {

	public enum Permission implements LicenseAttribute {
		REPRODUCTION,
		DISTRIBUTION,
		DERIVATIVE,
		SUBLICENSING,
		PATENTGRANT
	}
	
	public enum Requirement implements LicenseAttribute {
		NOTICE,
		ATTRIBUTION,
		SHAREALIKE,
		COPYLEFT,
		LESSERCOPYLEFT,
		STATECHANGES
	}
	
	public enum Prohibition implements LicenseAttribute {
		COMMERCIAL,
		USETRADEMARK
	}
}
