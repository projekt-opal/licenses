package org.dice_research.opal.licenses;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.jena.graph.Triple;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.dice_research.opal.licenses.LicenseCombinator.License;
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

	public int countLicensesInModel(Model m) {
		Set<String> licenses = new HashSet<>();
		
		StmtIterator stmts = m.listStatements();
		
		while (stmts.hasNext()) {
			Statement stmt = stmts.next();
			Triple t = stmt.asTriple();
			
			if (Strings.DCT_LICENSE.equals(t.getPredicate().toString()))
				licenses.add(t.getObject().toString());
		}
		
		/* System.out.print("Licenses: ");
		for (String s : licenses) {
			System.out.print(s + "  ");
		}
		System.out.println(); */
		
		return licenses.size();
	}
	
	/**
	 * Test unifying of licenses.
	 * 
	 * ModelA contains `http://europeandataportal.eu/content/show-license?license_id=OGL2.0`
	 * ModelB contains `https://www.europeandataportal.eu/content/show-license?license_id=OGL2.0`
	 * 
	 * The test generates a merged model, processes it and counts the unique license URIs.
	 */
	@Test
	public void testLicenseUnification() throws Exception {
		// Read test datasets
		Model datasetModelA = TestUtils.getDatasetAsModel("odc-a");
		Model datasetModelB = TestUtils.getDatasetAsModel("odc-b");

		Model merged = ModelFactory.createDefaultModel();
		merged.add(datasetModelA);
		merged.add(datasetModelB);

		// Process datasets
		JavaApi javaApi = new JavaApi();
		Model processed = javaApi.process(merged);

		Assert.assertEquals(countLicensesInModel(merged), 2);
		Assert.assertEquals(countLicensesInModel(processed), 1);
	}
	
	private void printList(List<? extends Object> objs) {
		System.out.print("[");
		
		Iterator<? extends Object> it = objs.iterator();
		
		while (it.hasNext()) {
			System.out.print(it.next().toString());
			if (it.hasNext()) System.out.print(", ");
		}
		
		System.out.println("]");
	}
	
	/**
	 * Test license combinations.
	 */
	@Test
	public void testLicenseCombinator() throws Exception {
		Collection<String> cc0 = new LinkedList<String>();
		cc0.add("https://creativecommons.org/publicdomain/zero/1.0/legalcode");
		
		Collection<String> ccbynd40 = new LinkedList<String>();
		ccbynd40.addAll(cc0);
		ccbynd40.add("http://creativecommons.org/licenses/by-nd/4.0/legalcode");
		
		LicenseCombinator lc = new LicenseCombinator();
		printList(lc.getLicenceSuggestions(cc0));
		printList(lc.getLicenceSuggestions(ccbynd40));
	}
}