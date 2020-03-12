package org.dice_research.opal.licenses.old.cleaning;

import org.apache.jena.rdf.model.Model;
import org.dice_research.opal.common.interfaces.JenaModelProcessor;
import org.dice_research.opal.common.interfaces.ModelProcessor;

/**
 * Public API for other Java components.
 * 
 * @author Adrian Wilke
 */
@SuppressWarnings("deprecation")
public class JavaApi implements ModelProcessor, JenaModelProcessor {

	@Override
	public void processModel(Model model, String datasetUri) throws Exception {
		new Licenses().process(model, datasetUri);
	}

	/**
	 * @deprecated Replaced by {@link #processModel(Model, String)}.
	 */
	@Deprecated
	@Override
	public Model process(Model model, String datasetUri) throws Exception {
		processModel(model, datasetUri);
		return model;
	}

	/**
	 * @deprecated Replaced by {@link #processModel(Model, String)}.
	 */
	@Deprecated
	public Model process(Model model) throws Exception {
		processModel(model, null);
		return model;
	}
}