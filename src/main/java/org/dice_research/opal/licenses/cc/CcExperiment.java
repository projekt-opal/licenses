package org.dice_research.opal.licenses.cc;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dice_research.opal.licenses.Attributes;
import org.dice_research.opal.licenses.BackMapping;
import org.dice_research.opal.licenses.Execution;
import org.dice_research.opal.licenses.KnowledgeBase;
import org.dice_research.opal.licenses.License;

public class CcExperiment {

	public static final String DATA_DIRECTORY = "../cc.licenserdf/cc/licenserdf/licenses/";
	private static final Logger LOGGER = LogManager.getLogger();

	public static void main(String[] args) {
		new CcExperiment().execute();
	}

	public void execute() {

		// Check availability of data
		if (!new File(DATA_DIRECTORY).exists()) {
			LOGGER.error("Directory not found: " + new File(DATA_DIRECTORY).getAbsolutePath());
		}

		// Get data
		CcData data = new CcData().setSourceDirectory(DATA_DIRECTORY);
		List<File> files = data.getMatixFiles();
		KnowledgeBase knowledgeBase = data.createKnowledgeBase(files);

		// Execute
		List<ResultContainer> results = new LinkedList<>();
		for (License licenseA : knowledgeBase.getLicenses()) {
			for (License licenseB : knowledgeBase.getLicenses()) {

				List<License> inputLicenses = new ArrayList<>(2);
				inputLicenses.add(licenseA);
				inputLicenses.add(licenseB);

				// Operator used to compute array of internal values
				Execution execution = new Execution().setKnowledgeBase(knowledgeBase);
				Attributes resultAttributes = execution.applyOperator(inputLicenses);

				// Back-mapping
				Set<License> resultingLicenses = new BackMapping().getCompatibleLicenses(inputLicenses,
						resultAttributes, knowledgeBase);

				results.add(new ResultContainer(licenseA, licenseB, resultingLicenses));
			}
		}

		for (ResultContainer result : results) {
			System.out.println(result);
		}
	}

	public class ResultContainer {
		public License licenseA;
		public License licenseB;
		public Set<License> resultingLicenses;

		public ResultContainer(License licenseA, License licenseB, Set<License> resultingLicenses2) {
			this.licenseA = licenseA;
			this.licenseB = licenseB;
			this.resultingLicenses = resultingLicenses2;
		}

		@Override
		public String toString() {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(licenseA.getName());
			stringBuilder.append(", ");
			stringBuilder.append(licenseB.getName());
			stringBuilder.append(": ");
			for (int i = 0; i < 16 - licenseA.getName().length() - licenseB.getName().length(); i++) {
				stringBuilder.append(" ");
			}
			for (License license : resultingLicenses) {
				stringBuilder.append(license.getName());
				stringBuilder.append("  ");
			}
			if (resultingLicenses.isEmpty()) {
				stringBuilder.append("-");
			}
			return stringBuilder.toString();
		}
	}
}