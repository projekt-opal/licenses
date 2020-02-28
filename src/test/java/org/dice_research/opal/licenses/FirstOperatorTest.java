package org.dice_research.opal.licenses;

import java.io.File;

import org.dice_research.opal.licenses.operator.Attribute;
import org.dice_research.opal.licenses.operator.Configuration;
import org.dice_research.opal.licenses.operator.Execution;
import org.dice_research.opal.licenses.operator.KnowledgeBase;
import org.junit.Test;

public class FirstOperatorTest {

	@Test
	public void test() {

		// Notice: Attribute objects can be compared by equals method, which is based on
		// their URis.

		File file = new File("");
		KnowledgeBase knowledgeBase = new KnowledgeBase();

		// use one of the read methods to create the knowledge base (kb)
		// kb attributes: just set the uris
		// kb licenses: set uris of licenses
		// kb licenses: set uris of attributes in licenses
		// kb licenses: set values of attributes in licenses
//		knowledgeBase = new OdrlReader().readOdrl(file);
//		knowledgeBase = new CsvReader().readCsv(file);

		// Add all attributes:
		Configuration configuration = new Configuration();
		for (Attribute attribute : knowledgeBase.getAttributes().getMap().values()) {
			configuration.addAttribute(attribute);
		}

		Execution execution = new Execution();
		execution.setKnowledgeBase(knowledgeBase);
		execution.setConfiguration(configuration);
		boolean[] result = execution.compute();

		System.out.println(result);
		// TODO: Add tests

	}

	public void execute() {

		// TODO create a configuration

	}
}
