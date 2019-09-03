package org.dice_research.opal.licenses;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Helper class to extract datasets for test cases.
 * 
 * Use {@link #main(String[])} and call {@link #getDataset(String)} to extract
 * dataset TURTLE.
 * 
 * @author Adrian Wilke
 */
@SuppressWarnings("unused")
public class DatasetExtractor {

	private final static String ENDPOINT_FUSEKI = "http://opalpro.cs.upb.de:3030/opal/sparql";
	private final static String ENDPOINT_VIRTUOSO = "http://opalpro.cs.upb.de:8891/sparql";
	private final static String ENDPOINT = ENDPOINT_VIRTUOSO;

	// License URI examples (see OPAL D3.3)
	private final static String URI_OGL_A = "http://europeandataportal.eu/content/show-license?license_id=OGL2.0";
	private final static String URI_OGL_B = "https://www.europeandataportal.eu/content/show-license?license_id=OGL2.0";
	private final static String URI_ODC_A = "https://www.europeandataportal.eu/content/show-license?license_id=ODC-ODbL";
	private final static String URI_ODC_B = "http://europeandataportal.eu/content/show-license?license_id=ODC-ODbL";

	// Distribution has license
	// http://europeandataportal.eu/content/show-license?license_id=OGL2.0
	private final static String DATASET_A = "http://projekt-opal.de/dataset/https___europeandataportal_eu_set_data_informatii_financiare";
	// Distribution has license
	// http://europeandataportal.eu/content/show-license?license_id=ODC-ODbL
	private final static String DATASET_B = "http://projekt-opal.de/dataset/https___europeandataportal_eu_set_data_lista_tuturor_institutiilor_2018";

	private static final Logger LOGGER = LogManager.getLogger();

	public static void main(String[] args) throws IOException {

		DatasetExtractor datasetExtractor = new DatasetExtractor();

		// Print catalogs
		if (Boolean.TRUE.equals(false))
			System.out.println(datasetExtractor.getCatalogs());

		// Print model filtered by license
		if (Boolean.TRUE.equals(false))
			RDFDataMgr.write(System.out, datasetExtractor.getDatasetsByLicense(DatasetExtractor.URI_OGL_B),
					Lang.TURTLE);

		// Print dataset for tests
		if (Boolean.TRUE.equals(false))
			RDFDataMgr.write(System.out, datasetExtractor.getDataset(DatasetExtractor.DATASET_B), Lang.TURTLE);
	}

	private Model getDataset(String datasetUri) throws IOException {
		LOGGER.info("getDataset");
		String query = getQuery("dataset").replace("?dataset", "<" + datasetUri + ">");
		return execConstruct(query);
	}

	private Model getDatasetsByLicense(String license) throws IOException {
		LOGGER.info("getDatasetsByLicense");
		String query = getQuery("datasets-by-license").replace("?license", "<" + license + ">");
		return execConstruct(query);
	}

	private List<String> getCatalogs() throws IOException {
		LOGGER.info("getCatalogs");
		List<String> list = new LinkedList<String>();
		ResultSet resultSet = execSelect(getQuery("catalogs"));
		while (resultSet.hasNext()) {
			list.add(resultSet.next().getResource("catalog").toString());
		}
		return list;
	}

	/**
	 * Executes CONSTRUCT query on endpoint.
	 */
	private Model execConstruct(String query) {
		LOGGER.info("Construct on " + ENDPOINT);
		QueryExecution queryExecution = QueryExecutionFactory.sparqlService(ENDPOINT, query);
		return queryExecution.execConstruct();
	}

	/**
	 * Executes SELECT query on endpoint.
	 */
	private ResultSet execSelect(String query) {
		LOGGER.info("Select on " + ENDPOINT);
		QueryExecution queryExecution = QueryExecutionFactory.sparqlService(ENDPOINT, query);
		return queryExecution.execSelect();
	}

	/**
	 * Gets query from file.
	 */
	private String getQuery(String id) throws IOException {
		File file = new File("src/test/resources/org/dice_research/opal/licenses/queries/" + id + ".txt");
		return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
	}

}