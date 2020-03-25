package org.dice_research.opal.licenses;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.dice_research.opal.licenses.edplcm.EdpLcmKnowledgeBase;
import org.dice_research.opal.licenses.edplcm.EdpLcmUris;
import org.dice_research.opal.licenses.edplcm.EpdLcmDerivates;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests {@link EpdLcmDerivates} and {@link EdpLcmKnowledgeBase}.
 * 
 * @author Adrian Wilke
 */
public class EdpLcmEvaluationSingleTest {

	private EpdLcmDerivates derivates;
	private EdpLcmKnowledgeBase knowledgeBase;

	@Before
	public void setUp() throws Exception {
		derivates = new EpdLcmDerivates();
		knowledgeBase = new EdpLcmKnowledgeBase().load();
	}

	@Test
	public void test() throws IOException {
		List<String> uris = new LinkedList<>();
		uris.add(EdpLcmUris.CC_BY_3_0_Austria);
		uris.add(EdpLcmUris.CC_BY_3_0_NL);
		uris.add(EdpLcmUris.CC_BY_4_0);
		check(uris);
	}

	@Test
	public void testWorking() throws IOException {
		List<String> uris = new LinkedList<>();
		uris.add(EdpLcmUris.CC0_1_0);
		uris.add(EdpLcmUris.CC_BY_4_0);
		check(uris);
	}

	public void check(List<String> licenseUris) throws IOException {
		boolean status = true;
		int errorCounter = 0;
		StringBuilder stringBuilder = new StringBuilder();

		// Combine licenses to check every cell in matrix
		for (License licenseA : knowledgeBase.getLicenses()) {

			if (!licenseUris.contains(licenseA.getUri()))
				continue;

			for (License licenseB : knowledgeBase.getLicenses()) {

				if (!licenseUris.contains(licenseB.getUri()))
					continue;

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
				boolean result = EdpLcmEvaluationTest.checkResults(licenseA, licenseB, resultingLicenses, derivates,
						stringBuilder);
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

}