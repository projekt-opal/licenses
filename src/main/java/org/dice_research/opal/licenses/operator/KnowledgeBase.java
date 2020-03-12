package org.dice_research.opal.licenses.operator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A knowledge base comprises known attributes and a list of known licenses.
 *
 * @author Adrian Wilke
 */
public class KnowledgeBase {

	private Attributes attributes = new Attributes();
	private Map<String, License> licenses = new HashMap<String, License>();

	public KnowledgeBase addAttribute(Attribute attribute) {
		attributes.addAttribute(attribute);
		return this;
	}

	public void addLicense(License license) {
		licenses.put(license.getUri(), license);
	}

	public Attributes getAttributes() {
		return attributes;
	}

	public Map<String, License> getLicenses() {
		return licenses;
	}

	public List<License> getMatchingLicenses(boolean[] attributeValues) {
		List<License> licenses = new LinkedList<>();
		for (License license : getLicenses().values()) {
			if (Arrays.equals(attributeValues, license.getAttributes().getArray())) {
				licenses.add(license);
			}
		}
		return licenses;
	}
}