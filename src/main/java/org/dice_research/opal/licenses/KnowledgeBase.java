package org.dice_research.opal.licenses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dice_research.opal.licenses.utils.ArrayUtil;

/**
 * A knowledge base comprises known attributes and a list of known licenses.
 *
 * @author Adrian Wilke
 */
public class KnowledgeBase {

	private Attributes attributes = new Attributes();
	private LinkedHashMap<String, License> urisToLicenses = new LinkedHashMap<>();
	private boolean attributesSorted = false;

	// TODO: Check EDP, if this can be removed
	private Map<String, List<String>> shareAlike = new HashMap<>();

	public KnowledgeBase addAttribute(Attribute attribute) {
		attributes.addAttribute(attribute);
		attributesSorted = false;
		return this;
	}

	public void addLicense(License license) {
		urisToLicenses.put(license.getUri(), license);
	}

	/**
	 * Sorts the attributes in knowledge base if not sorted. Returns them.
	 */
	public Attributes getAttributes() {
		if (!attributesSorted) {
			attributes.sort();
			attributesSorted = true;
		}
		return attributes;
	}

	public License getLicense(String uri) {
		return urisToLicenses.get(uri);
	}

	public List<License> getLicenses() {
		return new ArrayList<License>(urisToLicenses.values());
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

	public String toLines() {
		StringBuilder stringBuilder = new StringBuilder();
		for (Attribute attribute : getAttributes().getList()) {
			stringBuilder.append(attribute);
			stringBuilder.append(System.lineSeparator());
		}
		for (License license : getLicenses()) {
			stringBuilder.append(ArrayUtil.intString(license.getAttributes().getValuesArray()));
			stringBuilder.append(" ");
			stringBuilder.append(ArrayUtil.intString(license.getAttributes().getInternalValuesArray()));
			stringBuilder.append(" ");
			stringBuilder.append(license.getUri());
			stringBuilder.append(System.lineSeparator());
		}
		return stringBuilder.toString();
	}
}