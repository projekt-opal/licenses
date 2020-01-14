package org.dice_research.opal.licenses;


/**
 * 
 * @author Jochen Vothknecht
 */
public interface LicenseAttribute {

	public enum Permission implements LicenseAttribute {
		REPRODUCTION {
			public String toString() {
				return Strings.NS_ODRL + "reproduce";
			}
		},
		DISTRIBUTION {
			public String toString() {
				return Strings.NS_ODRL + "distribute";
			}
		},
		DERIVATIVE {
			public String toString() {
				return Strings.NS_ODRL + "derive";
			}
		},
		SUBLICENSING,
		PATENTGRANT
	}
	
	public enum Requirement implements LicenseAttribute {
		NOTICE {
			public String toString() {
				return Strings.NS_CC + "Notice";
			}
		},
		ATTRIBUTION {
			public String toString() {
				return Strings.NS_CC + "Attribution";
			}
		},
		SHAREALIKE {
			public String toString() {
				return Strings.NS_CC + "ShareAlike";
			}
		},
		COPYLEFT {
			public String toString() {
				return Strings.NS_CC + "CopyLeft";
			}
		},
		LESSERCOPYLEFT {
			public String toString() {
				return Strings.NS_CC + "LesserCopyleft";
			}
		},
		STATECHANGES
	}
	
	public enum Prohibition implements LicenseAttribute {
		COMMERCIAL {
			public String toString() {
				return Strings.NS_CC + "CommercialUse";
			}
		},
		USETRADEMARK
	}
}
