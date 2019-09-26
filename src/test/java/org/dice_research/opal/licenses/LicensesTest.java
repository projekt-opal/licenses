package org.dice_research.opal.licenses;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.junit.Assert;
import org.junit.Test;

public class LicensesTest {

	/**
	 * Dummy tests providing examples.
	 * 
	 * TODO: Test implementation.
	 */
	@Test
	public void test() throws Exception {

		Model inputModel = ModelFactory.createDefaultModel();
		Resource resourceA = ResourceFactory.createResource("http://example.org/resA");
		Resource resourceB = ResourceFactory.createResource("http://example.org/resB");
		Property property = ResourceFactory.createProperty("http://example.org/prop");
		inputModel.add(resourceA, property, resourceB);

		JavaApi javaApi = new JavaApi();
		Model outputModel = javaApi.process(inputModel, null);

		// Dummy test: Both models should have the same size
		Assert.assertEquals("Same size", inputModel.size(), outputModel.size());

		// Dummy test: Both models should not have the same size
		Assert.assertNotEquals("Different size", inputModel.size(),
				javaApi.process(ModelFactory.createDefaultModel(), null).size());

		// Dummy test: Exception expected
		boolean exceptionThrown = false;
		try {
			javaApi.process(null, null);
		} catch (Exception e) {
			// Exception expected
			exceptionThrown = true;
		}
		if (!exceptionThrown) {
			throw new AssertionError("Model size error.");
		}
	}

	/**
	 * Dummy tests with example datasets.
	 * 
	 * TODO: Test implementation.
	 */
	@Test
	public void testOdcIntegration() throws Exception {
		// Read test datasets
		Model datasetModelA = TestUtils.getDatasetAsModel("odc-a");
		Model datasetModelB = TestUtils.getDatasetAsModel("odc-b");

		// Process datasets
		JavaApi javaApi = new JavaApi();
		Model datasetModelAProcessed = javaApi.process(datasetModelA, null);
		Model datasetModelBProcessed = javaApi.process(datasetModelB, null);

		// Test (dummy)
		Assert.assertEquals(datasetModelA.size(), datasetModelAProcessed.size());
		Assert.assertEquals(datasetModelB.size(), datasetModelBProcessed.size());
	}

}