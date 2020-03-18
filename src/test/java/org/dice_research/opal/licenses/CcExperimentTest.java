package org.dice_research.opal.licenses;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.dice_research.opal.licenses.cc.CcData;
import org.dice_research.opal.licenses.cc.CcExperiment;
import org.dice_research.opal.licenses.cc.CcMatrix;
import org.dice_research.opal.licenses.utils.ArrayUtil;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests {@link CcExperiment}.
 * 
 * @author Adrian Wilke
 */
public class CcExperimentTest {

	public static final String DATA_DIRECTORY = "../cc.licenserdf/cc/licenserdf/licenses/";
	public static final String DATA_REPOSITORY = "https://github.com/projekt-opal/cc.licenserdf";

	public CcExperiment experiment;
	public KnowledgeBase knowledgeBase;
	public CcMatrix matrix;

	/**
	 * Checks data directory. Creates objects.
	 */
	@Before
	public void setUp() {
		Assume.assumeTrue("Please make available: " + DATA_REPOSITORY, new File(DATA_DIRECTORY).exists());
		CcData data = new CcData();
		experiment = new CcExperiment();
		knowledgeBase = data.setSourceDirectory(DATA_DIRECTORY).createKnowledgeBase(data.getMatixFiles());
		matrix = new CcMatrix();
	}

	/**
	 * Tests if CC matrix results are computed correctly.
	 */
	@Test
	public void testCreativeCommonsCompatibility() {
		boolean okay = true;
		StringBuilder stringBuilder = new StringBuilder();
		for (License license : knowledgeBase.getLicenses()) {
			// TODO
			System.out.println();
			System.err.println(" " + license);
			List<License> resultingLicenses = experiment.getMatchingLicenses(knowledgeBase,
					license.getAttributes().getInternalValuesArray());
			okay = checkResults(license, resultingLicenses, stringBuilder) && okay;
		}
		if (!okay) {
			stringBuilder.append("Expected compatibility results:");
			stringBuilder.append(System.lineSeparator());
			stringBuilder.append(matrix);
			stringBuilder.append(System.lineSeparator());
			stringBuilder.append("KnowledgeBase attributes:");
			stringBuilder.append(System.lineSeparator());
			stringBuilder.append(knowledgeBase.toLines());

			System.out.println(stringBuilder.toString());
		}
		Assert.assertTrue("Creative Commons compatibility", okay);
	}

	/**
	 * Checks single license. Used in {@link #testCreativeCommonsCompatibility()}.
	 */
	private boolean checkResults(License license, List<License> resultingLicenses, StringBuilder stringBuilder) {
		boolean okay = true;
		stringBuilder.append("Checking: ");
		stringBuilder.append(ArrayUtil.intString(license.getAttributes().getValuesArray()));
		stringBuilder.append(" ");
		stringBuilder.append(license.toString());
		stringBuilder.append(System.lineSeparator());
		List<String> resultingUris = resultingLicenses.stream().map(l -> l.getUri()).collect(Collectors.toList());

		// Check every URI
		for (String matrixUri : matrix.getUris()) {
			// Should be contained
			if (matrix.getBoolean(license.getUri(), matrixUri)) {
				if (!resultingUris.contains(matrixUri)) {
					stringBuilder.append("Missing:  ");
					stringBuilder.append(ArrayUtil.intString(license.getAttributes().getValuesArray()));
					stringBuilder.append(" ");
					stringBuilder.append(matrixUri);
					stringBuilder.append(System.lineSeparator());
					okay = false;
				}
			}
			// Should not be contained
			else {
				if (resultingUris.contains(matrixUri)) {
					stringBuilder.append("Wrong:    ");
					stringBuilder.append(ArrayUtil.intString(license.getAttributes().getValuesArray()));
					stringBuilder.append(" ");
					stringBuilder.append(matrixUri);
					stringBuilder.append(System.lineSeparator());
					okay = false;
				}
			}
		}

		stringBuilder.append(System.lineSeparator());
		return okay;
	}

	/**
	 * Tests if URIs from matrix and from RDF files are equal.
	 */
	@Test
	public void testEqualLicenseUris() {
		Assert.assertTrue(matrix.getUris().containsAll(knowledgeBase.getLicenseUris()));
		Assert.assertTrue(knowledgeBase.getLicenseUris().containsAll(matrix.getUris()));
	}
}