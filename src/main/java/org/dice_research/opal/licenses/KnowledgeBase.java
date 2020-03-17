package org.dice_research.opal.licenses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A knowledge base comprises known attributes and a list of known licenses.
 *
 * @author Adrian Wilke
 */
public class KnowledgeBase {

	private Attributes attributes = new Attributes();
	private LinkedHashMap<String, License> urisToLicenses = new LinkedHashMap<>();
	private Map<String, List<String>> shareAlike = new HashMap<>();

	public KnowledgeBase addAttribute(Attribute attribute) {
		attributes.addAttribute(attribute);
		return this;
	}

	public void addLicense(License license) {
		urisToLicenses.put(license.getUri(), license);
	}

	public Attributes getAttributes() {
		return attributes;
	}

	public Collection<License> getLicenses() {
		return urisToLicenses.values();
	}

	public Set<String> getLicenseUris() {
		return urisToLicenses.keySet();
	}

	public LinkedHashMap<String, License> getUrisToLicenses() {
		return urisToLicenses;
	}

	/**
	 * Gets list of shared-alike license URIs for the specified license URI.
	 */
	public List<String> getShareAlikeLicenses(String licenseUri) {
		if (shareAlike.containsKey(licenseUri)) {
			return shareAlike.get(licenseUri);
		} else {
			return new ArrayList<>(0);
		}
	}

	/**
	 * Sets a list of shared-alike license URIs for the specified license URI.
	 * 
	 * @throws IllegalArgumentException if a URI is unknown.
	 */
	public KnowledgeBase putShareAlike(String licenseUri, List<String> shareAlikeLicenseUris)
			throws IllegalArgumentException {
		if (!urisToLicenses.keySet().contains(licenseUri)) {
			throw new IllegalArgumentException(licenseUri);
		}
		for (String uri : shareAlikeLicenseUris) {
			if (!urisToLicenses.keySet().contains(uri)) {
				throw new IllegalArgumentException(uri);
			}
		}
		shareAlike.put(licenseUri, shareAlikeLicenseUris);
		return this;
	}

	// TODO: license required for shareAlike. But array required for using operator.
	public List<License> getMatchingLicenses(License license) {
		boolean[] attributeValues = license.getAttributes().getValuesArray();
		List<License> licenses = new LinkedList<>();

		// No derivates allowed -> Return empty list
		// (CC-BY-ND 4.0, CC-BY-NC-ND 4.0, PSEUL)
		if (!license.derivatesAllowed) {
			return licenses;
		}

		// Share-alike: Use Predifined lists
		if (license.shareAlike) {
			List<String> shareAlikeLicenses = getShareAlikeLicenses(license.getUri());

			// No share-alike: Use only license itself
			if (shareAlikeLicenses.isEmpty()) {
				licenses.add(license);
				return licenses;
			}

			// Return share-alike licenses from knowledge base
			else {
				for (String uri : shareAlikeLicenses) {
					licenses.add(getUrisToLicenses().get(uri));
				}
				return licenses;
			}
		}

		// TODO: Will not work by simply comparing values (missing OR)
		// Check attributes
		for (License licenseB : getLicenses()) {
			if (Arrays.equals(attributeValues, licenseB.getAttributes().getValuesArray())) {
				licenses.add(licenseB);
			}
		}

		return licenses;
	}

	// TODO: license required for shareAlike. But array (internal) required for
	// using operator.
	/**
	 * Attribute values have to be checked additionally.
	 * 
	 * If an attribute of result is a restriction/prohibition, which is true, than
	 * only true values of licenses are allowed. If it is false, and therefore there
	 * is no restriction, every value is allowed.
	 * 
	 */
	public List<License> getMatchingLicenses(boolean[] internalAttributeValues) {
		List<License> licenses = new LinkedList<>();
		for (int i = 0; i < internalAttributeValues.length; i++) {

		}
		for (License licenseB : getLicenses()) {
			// TODO check if OR is also needed here
			if (Arrays.equals(internalAttributeValues, licenseB.getAttributes().getInternalValuesArray())) {
				licenses.add(licenseB);
			}
		}
		return licenses;
	}
}