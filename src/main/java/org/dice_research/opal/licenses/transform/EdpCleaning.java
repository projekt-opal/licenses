package org.dice_research.opal.licenses.transform;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dice_research.opal.licenses.Io;

/**
 * Checks SPARQL results (CSV file) for duplicate keys ('license') and combines
 * them by summing up values ('count').
 *
 * @author Adrian Wilke
 */
public class EdpCleaning {

	// Configuration: URLs and files
	public static final String SOURCE_URL = "https://hobbitdata.informatik.uni-leipzig.de/OPAL/Statistics/Licenses/2020-03-05-EDP-Licenses-Distributions.csv";
	public static final String SOURCE_FILE_PREFIX = "EDP-Licenses-Distributions-";

	// Configuration: File handling
	public static final boolean DELETE_SOURCE = true;

	private static final Logger LOGGER = LogManager.getLogger();

	/**
	 * Main entry point.
	 */
	public static void main(String[] args) throws IOException {
		new EdpCleaning().clean();
	}

	/**
	 * Sorts map by value.
	 * 
	 * @see https://dzone.com/articles/how-to-sort-a-map-by-value-in-java-8
	 */
	public static Map<String, Integer> sortByValue(final Map<String, Integer> map) {
		return map.entrySet().stream().sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
	}

	/**
	 * Cleans EDP license data as given by {@link #SOURCE_URL}.
	 */
	private void clean() throws IOException {

		// Download
		File sourceFile = download();
		if (DELETE_SOURCE) {
			sourceFile.deleteOnExit();
		} else {
			LOGGER.info("Downloaded file: " + sourceFile.getAbsolutePath());
		}

		// Parse
		Map<String, Integer> map = parseCsv(sourceFile);
		int counter = 0;
		for (Integer count : map.values()) {
			counter += count;
		}
		LOGGER.info("Number of entries: " + counter);

		// Sort by value
		map = sortByValue(map);

		// Write
		StringBuilder stringBuilder = new StringBuilder();
		putCsv(map, stringBuilder);
		File outFile = new File(SOURCE_URL.substring(SOURCE_URL.lastIndexOf("/") + 1));
		FileUtils.write(outFile, stringBuilder, StandardCharsets.UTF_8);
		LOGGER.info("Wrote file: " + outFile.getAbsolutePath());
	}

	protected void putCsv(Map<String, Integer> map, Appendable appendable) throws IOException {
		CSVPrinter csvPrinter = CSVFormat.DEFAULT.withHeader("license", "count").print(appendable);
		for (Entry<String, Integer> entry : map.entrySet()) {
			csvPrinter.printRecord(entry.getKey(), entry.getValue());
		}
		csvPrinter.flush();
		csvPrinter.close();
	}

	/**
	 * Parses CSV. Ignores first line.
	 */
	protected Map<String, Integer> parseCsv(File file) throws IOException {
		Map<String, Integer> map = new HashMap<>();
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(new FileReader(file));
		int inputCounter = 0;
		for (CSVRecord csvRecord : records) {
			String license = csvRecord.get(0);
			int count = Integer.parseInt(csvRecord.get(1));
			inputCounter += count;
			if (map.containsKey(license)) {
				map.put(license, map.get(license) + count);
				LOGGER.info("Duplicate entry: " + license);
			} else {
				map.put(license, count);
			}
		}

		// Check counters
		int outputCounter = 0;
		for (Integer count : map.values()) {
			outputCounter += count;
		}
		if (inputCounter != outputCounter) {
			throw new IOException("In: " + inputCounter + ", out: " + outputCounter);
		}

		return map;
	}

	/**
	 * Downloads URL data to temporary file.
	 */
	protected File download() throws IOException {
		File file = File.createTempFile(SOURCE_FILE_PREFIX, ".csv");
		Io.download(new URL(SOURCE_URL), file);
		return file;
	}
}