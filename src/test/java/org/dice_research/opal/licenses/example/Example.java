package org.dice_research.opal.licenses.example;

import java.util.Collection;
import java.util.LinkedList;

import org.dice_research.opal.licenses.LicenseCombinator;
import org.dice_research.opal.licenses.LicenseCombinatorInterface;
import org.dice_research.opal.licenses.exceptions.UnknownLicenseException;


public class Example {
	
	public void combineLicenses() throws UnknownLicenseException {
		// Create LicenseCombinator
		LicenseCombinatorInterface lc = new LicenseCombinator();
		
		// Put some license-URIs in a collection
		Collection<String> licenses = new LinkedList<>();
		licenses.add("https://creativecommons.org/publicdomain/zero/1.0/legalcode");
		licenses.add("http://creativecommons.org/licenses/by-nd/4.0/legalcode");
		
		// Yields a list containing only "https://www.govdata.de/dl-de/by-2-0"
		lc.getLicenseSuggestions(licenses);
	}
}
