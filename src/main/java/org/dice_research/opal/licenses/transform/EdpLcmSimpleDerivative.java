package org.dice_research.opal.licenses.transform;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.dice_research.opal.licenses.io.Net;

/**
 * Parses CSV file of file "European Data Portal Licence Compatibility Matrix"
 * and sheet "Simple Derivative". Corrects error in IDs (CC-PDM). Creates
 * resource file {@value #OUTPUT_FILE}.
 *
 * Run with {@link #main(String[])}.
 *
 * Source file:
 * https://www.europeandataportal.eu/en/content/licence-assistant-european-data-portal-licence-compatibility-matrix
 * http://www.europeandataportal.eu/sites/default/files/edp-licence-compatibility-published_v1_0.xlsx
 *
 * @author Adrian Wilke
 */
public class EdpLcmSimpleDerivative {

	public static final String HOBBIT_OPAL = "https://hobbitdata.informatik.uni-leipzig.de/OPAL/";
	public static final String URL_DERIVATIVE = HOBBIT_OPAL
			+ "Misc/EDP-Licence-Compatibility-Matrix/Simple-Derivative.csv";
	public static final String DIR_RESOURCES = "src/main/resources/";
	public static final String OUTPUT_FILE = DIR_RESOURCES + "edp-licence-compatibility-matrix-simple-derivative.csv";

	public List<String> ids = new LinkedList<>();
	public Map<String, List<String>> rows = new HashMap<>();

	/**
	 * Creates resource file {@value #OUTPUT_FILE}.
	 */
	public static void main(String[] args) throws MalformedURLException, IOException {
		EdpLcmSimpleDerivative instance = new EdpLcmSimpleDerivative();

		// Get source data
		File file = File.createTempFile(EdpLcmSimpleDerivative.class.getSimpleName(), ".csv");
		file.deleteOnExit();
		Net.download(new URL(URL_DERIVATIVE), file);

		// Parse
		instance.parseCsv(file);

		// Dev
		if (Boolean.FALSE) {
			for (String id : instance.ids) {
				System.out.println(id);
			}
			for (Entry<String, List<String>> row : instance.rows.entrySet()) {
				System.out.print(row.getKey());
				System.out.print(" ");
				System.out.println(row.getValue());
			}
		}

		// Create
		StringBuilder stringBuilder = new StringBuilder();
		instance.createCsv(stringBuilder);

		// Write
		File outFile = new File(OUTPUT_FILE);
		FileUtils.write(outFile, stringBuilder, StandardCharsets.UTF_8);
		System.out.println("Wrote: " + outFile.getAbsolutePath());
	}

	/**
	 * Parses CSV extracted from source XLSX.
	 */
	protected void parseCsv(File file) throws IOException {
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(new FileReader(file));
		boolean idsParsed = false;
		for (CSVRecord csvRecord : records) {

			// Headers line
			if (!idsParsed && csvRecord.get(0).equals("Original Licence")) {
				Iterator<String> iterator = csvRecord.iterator();

				// Skip first cell
				iterator.next();

				while (iterator.hasNext()) {
					String id = iterator.next().replaceAll("[ ]+", " ");
					// Correct error in source file
					if (id.equals("CC-PDM 4.0")) {
						id = "CC-PDM 1.0";
					}
					ids.add(id);
				}
				idsParsed = true;
			}

			// Only get main contents
			else if (csvRecord.get(1).equals("Yes") || csvRecord.get(1).equals("No")) {
				List<String> list = new LinkedList<>();

				// Check IDs of rows and cols
				String id = csvRecord.get(0).replaceAll("[ ]+", " ");
				if (!ids.contains(id)) {
					throw new IOException("Unknown: " + id);
				}

				// Add row
				for (int i = 1; i < csvRecord.size(); i++) {
					list.add(csvRecord.get(i));
				}

				rows.put(id, list);
			}
		}
	}

	/**
	 * Creates binary values CSV.
	 */
	protected void createCsv(Appendable appendable) throws IOException {
		CSVPrinter csvPrinter = CSVFormat.DEFAULT.print(appendable);

		// Header row
		List<String> row = new LinkedList<>();
		row.addAll(ids);
		row.add("");
		csvPrinter.printRecord(row);

		// Value rows
		for (int i = 0; i < rows.entrySet().size(); i++) {
			row = new LinkedList<>();

			// First cell
			String id = ids.get(i);

			for (String cell : rows.get(id)) {
				if (cell.equals("Yes")) {
					row.add("1");
				} else if (cell.equals("No")) {
					row.add("0");
				} else {
					row.add(cell);
				}
			}
			row.add(id);
			csvPrinter.printRecord(row);
		}
		csvPrinter.flush();
		csvPrinter.close();
	}
}