package org.dice_research.opal.licenses;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class BackMapping {

	/**
	 * Gets matching licenses based on internal values.
	 */
	private List<License> removeMoreRestrictive(Attributes setting, List<License> licenses,
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
					// TODO
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
	private List<License> removeLessRestrictive(Attributes setting, List<License> licenses,
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
					// TODO
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
	 * @param inputLicenses Combination of licenses for which other compatible
	 *                      licenses are requested.
	 * @param setting       Attributes, based on internal values, typically computed
	 *                      by operator
	 * @param knowledgeBase Knowledgebase with all known licenses
	 *
	 * @return List of compatible licenses
	 */
	public List<License> getCompatibleLicenses(List<License> inputLicenses, Attributes setting,
			KnowledgeBase knowledgeBase) {
		boolean[] internalValues = setting.getInternalValuesArray();

		// No license to check -> no result
		if (inputLicenses.isEmpty()) {
			return new ArrayList<>(0);
		}

		// Check, if there is a derivates-allowed attribute
		for (Attribute attribute : knowledgeBase.getAttributes().getList()) {
			if (attribute.isPermissionOfDerivates()) {
				// If there is one license not allowing derivates -> no result
				for (License license : inputLicenses) {
					if (!license.getAttributes().getAttribute(attribute.getUri()).getValue()) {
						return new ArrayList<>(0);
					}
				}
			}
		}

		// Filter by attributes
		List<License> resultingLicenses = removeMoreRestrictive(setting, inputLicenses, false);

		// Share-alike
		for (License license : resultingLicenses) {
			List<License> compatible = removeMoreRestrictive(license.getAttributes(), resultingLicenses, true);
			// TODO
			if (license.getUri().equals("http://creativecommons.org/licenses/by-nc/4.0/")) {
				System.out.println(compatible);
			}
			resultingLicenses.retainAll(compatible);
		}
		// Does not work
		if (Boolean.FALSE)
			for (License inputLicense : inputLicenses) {
				for (License license : knowledgeBase.getLicenses()) {
					if (inputLicense.isRequirementShareAlike() && license.isRequirementShareAlike()) {
						List<License> licenseList = new LinkedList<>();
						licenseList.add(license);
//						System.err.println(removeLessRestrictive(inputLicense.getAttributes(), licenseList, false));
//						resultingLicenses
//								.retainAll(removeLessRestrictive(inputLicense.getAttributes(), licenseList, false));
						if (removeLessRestrictive(inputLicense.getAttributes(), licenseList, false).isEmpty()) {
resultingLicenses.remove(license);
						}
					}
				}
			}

		// TODO
		List<String> tmp = inputLicenses.stream().map(l -> l.getUri()).collect(Collectors.toList());
		if (tmp.contains("http://creativecommons.org/licenses/by-nc/4.0/")) {
			System.err.println(resultingLicenses);
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