package org.dice_research.opal.licenses;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class BackMapping {

	/**
	 * Gets matching licenses based on internal values.
	 */
	protected List<License> removeLessRestrictive(Attributes setting, List<License> licenses,
			boolean includeMetaAttributes) {
		List<License> results = new LinkedList<>();
		List<Attribute> settingAttributes = setting.getList();
		boolean[] settingValues = setting.getValuesArray();
		licenseLoop: for (License license : licenses) {
			boolean[] licenseValues = license.getAttributes().getValuesArray();
			valueLoop: for (int i = 0; i < settingValues.length; i++) {

				if (!includeMetaAttributes && settingAttributes.get(i).isMetaAttribute()) {
					continue valueLoop;
				}

				if (settingAttributes.get(i).getType().equals(Permission.TYPE)) {
					// TODO Map permissions
				}

				else if (settingAttributes.get(i).getType().equals(Prohibition.TYPE)) {
					// Not compatible: Setting restricted and license open
					if (settingValues[i] && !licenseValues[i]) {
						continue licenseLoop;
					}
				}

				else if (settingAttributes.get(i).getType().equals(Requirement.TYPE)) {
					// Not compatible: Setting restricted and license open
					if (settingValues[i] && !licenseValues[i]) {
						continue licenseLoop;
					}
				}

				else {
					throw new RuntimeException("Unknown type");
				}
			}

			results.add(license);
		}
		return results;
	}

	/**
	 * Gets matching licenses based on internal values.
	 */
	protected List<License> removeMoreRestrictive(Attributes setting, List<License> licenses,
			boolean includeMetaAttributes) {
		List<License> results = new LinkedList<>();
		List<Attribute> settingAttributes = setting.getList();
		boolean[] settingValues = setting.getValuesArray();
		licenseLoop: for (License license : licenses) {
			boolean[] licenseValues = license.getAttributes().getValuesArray();
			valueLoop: for (int i = 0; i < settingValues.length; i++) {

				if (!includeMetaAttributes && settingAttributes.get(i).isMetaAttribute()) {
					continue valueLoop;
				}

				if (settingAttributes.get(i).getType().equals(Permission.TYPE)) {
					// TODO Map permissions
				}

				else if (settingAttributes.get(i).getType().equals(Prohibition.TYPE)) {
					// Not compatible: Setting restricted and license open
					if (!settingValues[i] && licenseValues[i]) {
						continue licenseLoop;
					}
				}

				else if (settingAttributes.get(i).getType().equals(Requirement.TYPE)) {
					// Not compatible: Setting restricted and license open
					if (!settingValues[i] && licenseValues[i]) {
						continue licenseLoop;
					}
				}

				else {
					throw new RuntimeException("Unknown type");
				}
			}

			results.add(license);
		}
		return results;
	}

	/**
	 * Gets compatible licenses.
	 * 
	 * Creates set of call of
	 * {@link #getCompatibleLicensesList(List, Attributes, KnowledgeBase)}.
	 */
	public Set<License> getCompatibleLicenses(List<License> inputLicenses, Attributes setting,
			KnowledgeBase knowledgeBase) {
		return new HashSet<License>(getCompatibleLicensesList(inputLicenses, setting, knowledgeBase));
	}

	/**
	 * Gets compatible licenses.
	 * 
	 * @param inputLicenses Combination of licenses for which other compatible
	 *                      licenses are requested
	 * @param setting       Attributes, based on internal values, typically computed
	 *                      by operator
	 * @param knowledgeBase Knowledgebase with all known licenses
	 *
	 * @return List of compatible licenses
	 */
	public List<License> getCompatibleLicensesList(List<License> inputLicenses, Attributes setting,
			KnowledgeBase knowledgeBase) {

		// No license to check -> no result
		if (inputLicenses.isEmpty()) {
			return new ArrayList<>(0);
		}

		// Check, if there is a derivates-allowed attribute
		for (Attribute attribute : knowledgeBase.getAttributes().getList()) {
			if (attribute.isTypePermissionOfDerivates()) {
				// If there is one license not allowing derivates -> no result
				for (License license : inputLicenses) {
					if (!license.getAttributes().getAttribute(attribute.getUri()).getValue()) {
						return new ArrayList<>(0);
					}
				}
			}
		}

		// Filter by attributes
		List<License> resultingLicenses = removeLessRestrictive(setting, inputLicenses, false);

		// Check share-alike restrictions
		for (License inputLicense : inputLicenses) {
			for (License license : knowledgeBase.getLicenses()) {
				if (inputLicense.isShareAlike()) {
					List<License> licenseList = new LinkedList<>();
					licenseList.add(license);
					if (removeMoreRestrictive(inputLicense.getAttributes(), licenseList, false).isEmpty()) {
						resultingLicenses.remove(license);
					}
				}
			}
		}

		return resultingLicenses;
	}

	public List<License> getMatchingLicensesOLDEDP(License license) {
		throw new RuntimeException("code removed");
	}

	public List<License> getMatchingLicensesOLDEDP(boolean[] result) {
		throw new RuntimeException("code removed");
	}

}