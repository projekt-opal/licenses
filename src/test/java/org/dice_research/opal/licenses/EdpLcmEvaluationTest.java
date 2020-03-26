package org.dice_research.opal.licenses;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.dice_research.opal.licenses.edplcm.EdpLcmKnowledgeBase;
import org.dice_research.opal.licenses.edplcm.EpdLcmDerivates;
import org.dice_research.opal.licenses.utils.ArrayUtil;
import org.dice_research.opal.licenses.utils.F1Score;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests {@link EpdLcmDerivates} and {@link EdpLcmKnowledgeBase}.
 * 
 * @author Adrian Wilke
 */
public class EdpLcmEvaluationTest {

	private EpdLcmDerivates derivates;
	private EdpLcmKnowledgeBase knowledgeBase;

	@Before
	public void setUp() throws Exception {
		derivates = new EpdLcmDerivates();
		knowledgeBase = new EdpLcmKnowledgeBase().load();
	}

	// TODO old code
//	Set<String> compatibleUris = derivates.getCompatibleUris(licenseA.getUri()).stream().distinct()
//			.filter(derivates.getCompatibleUris(licenseB.getUri())::contains).collect(Collectors.toSet());

	@Test
	public void test() throws IOException {
		boolean status = true;
		StringBuilder stringBuilder = new StringBuilder();
		F1Score f1Score = new F1Score();

		// Combine licenses to check every cell in matrix
		for (License licenseA : knowledgeBase.getLicenses()) {

			for (License licenseB : knowledgeBase.getLicenses()) {

				List<License> inputLicenses = new ArrayList<>(2);
				inputLicenses.add(licenseA);
				inputLicenses.add(licenseB);

				// Operator used to compute array of internal values
				Execution execution = new Execution().setKnowledgeBase(knowledgeBase);
				Attributes resultAttributes = execution.applyOperator(inputLicenses);

				// Back-mapping
				List<License> resultingLicenses = new BackMapping().getCompatibleLicensesList(inputLicenses,
						resultAttributes, knowledgeBase);

				// Check license combination and update result status
				boolean result = checkResults(licenseA, licenseB, resultingLicenses, derivates, stringBuilder, f1Score);
				status = status & result;
			}
		}

		// Print debugging info, if test failed
		if (!status) {
			stringBuilder.append("Tests:     ");
			stringBuilder.append(f1Score.getAll());
			stringBuilder.append("  ");
			stringBuilder.append(System.lineSeparator());

			stringBuilder.append("Success:   ");
			stringBuilder.append(f1Score.getTrue());
			stringBuilder.append(" (");
			stringBuilder.append(f1Score.getPercentageTrue());
			stringBuilder.append(")");
			stringBuilder.append(System.lineSeparator());
			stringBuilder.append("Errors:    ");
			stringBuilder.append(f1Score.getFalse());
			stringBuilder.append(" (");
			stringBuilder.append(f1Score.getPercentageFalse());
			stringBuilder.append(")");
			stringBuilder.append(System.lineSeparator());

			stringBuilder.append("F1 score:  ");
			stringBuilder.append(f1Score.getF1score());
			stringBuilder.append(System.lineSeparator());
			stringBuilder.append("Precision: ");
			stringBuilder.append(f1Score.getPrecision());
			stringBuilder.append(System.lineSeparator());
			stringBuilder.append("Recall:    ");
			stringBuilder.append(f1Score.getRecall());
			stringBuilder.append(System.lineSeparator());

			stringBuilder.append("Cases:     ");
			stringBuilder.append("TP: ");
			stringBuilder.append(f1Score.getTruePositive());
			stringBuilder.append(", TN: ");
			stringBuilder.append(f1Score.getTrueNegative());
			stringBuilder.append(", FP: ");
			stringBuilder.append(f1Score.getFalsePositive());
			stringBuilder.append(", FN: ");
			stringBuilder.append(f1Score.getFalseNegative());
			stringBuilder.append(System.lineSeparator());

			stringBuilder.append(System.lineSeparator());
			stringBuilder.append("Expected compatibility results:");
			stringBuilder.append(System.lineSeparator());
			stringBuilder.append(derivates);
			stringBuilder.append(System.lineSeparator());
			stringBuilder.append("KnowledgeBase attributes:");
			stringBuilder.append(System.lineSeparator());
			stringBuilder.append(knowledgeBase.toLines());
			System.out.println(stringBuilder.toString());
		}
		Assert.assertTrue("EDP LCM compatibility", status);
	}

