package org.dice_research.opal.licenses.cc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dice_research.opal.licenses.Attributes;
import org.dice_research.opal.licenses.BackMapping;
import org.dice_research.opal.licenses.Execution;
import org.dice_research.opal.licenses.KnowledgeBase;
import org.dice_research.opal.licenses.License;

/**
 * Experiment to evaluate the compatibility results of 8 CC licenses.
 * 
 * @author Adrian Wilke
 */
public class CcExperimentAll {

	public static final String DATA_DIRECTORY = "../cc.licenserdf/cc/licenserdf/licenses/";
	private static final Logger LOGGER = LogManager.getLogger();

	public static void main(String[] args) throws IOException {
		new CcExperimentAll().execute();
	}

	public void execute() throws IOException {

		// Check availability of data
		if (!new File(DATA_DIRECTORY).exists()) {
			LOGGER.error("Directory not found: " + new File(DATA_DIRECTORY).getAbsolutePath());
		}

		// Get data
		CcData data = new CcData().setSourceDirectory(DATA_DIRECTORY).readDirectory();
		List<File> files = data.getAllRdfFiles();
		KnowledgeBase knowledgeBase = data.createKnowledgeBase(files);
		LOGGER.info("Created knowledge base, number of licenses: " + knowledgeBase.getLicenses().size());

		// Execute
		int progressCounter = 0;
		List<ResultContainer> results = new LinkedList<>();
		for (License licenseA : knowledgeBase.getLicenses()) {
			for (License licenseB : knowledgeBase.getLicenses()) {

				// Only compare pairs once.
				if (licenseA.getUri().compareTo(licenseB.getUri()) > 0) {
					continue;
				}

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

			// Print progress
			progressCounter++;
			if (progressCounter % 25 == 0) {
				LOGGER.info(progressCounter + " / " + knowledgeBase.getLicenses().size());
			}
		}

		// Collect number of compatible licenses
		Map<Integer, List<ResultContainer>> resultStats = new TreeMap<>();
		for (ResultContainer result : results) {
			int size = result.resultingLicenses.size();
			if (!resultStats.containsKey(size)) {
				resultStats.put(size, new LinkedList<>());
			}
			resultStats.get(size).add(result);
		}

		// Print results
		System.out.println("Results:" + results.size());
		for (Entry<Integer, List<ResultContainer>> entry : resultStats.entrySet()) {
			System.out.println(entry.getKey() + "\t" + entry.getValue().size());
		}
		for (Entry<Integer, List<ResultContainer>> entry : resultStats.entrySet()) {
			if (entry.getValue().size() < 50) {
				for (ResultContainer resultContainer : entry.getValue()) {
					System.out.println(resultContainer);
				}
			}
		}

	}

	public class ResultContainer {
		public License licenseA;
		public License licenseB;
		public Set<License> resultingLicenses;

		public ResultContainer(License licenseA, License licenseB, Set<License> resultingLicenses) {
			this.licenseA = licenseA;
			this.licenseB = licenseB;
			this.resultingLicenses = resultingLicenses;
		}

		@Override
		public String toString() {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(resultingLicenses.size());
			stringBuilder.append("\t");
			stringBuilder.append(licenseA.getUri());
			stringBuilder.append("\t");
			stringBuilder.append(licenseB.getUri());
			stringBuilder.append("\t");
			stringBuilder.append(resultingLicenses);
			return stringBuilder.toString();
		}
	}
}