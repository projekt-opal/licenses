package org.dice_research.opal.licenses.operator;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * A knowledge base comprises known attributes and a list of known licenses.
 *
 * @author Adrian Wilke
 */
public class KnowledgeBase {

	private Attributes attributes = new Attributes();
	private LinkedHashMap<String, License> urisToLicenses = new LinkedHashMap<String, License>();

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

	public LinkedHashMap<String, License> getUrisToLicenses() {
		return urisToLicenses;
	}

	// TODO: license required for shareAlike. But array required for using operator.
	public List<License> getMatchingLicenses(License license) {
		boolean[] attributeValues = license.getAttributes().getValuesArray();
		List<License> licenses = new LinkedList<>();

		// CC-BY-ND 4.0, CC-BY-NC-ND 4.0, PSEUL
		if (!license.derivatesAllowed) {
			return licenses;
		}

		for (License licenseB : getLicenses()) {
			if (Arrays.equals(attributeValues, licenseB.getAttributes().getValuesArray())) {
				licenses.add(licenseB);
			}
		}

		if (license.shareAlike) {
			if (licenses.contains(license)) {
				licenses = new LinkedList<>();
				licenses.add(license);
				return licenses;
			}
		}

		return licenses;
	}

	// TODO: license required for shareAlike. But array (internal) required for
	// using operator.
	public List<License> getMatchingLicenses(boolean[] internalAttributeValues) {
		List<License> licenses = new LinkedList<>();
		for (License licenseB : getLicenses()) {
			if (Arrays.equals(internalAttributeValues, licenseB.getAttributes().getInternalArray())) {
				licenses.add(licenseB);
			}
		}
		return licenses;
	}
}