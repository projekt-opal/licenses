package org.dice_research.opal.licenses;


/**
 * 
 * @author Jochen Vothknecht
 */
public interface LicenseAttribute {

	/**
	 * Used in searching for compatible licenses.
	 * 
	 * If License A has Permission P, than License B is compatible to A if it either permits P or not.
	 * If A doesn't permit P, than B can't permit P either.
	 */
	public enum Permission implements LicenseAttribute {
		REPRODUCTION {
			public String toString() {
				return Constants.NS_ODRL + "reproduce";
			}
		},
		DISTRIBUTION {
			public String toString() {
				return Constants.NS_ODRL + "distribute";
			}
		},
		DERIVATIVE {
			public String toString() {
				return Constants.NS_ODRL + "derive";
			}
		},
		SUBLICENSING,
		PATENTGRANT
	}
	
	/**
	 * Used in searching for compatible licenses.
	 * 
	 * If License A has Requirement R, than License B is compatible to A if it requires R too.
	 * If A doesn't require R, than B may or may not require R.
	 */
	public enum Requirement implements LicenseAttribute {
		NOTICE {
			public String toString() {
				return Constants.NS_CC + "Notice";
			}
		},
		ATTRIBUTION {
			public String toString() {
				return Constants.NS_CC + "Attribution";
			}
		},
		SHAREALIKE {
			public String toString() {
				return Constants.NS_CC + "ShareAlike";
			}
		},
		COPYLEFT {
			public String toString() {
				return Constants.NS_CC + "CopyLeft";
			}
		},
		LESSERCOPYLEFT {
			public String toString() {
				return Constants.NS_CC + "LesserCopyleft";
			}
		},
		STATECHANGES
	}
	
	/**
	 * Used in searching for compatible licenses.
	 * 
	 * If License A has Prohibition P, than License B is compatible to A if it prohibits P too.
	 * If A doesn't prohibit P, than B may or may not prohibit P.
	 */
	public enum Prohibition implements LicenseAttribute {
		COMMERCIAL {
			public String toString() {
				return Constants.NS_CC + "CommercialUse";
			}
		},
		USETRADEMARK
	}
}
