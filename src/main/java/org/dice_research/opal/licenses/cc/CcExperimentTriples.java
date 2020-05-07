package org.dice_research.opal.licenses.cc;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dice_research.opal.licenses.Attributes;
import org.dice_research.opal.licenses.BackMapping;
import org.dice_research.opal.licenses.Execution;
import org.dice_research.opal.licenses.KnowledgeBase;
import org.dice_research.opal.licenses.License;

/**
 * Experiment to evaluate the compatibility results of CC license triples.
 * 
 * @author Adrian Wilke
 */
public class CcExperimentTriples {

	// Will generate several GB of data
	public static final boolean WRITE_OUTPUT_LISTS = false;

	public static final String DATA_DIRECTORY = "../cc.licenserdf/cc/licenserdf/licenses/";
	private static final Logger LOGGER = LogManager.getLogger();
	private Map<String, Integer> licenceIndex = new HashMap<>();

	public static void main(String[] args) throws IOException {
		CcExperimentTriples experiment = new CcExperimentTriples();
		experiment.loadData();
		experiment.execute();
	}

	private KnowledgeBase knowledgeBase;

	public CcExperimentTriples loadData() throws IOException {

		// Check availability of data
		if (!new File(DATA_DIRECTORY).exists()) {
			LOGGER.error("Directory not found: " + new File(DATA_DIRECTORY).getAbsolutePath());
		}

		// Get data
		CcData data = new CcData().setSourceDirectory(DATA_DIRECTORY).readDirectory();
		List<File> files = data.getAllRdfFiles();
		knowledgeBase = data.createKnowledgeBase(files);

		LOGGER.info("Created knowledge base, number of licenses: " + knowledgeBase.getLicenses().size());
		return this;
	}

	public void execute() throws IOException {
		int progressCounter = 1;

		// Write URI index

		File indexFile = new File("index.txt");
		int counter = 1;
		for (License license : knowledgeBase.getLicenses()) {
			licenceIndex.put(license.getUri(), counter++);
		}
		for (Entry<String, Integer> entry : licenceIndex.entrySet()) {
			FileUtils.writeStringToFile(indexFile, entry.getKey() + "\t" + entry.getValue() + "\n",
					StandardCharsets.UTF_8, true);
		}
		LOGGER.info("Wrote: " + indexFile.getAbsolutePath());

		// Go through triples

		for (License licenseA : knowledgeBase.getLicenses()) {

			// Do not re-calculate existing results
			File file = new File("result" + progressCounter++ + ".txt");
			if (file.exists()) {
				continue;
			}

			List<ResultContainer> results = new LinkedList<>();

			for (License licenseB : knowledgeBase.getLicenses()) {
				for (License licenseC : knowledgeBase.getLicenses()) {

					// Only compare triples once.
					if (licenseA.getUri().compareTo(licenseB.getUri()) > 0) {
						continue;
					}
					if (licenseA.getUri().compareTo(licenseC.getUri()) > 0) {
						continue;
					}
					if (licenseB.getUri().compareTo(licenseC.getUri()) > 0) {
						continue;
					}

					List<License> inputLicenses = new ArrayList<>(3);
					inputLicenses.add(licenseA);
					inputLicenses.add(licenseB);
					inputLicenses.add(licenseC);

					// Operator used to compute array of internal values
					Execution execution = new Execution().setKnowledgeBase(knowledgeBase);
					Attributes resultAttributes = execution.applyOperator(inputLicenses);

					// Back-mapping
					Set<License> resultingLicenses = new BackMapping().getCompatibleLicenses(inputLicenses,
							resultAttributes, knowledgeBase);

					results.add(new ResultContainer(licenseA, licenseB, licenseC, resultingLicenses));
				}
			}

			// Write results of iteration to file
			for (ResultContainer resultContainer : results) {
				FileUtils.write(file, resultContainer.toString() + "\n", StandardCharsets.UTF_8, true);
			}
			LOGGER.info("Wrote: " + file.getAbsolutePath());
		}

	}

	public class ResultContainer {
		public License licenseA;
		public License licenseB;
		public License licenseC;
		public Set<License> resultingLicenses;

		public ResultContainer(License licenseA, License licenseB, License licenseC, Set<License> resultingLicenses) {
			this.licenseA = licenseA;
			this.licenseB = licenseB;
			this.licenseC = licenseC;
			this.resultingLicenses = resultingLicenses;
		}

		@Override
		public String toString() {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(resultingLicenses.size());
			stringBuilder.append("\t");
			stringBuilder.append(licenceIndex.get(licenseA.getUri()));
			stringBuilder.append("\t");
			stringBuilder.append(licenceIndex.get(licenseB.getUri()));
			stringBuilder.append("\t");
			stringBuilder.append(licenceIndex.get(licenseC.getUri()));
			if (WRITE_OUTPUT_LISTS) {
				stringBuilder.append("\t");
				for (License license : resultingLicenses) {
					stringBuilder.append(licenceIndex.get(license.getUri()));
					stringBuilder.append(" ");
				}
			}
			return stringBuilder.toString();
		}
	}
}