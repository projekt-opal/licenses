package org.example.licenses.cc;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.example.licenses.Attributes;
import org.example.licenses.Execution;
import org.example.licenses.KnowledgeBase;
import org.example.licenses.License;
import org.example.licenses.cc.CcData;
import org.example.licenses.cc.CcMatrix;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests {@link Execution}.
 * 
 * @author 33a1cc8d616a72f953d8e15274194bcd5aac2b78fbe6b4a4d1a911e0f2ef00cd
 */
public class ExecutionTest {

	private CcData ccData;
	private KnowledgeBase knowledgeBase;

	/**
	 * Checks data directory. Creates objects.
	 */
	@Before
	public void setUp() {
		ccData = CcTestUtils.getCcData();
		knowledgeBase = CcTestUtils.getKnowledgeBase(ccData);
	}

	@Test
	public void test() {
		Execution execution = new Execution().setKnowledgeBase(knowledgeBase);
		int numberOfLicenses = knowledgeBase.getLicenses().size();

		License mark = knowledgeBase.getLicense(CcMatrix.I0_MARK);
		License zero = knowledgeBase.getLicense(CcMatrix.I1_ZERO);
		License by = knowledgeBase.getLicense(CcMatrix.I2_BY);
		License bySa = knowledgeBase.getLicense(CcMatrix.I3_BY_SA);

		List<License> licenses = new LinkedList<>();
		Attributes attributes;
		Set<License> result;

		attributes = null;
		result = execution.applyBackMapping(licenses, attributes);
		Assert.assertTrue(result.isEmpty());

		licenses.add(mark);
		attributes = execution.applyOperator(licenses);
		result = execution.applyBackMapping(licenses, attributes);
		Assert.assertEquals(numberOfLicenses, result.size());
		Assert.assertTrue(result.contains(mark));

		licenses.add(zero);
		attributes = execution.applyOperator(licenses);
		result = execution.applyBackMapping(licenses, attributes);
		Assert.assertEquals(numberOfLicenses, result.size());
		Assert.assertTrue(result.contains(mark));
		Assert.assertTrue(result.contains(zero));

		licenses.add(by);
		attributes = execution.applyOperator(licenses);
		result = execution.applyBackMapping(licenses, attributes);
		Assert.assertEquals(numberOfLicenses - 2, result.size());
		Assert.assertFalse(result.contains(mark));
		Assert.assertFalse(result.contains(zero));
		Assert.assertTrue(result.contains(by));

		licenses.add(bySa);
		attributes = execution.applyOperator(licenses);
		result = execution.applyBackMapping(licenses, attributes);
		Assert.assertEquals(1, result.size());
		Assert.assertTrue(result.contains(bySa));
	}

}