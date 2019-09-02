package org.dice_research.opal.licenses;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.junit.Assert;
import org.junit.Test;

public class LicensesTest {

	@Test
	public void test() throws Exception {

		Model inputModel = ModelFactory.createDefaultModel();
		Resource resourceA = ResourceFactory.createResource("http://example.org/resA");
		Resource resourceB = ResourceFactory.createResource("http://example.org/resB");
		Property property = ResourceFactory.createProperty("http://example.org/prop");
		inputModel.add(resourceA, property, resourceB);

		JavaApi javaApi = new JavaApi();
		Model outputModel = javaApi.process(inputModel);

		// Dummy test: Both models should have the same size
		Assert.assertEquals("Same size", inputModel.size(), outputModel.size());

		// Dummy test: Both models should not have the same size
		Assert.assertNotEquals("Different size", inputModel.size(),
				javaApi.process(ModelFactory.createDefaultModel()).size());

		// Dummy test: Exception expected
		boolean exceptionThrown = false;
		try {
			javaApi.process(null);
		} catch (Exception e) {
			// Exception expected
			exceptionThrown = true;
		}
		if (!exceptionThrown) {
			throw new AssertionError("Model size error.");
		}

	}

}
