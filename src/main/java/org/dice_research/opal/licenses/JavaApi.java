package org.dice_research.opal.licenses;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.vocabulary.DCAT;
import org.dice_research.opal.common.interfaces.JenaModelProcessor;

/**
 * Public API for other Java components.
 * 
 * @author Adrian Wilke
 */
public class JavaApi implements JenaModelProcessor {

	/**
	 * Reads data in given Jena {@link Model}, processes data related to DCAT
	 * {@link DCAT#Dataset} URIs, and returns new Jena {@link Model}.
	 * 
	 * @param model      Jena input model
	 * @param datasetUri URI of DCAT dataset to process
	 * @return Jena output model with processed data
	 * @throws Exception On errors
	 */
	@Override
	public Model process(Model model, String datasetUri) throws Exception {
		return new Licenses().process(model, datasetUri);
	}

}