package org.dice_research.opal.licenses.cc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dice_research.opal.licenses.Attribute;
import org.dice_research.opal.licenses.KnowledgeBase;
import org.dice_research.opal.licenses.License;
import org.dice_research.opal.licenses.Permission;
import org.dice_research.opal.licenses.Prohibition;
import org.dice_research.opal.licenses.Requirement;

public class CcExperiment {

//	public static void main(String[] args) {
//		CcData ccData = new CcData();
//		KnowledgeBase kb = ccData.setSourceDirectory("../cc.licenserdf/cc/licenserdf/licenses/")
//				.createKnowledgeBase(ccData.getMatixFiles());
//		new CcExperiment().execute(kb);
//	}

	public static final String SHARE_ALIKE = "http://creativecommons.org/ns#ShareAlike";
	public static final String DERIVATIVE_WORKS = "http://creativecommons.org/ns#DerivativeWorks";

//	private void execute(KnowledgeBase kb) {
////		for (License licenseA : kb.getLicenses()) {
////			for (License licenseB : kb.getLicenses()) {
////				boolean[] result = new Operator().compute(licenseA.getAttributes().getInternalValuesArray(),
////						licenseB.getAttributes().getInternalValuesArray());
////				List<License> resultingLicenses = getMatchingLicenses(kb, result);
////			}
////		}
//		Map<License, List<License>> map = new HashMap<>();
////		for (License license : kb.getLicenses()) {
////			map.put(license, getMatchingLicenses(kb, license.getAttributes().getInternalValuesArray()));
////		}
//
////		License byNc = kb.getUrisToLicenses().get("http://creativecommons.org/licenses/by-nc/4.0/");
////		map.put(byNc, getMatchingLicenses(kb, byNc.getAttributes().getInternalValuesArray()));
//		License bySa = kb.getUrisToLicenses().get("http://creativecommons.org/licenses/by-sa/4.0/");
//		map.put(bySa, getMatchingLicenses(kb, bySa.getAttributes().getInternalValuesArray()));
//
//		System.err.println(" " + Arrays.toString(bySa.getAttributes().getInternalValuesArray()));
//		System.err.println(" " + Arrays.toString(bySa.getAttributes().getValuesArray()));
//
//		for (Entry<License, List<License>> entry : map.entrySet()) {
//			System.out.println(entry.getKey());
//			for (License resultLicense : entry.getValue()) {
//				System.out.println(resultLicense);
//				System.out.println(Arrays.toString(resultLicense.getAttributes().getValuesArray()));
//			}
//			System.out.println();
//		}
//	}

	private void tmpcheck(License license, int i, boolean a, boolean b, String... string) {
		if (string.length == 0)
			string = new String[1];
		string[0] = "";
//		if(license.getUri().equals(CcMatrix.I2_BY)) {
		System.err.println(i + " " + license + " " + a + " " + b + " " + string[0]);
//		}

	}

	public List<License> getMatchingLicenses(KnowledgeBase kb, boolean[] resultInternalValues) {

//		System.err.println(Arrays.toString(resultInternalValues));

		List<Attribute> attributesList = new ArrayList<Attribute>(kb.getAttributes().getObjects());

		List<License> licenses = new LinkedList<>();
		licenseLoop: for (License license : kb.getLicenses()) {
			boolean[] licenseInternalValues = license.getAttributes().getInternalValuesArray();
			for (int i = 0; i < resultInternalValues.length; i++) {
				if (attributesList.get(i).getType().equals(Permission.TYPE)) {
					if (resultInternalValues[i] != licenseInternalValues[i]) {
						tmpcheck(license, i, resultInternalValues[i], licenseInternalValues[i]);
						continue licenseLoop;
					}
				} else if (attributesList.get(i).getType().equals(Prohibition.TYPE)) {
					if (resultInternalValues[i] && !licenseInternalValues[i]) {
						tmpcheck(license, i, resultInternalValues[i], licenseInternalValues[i], "pro");
						continue licenseLoop;
					}
				} else if (attributesList.get(i).getType().equals(Requirement.TYPE)) {
					if (resultInternalValues[i] && !licenseInternalValues[i]) {
						tmpcheck(license, i, resultInternalValues[i], licenseInternalValues[i]);
						continue licenseLoop;
					}
				} else {
					throw new RuntimeException("Unknown type");
				}
//				if (resultInternalValues[i] && !licenseInternalValues[i]) {
//					System.err.println(i + " " + license + " " + Arrays.toString(licenseInternalValues));
//					continue licenseLoop;
//				}
			}

//			if (license.getAttributes().getUris().contains(SHARE_ALIKE)
//					&& license.getAttributes().getUriToAttributeMap().get(SHARE_ALIKE).getValue()) {
//				license.shareAlike = true;
//			}

			// http://creativecommons.org/ns#DerivativeWorks (Permission)
			if (!license.getAttributes().getUriToAttributeMap().get(DERIVATIVE_WORKS).getValue()) {
				// TODO: maybe elsewhere
				license.derivatesAllowed = false;
				return new ArrayList<>(0);
			}

			licenses.add(license);
		}
		return licenses;
	}

}