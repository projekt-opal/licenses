package org.dice_research.opal.licenses;

import org.apache.jena.rdf.model.Model;

/**
 * Public API for other Java components.
 * 
 * @author Adrian Wilke
 */
public class JavaApi {

	/**
	 * Processes one or multiple DCAT datasets.
	 * 
	 * @param model Input model containing one or multipe DCAT datasets.
	 * @return Output model containing processed datasets.
	 * @throws Exception on errors.
	 */
	public Model process(Model model) throws Exception {
		return new Licenses().process(model);
	}

}