package org.dice_research.opal.licenses;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BackMapping {

	/**
	 * Gets matching licenses based on internal values.
	 */
	private List<License> getMatching(Attributes setting, List<License> licenses, boolean includeMetaAttributes) {
		List<License> results = new LinkedList<>();
		List<Attribute> attributes = setting.getList();
		boolean[] internalValues = setting.getInternalValuesArray();
		licenseLoop: for (License license : licenses) {
			boolean[] licenseInternalValues = license.getAttributes().getInternalValuesArray();
			valueLoop: for (int i = 0; i < internalValues.length; i++) {

				if (!includeMetaAttributes && attributes.get(i).isMetaAttribute()) {
					continue valueLoop;
				}

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

		// Check, if there is a share-alike attribute
		// TODO
//		String shareAlikeAttribute = null;
//		for (Attribute attribute : knowledgeBase.getAttributes().getList()) {
//			if (attribute.isRequirementShareAlike()) {
//				shareAlikeAttribute = attribute.getUri();
//				break;
//			}
//		}
//		// TODO
//		ArrayList<License> listCopy = new ArrayList<>(inputLicenses);
//		if (shareAlikeAttribute != null) {
//			for (License inputLicense : inputLicenses) {
//				if (inputLicense.getAttributes().getAttribute(shareAlikeAttribute).getValue()) {
//					List<License> compatible = shareAlikeCompatibility(inputLicense, inputLicenses);
//					listCopy.retainAll(compatible);
//				}
//			}
//		}

		// TODO do not include meta attributes
		List<License> resultingLicenses = getMatching(setting, knowledgeBase.getLicenses(), true);

		return resultingLicenses;
	}

	public List<License> getMatchingLicensesOLDEDP(License license) {
		throw new RuntimeException("code removed");
	}

	public List<License> getMatchingLicensesOLDEDP(boolean[] result) {
		throw new RuntimeException("code removed");
	}

}