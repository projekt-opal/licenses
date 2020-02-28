package org.dice_research.opal.licenses.operator;

import java.util.LinkedList;
import java.util.List;

/**
 * A knowledge base comprises known attributes and a list of known licenses.
 *
 * @author Adrian Wilke
 */
public class KnowledgeBase {

	private Attributes attributes = new Attributes();
	private List<License> licenses = new LinkedList<>();

	public KnowledgeBase addAttribute(Attribute attribute) {
		attributes.addAttribute(attribute);
		return this;
	}

	public void addLicense(License license) {
		licenses.add(license);
	}

	public Attributes getAttributes() {
		return attributes;
	}

	public List<License> getLicenses() {
		return licenses;
	}

}