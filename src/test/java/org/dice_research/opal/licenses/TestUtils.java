package org.dice_research.opal.licenses;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.RDFDataMgr;

/**
 * Test utilities.
 * 
 * @author Adrian Wilke
 */
public abstract class TestUtils {

	public static String getDatasetAsString(String id) throws IOException {
		File file = new File("src/test/resources/org/dice_research/opal/licenses/datasets/" + id + ".ttl");
		return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
	}

	public static Model getDatasetAsModel(String id) {
		File file = new File("src/test/resources/org/dice_research/opal/licenses/datasets/" + id + ".ttl");
		Model model = ModelFactory.createDefaultModel();
		RDFDataMgr.read(model, file.toURI().toString());
		return model;
	}

}