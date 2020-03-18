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

import org.dice_research.opal.licenses.utils.ArrayUtil;

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

	// TODO: OLD code, remove dependencies updated
	// TO DO: license required for shareAlike. But array required for using
	// operator.
	public List<License> getMatchingLicensesOLDEDP(License license) {
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

		// TO DO: Will not work by simply comparing values (missing OR)
		// Check attributes
		for (License licenseB : getLicenses()) {
			if (Arrays.equals(attributeValues, licenseB.getAttributes().getValuesArray())) {
				licenses.add(licenseB);
			}
		}

		return licenses;
	}

	// TODO: OLD code, remove dependencies updated
	// TO DO: license required for shareAlike. But array (internal) required for
	// using operator.
	/**
	 * Attribute values have to be checked additionally.
	 * 
	 * If an attribute of result is a restriction/prohibition, which is true, than
	 * only true values of licenses are allowed. If it is false, and therefore there
	 * is no restriction, every value is allowed.
	 * 
	 */
	public List<License> getMatchingLicensesOLDEDP(boolean[] internalAttributeValues) {
		List<License> licenses = new LinkedList<>();
		for (int i = 0; i < internalAttributeValues.length; i++) {

		}
		for (License licenseB : getLicenses()) {
			// TO DO check if OR is also needed here
			if (Arrays.equals(internalAttributeValues, licenseB.getAttributes().getInternalValuesArray())) {
				licenses.add(licenseB);
			}
		}
		return licenses;
	}

	public List<License> getMatchingLicenses(List<License> inputLicenses, boolean[] internalValues) {

		// No license to check -> no result
		if (inputLicenses.isEmpty()) {
			return new ArrayList<>(0);
		}

		// One license does not allow derivates -> no result
		for (License license : inputLicenses) {
			if (!license.derivatesAllowed) {
				return new ArrayList<>(0);
			}
		}

		// TODO: Does not work for e.g. public domain + share alike
		// One license is share-alike -> check, if all licenses are equal
//		boolean shareAlike = false;
//		for (License license : inputLicenses) {
//			if (license.shareAlike) {
//				shareAlike = true;
//				break;
//			}
//		}
//		if (shareAlike) {
//			License firstLicense = inputLicenses.get(0);
//			for (License license : inputLicenses) {
//				if (!firstLicense.equals(license)) {
//					return new ArrayList<>(0);
//				}
//			}
//		}

		// Check single attributes
		List<License> resultingLicenses = new LinkedList<>();
		List<Attribute> attributes = getAttributes().getObjects();
		licenseLoop: for (License license : getLicenses()) {
			boolean[] licenseInternalValues = license.getAttributes().getInternalValuesArray();
			for (int i = 0; i < internalValues.length; i++) {

				if (attributes.get(i).getType().equals(Permission.TYPE)) {
					if (internalValues[i] != licenseInternalValues[i]) {
						continue licenseLoop;
					}
				}

				else if (attributes.get(i).getType().equals(Prohibition.TYPE)) {
					if (internalValues[i] && !licenseInternalValues[i]) {
						continue licenseLoop;
					}
				}

				else if (attributes.get(i).getType().equals(Requirement.TYPE)) {
					if (internalValues[i] && !licenseInternalValues[i]) {
						continue licenseLoop;
					}
				}

				else {
					throw new RuntimeException("Unknown type");
				}
			}

			resultingLicenses.add(license);
		}
		return resultingLicenses;
	}

	public String toLines() {
		StringBuilder stringBuilder = new StringBuilder();
		for (Attribute attribute : getAttributes().getObjects()) {
			stringBuilder.append(attribute);
			stringBuilder.append(System.lineSeparator());
		}
		for (License license : getLicenses()) {
			stringBuilder.append(ArrayUtil.intString(license.getAttributes().getValuesArray()));
			stringBuilder.append(" ");
			stringBuilder.append(license.getUri());
			stringBuilder.append(System.lineSeparator());
		}
		return stringBuilder.toString();
	}
}