	/**
	 * Checks single license.
	 */
	public static boolean checkResults(License licenseA, License licenseB, List<License> resultingLicenses,
			EpdLcmDerivates derivates, StringBuilder stringBuilder, F1Score f1Score)
			throws NullPointerException, IOException {
		boolean status = true;
		List<String> resultingUris = resultingLicenses.stream().map(l -> l.getUri()).collect(Collectors.toList());
		boolean derivatesValue = derivates.getValue(licenseA.getUri(), licenseB.getUri());

		// Check result and add debugging information if test failed
		if (derivatesValue && !resultingUris.contains(licenseB.getUri())) {
			stringBuilder.append("Missing: ");
			stringBuilder.append(ArrayUtil.intString(licenseB.getAttributes().getValuesArray()));
			stringBuilder.append(" ");
			stringBuilder.append(ArrayUtil.intString(licenseB.getAttributes().getInternalValuesArray()));
			stringBuilder.append(" ");
			stringBuilder.append(licenseB.getUri());
			stringBuilder.append(System.lineSeparator());
			status = false;
			f1Score.falseNegative();
		} else if (!derivatesValue && resultingUris.contains(licenseB.getUri())) {
			stringBuilder.append("Wrong:   ");
			stringBuilder.append(ArrayUtil.intString(licenseB.getAttributes().getValuesArray()));
			stringBuilder.append(" ");
			stringBuilder.append(ArrayUtil.intString(licenseB.getAttributes().getInternalValuesArray()));
			stringBuilder.append(" ");
			stringBuilder.append(licenseB.getUri());
			stringBuilder.append(System.lineSeparator());
			status = false;
			f1Score.falsePositive();
		} else if (derivatesValue && resultingUris.contains(licenseB.getUri())) {
			f1Score.truePositive();
		} else {
			f1Score.trueNegative();
		}
		// Add debugging information if test failed
		if (!status) {
			stringBuilder.append("Checked: ");
			stringBuilder.append(ArrayUtil.intString(licenseA.getAttributes().getValuesArray()));
			stringBuilder.append(" ");
			stringBuilder.append(ArrayUtil.intString(licenseA.getAttributes().getInternalValuesArray()));
			stringBuilder.append(" ");
			stringBuilder.append(licenseA.toString());
			stringBuilder.append(System.lineSeparator());
			stringBuilder.append("         ");
			stringBuilder.append(ArrayUtil.intString(licenseB.getAttributes().getValuesArray()));
			stringBuilder.append(" ");
			stringBuilder.append(ArrayUtil.intString(licenseB.getAttributes().getInternalValuesArray()));
			stringBuilder.append(" ");
			stringBuilder.append(licenseB.toString());
			stringBuilder.append(System.lineSeparator());
			stringBuilder.append("         ");
			stringBuilder.append(Arrays.toString(licenseB.getAttributes().getShortFormArray()));
			stringBuilder.append(" ");
			stringBuilder.append(Arrays.toString(licenseB.getAttributes().getShortFormArray()));
			stringBuilder.append(System.lineSeparator());
			stringBuilder.append(System.lineSeparator());
		}

		return status;
	}

	/**
	 * Gets sets of licenses which have the same restrictions and the same
	 * prohibitions. The licenses in one set may differ in permissions.
	 */
	public void listDiffInPermission() {
		Map<String, List<License>> map = new HashMap<>();
		for (License licenseA : knowledgeBase.getLicenses()) {
			List<License> list = new LinkedList<>();
			String keyUri = licenseA.getUri();
			loopB: for (License licenseB : knowledgeBase.getLicenses()) {
				for (int i = 0; i < licenseA.getAttributes().getList().size(); i++) {
					Attribute attributeA = licenseA.getAttributes().getList().get(i);
					Attribute attributeB = licenseB.getAttributes().getList().get(i);
					if (!attributeA.getType().equals(Permission.TYPE)) {
						if (attributeA.getValue() != attributeB.getValue()) {
							continue loopB;
						}
					}
				}
				list.add(licenseB);
				if (keyUri.compareTo(licenseB.getUri()) < 0) {
					keyUri = licenseB.getUri();
				}
			}
			map.put(keyUri, list);
		}
		for (List<License> licenses : map.values()) {
			for (License license : licenses) {
				System.out.println(ArrayUtil.intString(license.getAttributes().getValuesArray()) + " " + license);
			}
			System.out.println();
		}
	}
}