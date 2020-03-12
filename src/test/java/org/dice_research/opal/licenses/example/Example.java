package org.dice_research.opal.licenses.example;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.dice_research.opal.licenses.old.LicenseAttribute;
import org.dice_research.opal.licenses.old.LicenseCombinator;
import org.dice_research.opal.licenses.old.LicenseCombinatorInterface;
import org.dice_research.opal.licenses.old.exceptions.UnknownLicenseException;


public class Example {
	
		LicenseCombinatorInterface lc = new LicenseCombinator();
		
	public void combineLicenses() throws UnknownLicenseException {
		// Put some license-URIs in a collection
		Collection<String> licenses = new LinkedList<>();
		licenses.add("https://creativecommons.org/publicdomain/zero/1.0/legalcode");
		licenses.add("http://creativecommons.org/licenses/by-nd/4.0/legalcode");
		
		// Yields a list containing only "https://www.govdata.de/dl-de/by-2-0"
		lc.getLicenseSuggestions(licenses);
	}
	
	/**
	 * Search for licenses with specific attributes
	 */
	public void licenseFromAttributes() {
		
		// create attribute map
		Map<LicenseAttribute, Boolean> attributes = new HashMap<>();
		
		// add some attributes
		attributes.put(LicenseAttribute.Permission.DERIVATIVE, true);
		attributes.put(LicenseAttribute.Permission.SUBLICENSING, true);
		
		// Yields 12 different licenses which all allow derivative works and sublicensing
		lc.getLicenseFromAttributes(attributes);
	}
}
