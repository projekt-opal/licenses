package org.dice_research.opal.licenses;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.jena.graph.Triple;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.junit.Assert;
import org.junit.Test;

public class LicensesTest {

	public static final HashMap<LicenseAttribute, Boolean> cc0attrs;
	
	static {
		cc0attrs = new HashMap<>();
		cc0attrs.put(LicenseAttribute.Permission.REPRODUCTION, true);
		cc0attrs.put(LicenseAttribute.Permission.DISTRIBUTION, true);
		cc0attrs.put(LicenseAttribute.Permission.DERIVATIVE, true);
		cc0attrs.put(LicenseAttribute.Permission.SUBLICENSING, true);
		cc0attrs.put(LicenseAttribute.Permission.PATENTGRANT, false);
		cc0attrs.put(LicenseAttribute.Requirement.NOTICE, false);
		cc0attrs.put(LicenseAttribute.Requirement.ATTRIBUTION, false);
		cc0attrs.put(LicenseAttribute.Requirement.SHAREALIKE, false);
		cc0attrs.put(LicenseAttribute.Requirement.COPYLEFT, false);
		cc0attrs.put(LicenseAttribute.Requirement.LESSERCOPYLEFT, false);
		cc0attrs.put(LicenseAttribute.Requirement.STATECHANGES, false);
		cc0attrs.put(LicenseAttribute.Prohibition.COMMERCIAL, false);
		cc0attrs.put(LicenseAttribute.Prohibition.USETRADEMARK, false);
	}
	
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
		
		Collection<String> expectedLicenses = new LinkedList<>();
		expectedLicenses.add("https://www.govdata.de/dl-de/by-2-0");
		
		Assert.assertEquals(expectedLicenses, lc.getLicenseSuggestions(ccbynd40));

		
		List<String> cc0Suggestions = lc.getLicenseSuggestions(cc0);
		
		Assert.assertEquals("List doesn't start with input license", "https://creativecommons.org/publicdomain/zero/1.0/legalcode", cc0Suggestions.get(0));

		expectedLicenses = new HashSet<>();
		expectedLicenses.add("https://creativecommons.org/publicdomain/zero/1.0/legalcode");
		expectedLicenses.add("http://www.nationalarchives.gov.uk/doc/open-government-licence/version/3/");
		expectedLicenses.add("http://www.nationalarchives.gov.uk/doc/open-government-licence/version/1/");
		expectedLicenses.add("https://www.govdata.de/dl-de/by-2-0");
		expectedLicenses.add("https://www.govdata.de/dl-de/by-1-0");
		expectedLicenses.add("http://data.gov.ro/base/images/logoinst/OGL-ROU-1.0.pdf");
		expectedLicenses.add("https://joinup.ec.europa.eu/software/page/eupl/licence-eupl");
		expectedLicenses.add("https://www.govdata.de/dl-de/zero-2-0");
		expectedLicenses.add("https://www.govdata.de/dl-de/by-nc-1-0");
		expectedLicenses.add("http://www.nationalarchives.gov.uk/doc/open-government-licence/version/2/");
		expectedLicenses.add("http://opendatacommons.org/licenses/pddl/1-0/");
		expectedLicenses.add("http://www.nationalarchives.gov.uk/doc/non-commercial-government-licence/non-commercial-government-licence.htm");
		
