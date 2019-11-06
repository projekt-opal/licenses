package org.dice_research.opal.licenses;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.dice_research.opal.licenses.exceptions.LicensesException;

public class Licenses {

	public Model process(Model model, String datasetUri) throws LicensesException {
		Model returnModel = ModelFactory.createDefaultModel();

		// TODO: Process datasetUris

		// Dummy
		returnModel.add(model);

		return model;
	}

}