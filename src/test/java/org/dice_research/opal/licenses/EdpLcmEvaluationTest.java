package org.dice_research.opal.licenses;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.dice_research.opal.licenses.edplcm.EdpLcmKnowledgeBase;
import org.dice_research.opal.licenses.edplcm.EpdLcmDerivates;
import org.dice_research.opal.licenses.utils.ArrayUtil;
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
		int errorCounter = 0;
		StringBuilder stringBuilder = new StringBuilder();

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
				boolean result = checkResults(licenseA, licenseB, resultingLicenses, derivates, stringBuilder);
				if (!result) {
					errorCounter++;
				}
				status = status & result;
			}
		}

		// Print debugging info, if test failed
		if (!status) {
			stringBuilder.append("Expected compatibility results:");
			stringBuilder.append(System.lineSeparator());
			stringBuilder.append(derivates);
			stringBuilder.append(System.lineSeparator());
			stringBuilder.append("KnowledgeBase attributes:");
			stringBuilder.append(System.lineSeparator());
			stringBuilder.append(knowledgeBase.toLines());
			stringBuilder.append(System.lineSeparator());
			stringBuilder.append("Errors: ");
			stringBuilder.append(errorCounter);
			System.out.println(stringBuilder.toString());
		}
		Assert.assertTrue("Creative Commons compatibility", status);
	}

	/**
	 * Checks single license.
	 */
	public static boolean checkResults(License licenseA, License licenseB, List<License> resultingLicenses,
			EpdLcmDerivates derivates, StringBuilder stringBuilder) throws NullPointerException, IOException {
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
		} else if (!derivatesValue && resultingUris.contains(licenseB.getUri())) {
			stringBuilder.append("Wrong:   ");
			stringBuilder.append(ArrayUtil.intString(licenseB.getAttributes().getValuesArray()));
			stringBuilder.append(" ");
			stringBuilder.append(ArrayUtil.intString(licenseB.getAttributes().getInternalValuesArray()));
			stringBuilder.append(" ");
			stringBuilder.append(licenseB.getUri());
			stringBuilder.append(System.lineSeparator());
			status = false;
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
}