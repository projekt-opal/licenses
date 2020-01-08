package org.dice_research.opal.licenses;

import java.util.Collection;
import java.util.List;

import org.dice_research.opal.licenses.exceptions.UnknownLicenseException;

/**
 * Components implementing this interface create suggestions for possible
 * licenses to be combined. It is based on a collection of input licenses, which
 * are related to restrictions to be met.
 *
 * @author Adrian Wilke
 */
public interface LicenceCombinatorInterface {

	/**
	 * Suggests licenses for a combination of different input licenses.
	 * 
	 * @param usedLicenceUris A collection of license-URIs, which have to be
	 *                        considered for suggesting possible licenses.
	 * 
	 * @return A list of URIs of licenses, which can be used related to restrictions
	 *         of the input licenses. The list is ordered. If it contains licenses
	 *         contained in the input, these should be listed at top. If licenses
	 *         are more probable, e.g. by their openness, they should be listed
	 *         before others. The returned list may be empty.
	 * 
	 * 
	 * @throws UnknownLicenseException If an input-URI is not known.
	 */
	List<String> getLicenceSuggestions(Collection<String> usedLicenceUris) throws UnknownLicenseException;

}