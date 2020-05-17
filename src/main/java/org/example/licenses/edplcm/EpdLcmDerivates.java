package org.example.licenses.edplcm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.licenses.Attribute;
import org.example.licenses.transform.EdpLcmUriMapping;

/**
 * EDP Licence Compatibility Matrix - Derivates.
 * 
 * Usage: Use {@link #getUris()} and {@link #getValue(String, String)}.
 * 
 * @author 33a1cc8d616a72f953d8e15274194bcd5aac2b78fbe6b4a4d1a911e0f2ef00cd
 */
public class EpdLcmDerivates {

	private static final Logger LOGGER = LogManager.getLogger();

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

	public List<String> getCompatibleUris(String uri) throws IOException {
		if (!isLoaded) {
			load();
		}
		List<String> compatibleUris = new LinkedList<>();
		Vector<Boolean> compatible = matrix.get(getIndexOfUri(uri));
		for (String uriB : getUris()) {
			if (compatible.get(getIndexOfUri(uriB))) {
				compatibleUris.add(uriB);
			}
		}
		return compatibleUris;
	}

	Integer getIndexOfUri(String uri) {
		if (!uris.containsKey(uri)) {
			throw new RuntimeException("URI not found: " + uri);
		} else {
			return uris.get(uri);
		}
	}

	public Boolean getValue(String uriA, String uriB) throws IOException {
		if (!isLoaded) {
			load();
		}
		return matrix.get(getIndexOfUri(uriA)).get(getIndexOfUri(uriB));
	}

	public EpdLcmDerivates load() throws IOException {
		boolean idsParsed = false;

		// Get license URIs

		EdpLcmKnowledgeBase edpLcmKnowledgeBase = new EdpLcmKnowledgeBase().load();
		Map<String, String> namesToUris = EdpLcmUriMapping.mapNamesToUris(edpLcmKnowledgeBase);

		// Load matrix

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
					uris.put(namesToUris.get(csvRecord.get(i)), i);
				}
				idsParsed = true;
			}

			// Values
			else {
				vector = new Vector<>();
				for (int i = 0; i < csvRecord.size() - 1; i++) {
					vector.add(csvRecord.get(i).equals("0") ? false : true);
				}
				String uri = namesToUris.get(csvRecord.get(csvRecord.size() - 1));

				// Check ID of columns and rows
				if (!uris.get(uri).equals(rowId - 1)) {
					throw new RuntimeException(uri + " does not match " + (rowId - 1));
				}
				matrix.add(vector);
			}
		}
		inputStream.close();

		isLoaded = true;
		return this;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		List<String> uris = new ArrayList<>();
		try {
			uris = getUris();
		} catch (IOException e) {
			LOGGER.error(e);
		}
		for (String uriA : uris) {
			for (String uriB : uris) {
				try {
					stringBuilder.append(Attribute.booleanToBinary(getValue(uriA, uriB)));
				} catch (IOException e) {
					LOGGER.error(e);
				}
				stringBuilder.append(" ");
			}
			stringBuilder.append(uriA == null ? "-" : uriA);
			stringBuilder.append(System.lineSeparator());
		}
		return stringBuilder.toString();
	}
}