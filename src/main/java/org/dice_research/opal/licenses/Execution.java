package org.dice_research.opal.licenses;

import java.util.LinkedList;
import java.util.List;

/**
 * Execution.
 *
 * @author Adrian Wilke
 */
public class Execution {

	private Configuration configuration = new Configuration();
	private KnowledgeBase knowledgeBase;

	public boolean[] compute() {
		List<boolean[]> attributes = new LinkedList<>();
		for (License license : knowledgeBase.getUrisToLicenses().values()) {
			attributes.add(license.mapToBoolean(configuration));
		}
		return new Operator().compute(attributes);
	}

	public Execution setConfiguration(Configuration configuration) {
		this.configuration = configuration;
		return this;
	}

	public Execution setKnowledgeBase(KnowledgeBase knowledgeBase) {
		this.knowledgeBase = knowledgeBase;
		return this;
	}

}