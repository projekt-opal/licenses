package org.example.licenses.cc;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.licenses.Attributes;
import org.example.licenses.BackMapping;
import org.example.licenses.Execution;
import org.example.licenses.KnowledgeBase;
import org.example.licenses.License;
import org.example.utils.Cfg;

/**
 * Experiment to evaluate the compatibility results of CC license triples.
 * 
 * This experiment runs for around 9 hours 30 minutes and generates 612+1 files,
 * around 523 MB all together.
 * 
 * @author 33a1cc8d616a72f953d8e15274194bcd5aac2b78fbe6b4a4d1a911e0f2ef00cd
 */
public class CcExperimentTriples {

	// Will generate several GB of data
	public static final boolean WRITE_OUTPUT_LISTS = false;

	private static final Logger LOGGER = LogManager.getLogger();
	private Map<String, Integer> licenceIndex = new HashMap<>();

	public static void main(String[] args) throws Exception {
		CcExperimentTriples experiment = new CcExperimentTriples();
		experiment.loadData(Cfg.getCcLicenseRdf());
		experiment.execute();
		experiment.printResults(new File("").getAbsolutePath());
	}

	private KnowledgeBase knowledgeBase;

	public CcExperimentTriples loadData(String dataDirectory) throws IOException {

		// Check availability of data
		if (!new File(dataDirectory).exists()) {
			LOGGER.error("Directory not found: " + new File(dataDirectory).getAbsolutePath());
			return null;
		}

		// Get data
		CcData data = new CcData().setSourceDirectory(dataDirectory).readDirectory();
		List<File> files = data.getAllRdfFiles();
		knowledgeBase = data.createKnowledgeBase(files);

		LOGGER.info("Created knowledge base, number of licenses: " + knowledgeBase.getLicenses().size());
		return this;
	}

	public void printResults(String resultFilesDirectory) throws IOException {

		// Check availability of directory
		File directory = new File(resultFilesDirectory);
		if (!directory.exists()) {
			LOGGER.error("Directory not found: " + directory.getAbsolutePath());
			return;
		}

		// Get result files
		FilenameFilter filter = new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				if (name.startsWith("result") && name.endsWith(".txt"))
					return true;
				else
					return false;
			}
		};
		File[] resultFiles = directory.listFiles(filter);

		// Read results
		Map<Integer, Integer> resultStats = new TreeMap<>();
		for (File file : resultFiles) {
			for (String line : FileUtils.readLines(file, StandardCharsets.UTF_8)) {
				Integer size = Integer.valueOf(line.substring(0, line.indexOf("\t")));
				if (!resultStats.containsKey(size)) {
					resultStats.put(size, 0);
				}
				resultStats.put(size, resultStats.get(size) + 1);
			}
		}

		// Print results
		int counter = 0;
		for (Entry<Integer, Integer> entry : resultStats.entrySet()) {
			System.out.println(entry.getKey() + "\t" + entry.getValue());
			counter += entry.getValue();
		}
		System.out.println();
		System.out.println(counter);
	}

	public CcExperimentTriples execute() throws IOException {
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

		return this;
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