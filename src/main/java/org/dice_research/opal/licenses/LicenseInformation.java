package org.dice_research.opal.licenses;

import java.util.HashMap;

public class LicenseInformation {

	public static final HashMap<String, License> licenses;
	
	public class License {
		
		/*
		 * Permissions
		 * 
		 * This are negative properties.
		 * If one license in a combination doesn't give you a permission, the resulting license can't give it to you either.
		 */
		public final boolean reproduction;
		public final boolean districution;
		public final boolean derivative;
		public final boolean sublicensing;
		public final boolean patentGrant;
		
		/*
		 * Requirements
		 * 
		 * This are positive properties.
		 * If one license in a combination requires you to do something, the resulting license must require you to do this, too.
		 */
		public final boolean notice;
		public final boolean attribution;
		public final boolean shareAlike;
		public final boolean copyleft;
		public final boolean lesserCopyLeft;
		public final boolean stateChanges;
		
		// Prohibitions
		public final boolean commercial;
		public final boolean useTrademark;
		
		public License(
				boolean reproduction,
				boolean distribution,
				boolean derivative,
				boolean sublicensing,
				boolean patentGrant,

				boolean notice,
				boolean attribution,
				boolean shareAlike,
				boolean copyLeft,
				boolean lesserCopyLeft,
				boolean stateChanges,

				boolean commercial,
				boolean useTrademark
		) {
			this.reproduction = reproduction;
			this.districution= distribution;
			this.derivative = derivative;
			this.sublicensing = sublicensing;
			this.patentGrant = patentGrant;

			this.notice = notice;
			this.attribution = attribution;
			this.shareAlike = shareAlike;
			this.copyleft = copyLeft;
			this.lesserCopyLeft = lesserCopyLeft;
			this.stateChanges = stateChanges;

			this.commercial = commercial;
			this.useTrademark = useTrademark;
		}
	}
	
	static {
		licenses = new HashMap<String, License>();
	}
}
