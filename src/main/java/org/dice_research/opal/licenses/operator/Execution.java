package org.dice_research.opal.licenses.operator;

import java.io.File;
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
		for (License license : knowledgeBase.getLicenses()) {
			attributes.add(license.mapToBoolean(configuration));
		}
		return new Operator().compute(attributes);
	}

	public Execution readCsv(File file) {
		knowledgeBase = new CsvReader().readCsv(file);
		return this;
	}

	public Execution readOdrl(File file) {
		knowledgeBase = new OdrlReader().readOdrl(file);
		return this;
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