package org.example.licenses;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.example.utils.ArrayUtil;

/**
 * A knowledge base comprises known attributes and a list of known licenses.
 *
 * @author 33a1cc8d616a72f953d8e15274194bcd5aac2b78fbe6b4a4d1a911e0f2ef00cd
 */
public class KnowledgeBase {

	private Attributes attributes = new Attributes();
	private LinkedHashMap<String, License> urisToLicenses = new LinkedHashMap<>();
	private boolean attributesSorted = false;

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