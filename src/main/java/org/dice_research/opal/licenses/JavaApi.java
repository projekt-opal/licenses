package org.dice_research.opal.licenses;

import java.util.Collection;

import org.apache.jena.rdf.model.Model;
import org.dice_research.opal.common.interfaces.JenaModelProcessor;

/**
 * Public API for other Java components.
 * 
 * @author Adrian Wilke
 */
public class JavaApi implements JenaModelProcessor {

	/**
	 * Processes one or multiple DCAT datasets.
	 * 
	 * @param model Input model containing one or multipe DCAT datasets.
	 * @return Output model containing processed datasets.
	 * @throws Exception on errors.
	 */
	@Override
	public Model process(Model model, Collection<String> datasetUris) throws Exception {
		return new Licenses().process(model, datasetUris);
	}

}