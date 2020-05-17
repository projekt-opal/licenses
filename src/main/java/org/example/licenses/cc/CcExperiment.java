package org.example.licenses.cc;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.licenses.Attributes;
import org.example.licenses.BackMapping;
import org.example.licenses.Execution;
import org.example.licenses.KnowledgeBase;
import org.example.licenses.License;
import org.example.utils.Cfg;

/**
 * Experiment to evaluate the compatibility results of 8 CC licenses.
 * 
 * @author Adrian Wilke
 */
public class CcExperiment {

	private static final Logger LOGGER = LogManager.getLogger();

	public static void main(String[] args) throws Exception {
		new CcExperiment().execute(Cfg.getCcLicenseRdf());
	}

	public void execute(String dataDirectory) {

		// Check availability of data
		if (!new File(dataDirectory).exists()) {
			LOGGER.error("Directory not found: " + new File(dataDirectory).getAbsolutePath());
		}

		// Get data
		CcData data = new CcData().setSourceDirectory(dataDirectory);
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

		System.out.println();
		System.out.println(toTable(results, true));

		System.out.println();
		System.out.println(toTable(results, false));
	}

	private String toTable(List<ResultContainer> results, boolean limitToInput) {
		StringBuilder stringBuilder = new StringBuilder();
		String divider = "\t";

		// Header
		stringBuilder.append("-");
		stringBuilder.append(divider);
		ResultContainer lastResult = new ResultContainer(null, null, null);
		for (ResultContainer result : results) {
			if (!limitToInput && result.inputContainsNd()) {
				continue;
			}
			if (result.licenseA != lastResult.licenseA) {
				stringBuilder.append(replaceName(result.licenseA.getName()));
				stringBuilder.append(divider);
				lastResult = result;
			}
		}

		// Value rows
		lastResult = new ResultContainer(null, null, null);
		for (ResultContainer result : results) {
			if (!limitToInput && result.inputContainsNd()) {
				continue;
			}
			if (result.licenseA != lastResult.licenseA) {
				stringBuilder.append(System.lineSeparator());
				stringBuilder.append(replaceName(result.licenseA.getName()));
				stringBuilder.append(divider);
			}
			if (result.resultingLicenses.isEmpty()) {
				stringBuilder.append("-");
			} else {
				Set<License> resultingLicenses = new HashSet<License>(result.resultingLicenses);
				if (limitToInput) {
					List<License> inputLicenses = new LinkedList<>();
					inputLicenses.add(result.licenseA);
					inputLicenses.add(result.licenseB);
					resultingLicenses.retainAll(inputLicenses);
				}
				if (resultingLicenses.size() == 8) {
					stringBuilder.append("all");
				} else {
					stringBuilder.append(setToString(resultingLicenses, divider));
				}
			}
			stringBuilder.append(divider);

			lastResult = result;
		}
		stringBuilder.append(System.lineSeparator());

		return stringBuilder.toString();
	}

	private StringBuilder setToString(Set<License> licenses, String divider) {
		TreeSet<License> orderdLicenses = new TreeSet<>(new Comparator<License>() {

			@Override
			public int compare(License o1, License o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		orderdLicenses.addAll(licenses);

		StringBuilder stringBuilder = new StringBuilder();
		boolean first = true;
		for (License license : orderdLicenses) {
			if (first) {
				first = false;
			} else {
				stringBuilder.append(", ");
			}
			stringBuilder.append(replaceName(license.getName()));
		}
		return stringBuilder;
	}

	private String replaceName(String name) {
		if (name.equals("mark")) {
			return "PD";
		}
		return name.toUpperCase();
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

		private boolean inputContainsNd() {
			if (licenseA.getName().toLowerCase().contains("nd") || licenseB.getName().toLowerCase().contains("nd")) {
				return true;
			} else {
				return false;
			}
		}
	}
}