package org.dice_research.opal.licenses.operator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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