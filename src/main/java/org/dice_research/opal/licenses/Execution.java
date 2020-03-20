package org.dice_research.opal.licenses;

import java.util.ArrayList;
import java.util.List;

/**
 * Methods for execution.
 * 
 * @author Adrian Wilke
 */
public class Execution {

	private KnowledgeBase knowledgeBase;

	/**
	 * Applies operator on the given licenses list. Returns operator result in
	 * attributes object based on attributes in knowledge base (types, URIs).
	 */
	public Attributes applyOperator(List<License> licenses) {

		// Collect internal values of licenses
		List<boolean[]> internalValues = new ArrayList<>(licenses.size());
		for (License license : licenses) {
			internalValues.add(license.getAttributes().getInternalValuesArray());
		}

		// Apply operator
		boolean[] result = new Operator().compute(internalValues);

		// Put results in attribute object
		Attributes attributes = new Attributes();
		for (int i = 0; i < knowledgeBase.getAttributes().getList().size(); i++) {
			attributes.addAttribute(
					AttributeFactory.get().createAttribute(knowledgeBase.getAttributes().getList().get(i), false)
							.setValueByInternal(result[i]));
		}
		return attributes;
	}

	public Execution setKnowledgeBase(KnowledgeBase knowledgeBase) {
		this.knowledgeBase = knowledgeBase;
		return this;
	}

}