		// test remaining data order-independent
		Set<String> cc0SuggSet = new HashSet<String>(cc0Suggestions);
		Assert.assertEquals(expectedLicenses, cc0SuggSet);
	}
	
	/**
	 * Test license attributes
	 * 
	 * Tests if by giving a single license(no combination computation performed) we get the exact attributes of this license
	 */
	@Test
	public void testLicenseAttributes0() throws Exception {
		LicenseCombinator lc = new LicenseCombinator();
		
		Collection<String> cc0 = new LinkedList<String>();
		cc0.add("https://creativecommons.org/publicdomain/zero/1.0/legalcode");
		
		Assert.assertEquals(lc.getLicenseAttributes(cc0), cc0attrs);
	}

	/**
	 * Test license attributes
	 * 
	 * Tests if by combining two licenses, we get the smallest denominator attribute set.
	 */
	@Test
	public void testLicenseAttributes1() throws Exception {
		LicenseCombinator lc = new LicenseCombinator();
		
		Collection<String> ccbynd40 = new LinkedList<String>();
		ccbynd40.add("https://creativecommons.org/publicdomain/zero/1.0/legalcode");
		ccbynd40.add("http://creativecommons.org/licenses/by-nd/4.0/legalcode");
		
		HashMap<LicenseAttribute, Boolean> combination = new HashMap<>();
		combination.put(LicenseAttribute.Permission.REPRODUCTION, true);
		combination.put(LicenseAttribute.Permission.DISTRIBUTION, true);
		combination.put(LicenseAttribute.Permission.DERIVATIVE, false);
		combination.put(LicenseAttribute.Permission.SUBLICENSING, false);
		combination.put(LicenseAttribute.Permission.PATENTGRANT, false);
		combination.put(LicenseAttribute.Requirement.NOTICE, true);
		combination.put(LicenseAttribute.Requirement.ATTRIBUTION, true);
		combination.put(LicenseAttribute.Requirement.SHAREALIKE, false);
		combination.put(LicenseAttribute.Requirement.COPYLEFT, false);
		combination.put(LicenseAttribute.Requirement.LESSERCOPYLEFT, false);
		combination.put(LicenseAttribute.Requirement.STATECHANGES, true);
		combination.put(LicenseAttribute.Prohibition.COMMERCIAL, false);
		combination.put(LicenseAttribute.Prohibition.USETRADEMARK, false);
		
		Assert.assertEquals(lc.getLicenseAttributes(ccbynd40), combination);
	}
	
	/**
	 * Tests getting a license compatible to a given attribute set
	 */
	@Test
	public void testGetLicenseFromAttributes() throws Exception {
		LicenseCombinator lc = new LicenseCombinator();

		List<String> cc0 = new LinkedList<String>();
		cc0.add("https://creativecommons.org/publicdomain/zero/1.0/legalcode");
		
		Set<String> licensesFromAttributes = new HashSet<>(lc.getLicenseFromAttributes(cc0attrs));
		Set<String> expectedURIs = new HashSet<>(lc.getLicenseSuggestions(cc0));
		
		Assert.assertEquals(licensesFromAttributes, expectedURIs);
	}
	
	@Test
	public void testGetLicenseSuggestionsFromModels() throws Exception {
		Model m0 = ModelFactory.createDefaultModel();
		Model m1 = ModelFactory.createDefaultModel();

		/* ### Build models ### */
		
		Resource s, o;
		Property p;
		
		s = m0.createResource("file:///resource0");
		p = m0.createProperty(Strings.DCT_LICENSE);

		o = m0.createResource("https://creativecommons.org/publicdomain/zero/1.0/legalcode");
		m0.add(s, p, o);
		
		o = m0.createResource("http://creativecommons.org/licenses/by-nc-nd/4.0/legalcode");
		m0.add(s, p, o);

		
		s = m1.createResource("file:///resource1");
		p = m1.createProperty(Strings.DCT_LICENSE);

		o = m1.createResource("http://creativecommons.org/licenses/by-nd/4.0/legalcode");
		m1.add(s, p ,o);
		o = m1.createResource("http://creativecommons.org/licenses/by-nc-nd/4.0/legalcode");
		m1.add(s, p, o);
		
		/* ### Real testing ### */
		
		LicenseCombinator lc = new LicenseCombinator();
		
		Set<Set<String>> suggestions = lc.getLicenseSuggestions(m0, m1).stream().map(list -> new HashSet<>(list)).collect(Collectors.toSet());
		
		Set<String> suggestion = new HashSet<>();
		suggestion.add("https://www.govdata.de/dl-de/by-2-0");
		
		Assert.assertTrue(suggestions.contains(suggestion));
		
		suggestion.clear();
		suggestion.add("http://creativecommons.org/licenses/by-nc-nd/4.0/legalcode");
		suggestion.add("http://creativecommons.org/licenses/by-nc/4.0/legalcode");
		suggestion.add("http://creativecommons.org/licenses/by-nc-sa/4.0/legalcode");
		
		Assert.assertTrue(suggestions.contains(suggestion));
	}
}