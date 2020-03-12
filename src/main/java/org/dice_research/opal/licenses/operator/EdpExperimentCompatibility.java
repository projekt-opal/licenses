package org.dice_research.opal.licenses.operator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
 * Reads compatibility information of the "European Data Portal Licence
 * Compatibility Matrix".
 * 
 * Usage: Use {@link #getUris()} and {@link #getValue(String, String)}.
 * 
 * @author Adrian Wilke
 */
public class EdpExperimentCompatibility {

	public static final String RESOURCE_CSV = "edp-licence-compatibility-matrix-simple-derivative.csv";
	protected Vector<Vector<Boolean>> matrix = new Vector<>();
	protected LinkedHashMap<String, Integer> uris = new LinkedHashMap<>();
	protected boolean isLoaded = false;

	// TODO: Remove when completed
	public static void main(String[] args) throws IOException {
		new EdpExperimentCompatibility().runExperiment();
	}

	protected void runExperiment() throws IOException {
		EdpKnowledgeBase kb = new EdpKnowledgeBase();

		// Structure to access licenses by URIs used in this class
		Map<String, License> nameuriToLicense = new HashMap<>();
		for (License license : kb.getLicenses().values()) {
			nameuriToLicense.put(EdpKnowledgeBase.attributeIdToUri(license.getName()), license);
		}

		StringBuilder stringBuilder = new StringBuilder();

		for (Attribute attribute : nameuriToLicense.values().iterator().next().getAttributes().getObjects()) {
			System.out.println(attribute.getClass().getSimpleName());
			System.out.println(attribute.getUri());
			System.out.println();
		}

		for (String uriLicenseA : getUris()) {
			License licenseA = nameuriToLicense.get(uriLicenseA);
			for (String uriLicenseB : getUris()) {
				License licenseB = nameuriToLicense.get(uriLicenseB);
				boolean[] result = new Operator().compute(licenseA.getAttributes().getInternalArray(),
						licenseB.getAttributes().getInternalArray());
				List<License> resultingLicenses = kb.getMatchingLicenses(result, false);
				addResult(stringBuilder, licenseA, licenseB, result, resultingLicenses);
			}
		}

		// TODO: Investigate

		System.out.println(stringBuilder);
	}

	protected void addResult(StringBuilder stringBuilder, License licenseA, License licenseB, boolean[] result,
			List<License> resultingLicenses) {

		for (Attribute license : licenseA.getAttributes().getObjects()) {
			if (license instanceof Permission) {
				stringBuilder.append("Per ");
			} else if (license instanceof Prohibition) {
				stringBuilder.append("Pro ");

			} else if (license instanceof Requirement) {
				stringBuilder.append("Req ");
			}
		}
		stringBuilder.append(System.lineSeparator());

		stringBuilder.append(licenseA.getName());
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append(Arrays.toString(licenseA.getAttributes().getInternalArray()));
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append(licenseB.getName());
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append(Arrays.toString(licenseB.getAttributes().getInternalArray()));
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append("Result");
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append(Arrays.toString(result));
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append(resultingLicenses);
		stringBuilder.append(System.lineSeparator());
		// TODO: Instead of the following test, directly get the intersection of
		// compatible
		// licenses of A and B.
		if ((Boolean.FALSE && resultingLicenses.contains(licenseA)) && resultingLicenses.contains(licenseB)) {
			stringBuilder.append("ok");
		} else {
			stringBuilder.append("fail");
		}
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append(System.lineSeparator());
	}

	public List<String> getUris() throws IOException {
		if (!isLoaded) {
			load();
		}
		return new ArrayList<>(uris.keySet());
	}

	public Boolean getValue(String uriA, String uriB) throws IOException {
		if (!isLoaded) {
			load();
		}
		return matrix.get(uris.get(uriA)).get(uris.get(uriB));
	}

	protected EdpExperimentCompatibility load() throws IOException {
		boolean idsParsed = false;

		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(RESOURCE_CSV);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		Iterable<CSVRecord> csvRecords = CSVFormat.DEFAULT.parse(bufferedReader);
		Vector<Boolean> vector;
		int rowId = -1;
		for (CSVRecord csvRecord : csvRecords) {
			rowId++;

			// First line: Collect IDs
			if (!idsParsed) {
				for (int i = 0; i < csvRecord.size() - 1; i++) {
					uris.put(EdpKnowledgeBase.attributeIdToUri(csvRecord.get(i)), i);
				}
				idsParsed = true;
			}

			// Values
			else {
				vector = new Vector<>();
				for (int i = 0; i < csvRecord.size() - 1; i++) {
					vector.add(csvRecord.get(i).equals("0") ? false : true);
				}
				String id = EdpKnowledgeBase.attributeIdToUri(csvRecord.get(csvRecord.size() - 1));

				// Check ID of columns and rows
				if (!uris.get(id).equals(rowId - 1)) {
					throw new RuntimeException(id + " does not match " + (rowId - 1));
				}
				matrix.add(vector);
			}
		}
		isLoaded = true;
		return this;
	}

}