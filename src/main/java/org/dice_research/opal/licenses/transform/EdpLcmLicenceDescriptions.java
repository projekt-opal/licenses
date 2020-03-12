package org.dice_research.opal.licenses.transform;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.dice_research.opal.licenses.Io;

/**
 * Parses CSV file of file "European Data Portal Licence Compatibility Matrix"
 * and sheet "Licence Descriptions". Creates resource file
 * {@value #OUTPUT_FILE}.
 *
 * Run with {@link #main(String[])}.
 * 
 * Source file:
 * https://www.europeandataportal.eu/en/content/licence-assistant-european-data-portal-licence-compatibility-matrix
 * http://www.europeandataportal.eu/sites/default/files/edp-licence-compatibility-published_v1_0.xlsx
 *
 * @author Adrian Wilke
 */
public class EdpLcmLicenceDescriptions {

	public static final String HOBBIT_OPAL = "https://hobbitdata.informatik.uni-leipzig.de/OPAL/";
	public static final String URL_LICENSES = HOBBIT_OPAL
			+ "Misc/EDP-Licence-Compatibility-Matrix/Licence-Descriptions.csv";
	public static final String DIR_RESOURCES = "src/main/resources/";
	public static final String OUTPUT_FILE = DIR_RESOURCES
			+ "edp-licence-compatibility-matrix-licence-descriptions.csv";

	public static final String TYPE_PER = "Permission";
	public static final String TYPE_REQ = "Requirement";
	public static final String TYPE_PRO = "Prohibition";

	public List<String> types = new LinkedList<>();
	public List<String> headers = new LinkedList<>();
	public List<List<String>> rows = new LinkedList<>();

	/**
	 * Creates resource file {@value #OUTPUT_FILE}.
	 */
	public static void main(String[] args) throws MalformedURLException, IOException {
		EdpLcmLicenceDescriptions instance = new EdpLcmLicenceDescriptions();

		// Get source data
		File file = File.createTempFile(EdpLcmLicenceDescriptions.class.getSimpleName(), ".csv");
		file.deleteOnExit();
		Io.download(new URL(URL_LICENSES), file);

		// Parse
		instance.parseCsv(file);

		// Dev
		if (Boolean.FALSE) {
			for (String type : instance.types) {
				System.out.print(type);
				System.out.print(" ");
			}
			System.out.println();
			for (String header : instance.headers) {
				System.out.print(header);
				System.out.print(" ");
			}
			System.out.println();
			Iterator<List<String>> itRows = instance.rows.iterator();
			while (itRows.hasNext()) {
				List<String> row = itRows.next();
				Iterator<String> itCells = row.iterator();
				while (itCells.hasNext()) {
					System.out.print(itCells.next());
					System.out.print(" ");
				}
				System.out.println();
			}
		}

		// Create
		StringBuilder stringBuilder = new StringBuilder();
		instance.createCsv(stringBuilder);
		System.out.println(stringBuilder);

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
		boolean typesParsed = false;
		boolean headersParsed = false;
		for (CSVRecord csvRecord : records) {

			// Types line
			if (!typesParsed && csvRecord.get(2).equals("Permissions")) {
				Iterator<String> iterator = csvRecord.iterator();
				String currentType = null;
				while (iterator.hasNext()) {
					String type = iterator.next();
					if (type.isEmpty()) {
						if (currentType == null) {
						} else {
							types.add(currentType);
						}
					} else if (type.equals("Permissions")) {
						currentType = TYPE_PER;
						types.add(currentType);
					} else if (type.equals("Requirements")) {
						currentType = TYPE_REQ;
						types.add(currentType);
					} else if (type.equals("Prohibitions")) {
						currentType = TYPE_PRO;
						types.add(currentType);
					} else {
						throw new RuntimeException();
					}
				}
				typesParsed = true;
			}

			// Headers line
			else if (!headersParsed && csvRecord.get(0).equals("Licence")) {
				for (int i = 2; i < csvRecord.size(); i++) {
					headers.add(csvRecord.get(i));
				}
				headers.add(csvRecord.get(1));
				headers.add(csvRecord.get(0));
				headersParsed = true;
			}

			// Main contents
			else if (csvRecord.get(2).equals("Yes") || csvRecord.get(2).equals("No")) {
				List<String> list = new LinkedList<>();
				for (int i = 2; i < csvRecord.size(); i++) {
					list.add(csvRecord.get(i));
				}
				list.add(csvRecord.get(1));
				list.add(csvRecord.get(0).replaceAll("[ ]+", " "));
				rows.add(list);
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
		row.addAll(headers);
		csvPrinter.printRecord(row);

		// Types row
		row = new LinkedList<>();
		row.addAll(types);
		row.add("");
		row.add("");
		csvPrinter.printRecord(row);

		// Value rows
		for (List<String> inRow : rows) {
			row = new LinkedList<>();
			for (String cell : inRow) {
				if (cell.equals("Yes")) {
					row.add("1");
				} else if (cell.equals("No")) {
					row.add("0");
				} else if (cell.equals("N.A.")) {
					row.add("-1");
				} else {
					row.add(cell);
				}
			}
			csvPrinter.printRecord(row);
		}
		csvPrinter.flush();
		csvPrinter.close();
	}
}