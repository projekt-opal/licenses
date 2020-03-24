package org.dice_research.opal.licenses;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests {@link BackMapping}.
 * 
 * @author Adrian Wilke
 */
public class CcBackMappingTest extends BackMapping {

	private KnowledgeBase knowledgeBase;
	private License BY, BY_NC, BY_SA, BY_NC_SA;

	/**
	 * Checks data directory. Creates objects.
	 */
	@Before
	public void setUp() {
		knowledgeBase = CcTestUtils.getKnowledgeBase();
		BY = knowledgeBase.getLicense(CcTestUtils.BY);
		BY_NC = knowledgeBase.getLicense(CcTestUtils.BY_NC);
		BY_SA = knowledgeBase.getLicense(CcTestUtils.BY_SA);
		BY_NC_SA = knowledgeBase.getLicense(CcTestUtils.BY_NC_SA);
	}

	/**
	 * E.g. If setting for CommercialUse (Prohibition) is 1, the result must not
	 * contain 0.
	 */
	@Test
	public void testRemoveLessRestrictive() {
		License BY = knowledgeBase.getLicense(CcTestUtils.BY);
		License BY_NC = knowledgeBase.getLicense(CcTestUtils.BY_NC);

		Attributes setting = BY_NC.getAttributes();
		List<License> licenses = new LinkedList<>();
		licenses.add(BY);
		licenses.add(BY_NC);
		List<License> resultingLicenses = removeLessRestrictive(setting, licenses, false);

		Assert.assertTrue("BY NC", resultingLicenses.contains(BY_NC));
		Assert.assertFalse("not BY", resultingLicenses.contains(BY));
	}

	/**
	 * E.g. If setting for CommercialUse (Prohibition) is 0, the result must not
	 * contain 1.
	 */
	@Test
	public void testRemoveMoreRestrictive() {

		Attributes setting = BY.getAttributes();
		List<License> licenses = new LinkedList<>();
		licenses.add(BY);
		licenses.add(BY_NC);
		licenses.add(BY_NC_SA);
		List<License> resultingLicenses = removeMoreRestrictive(setting, licenses, false);

		Assert.assertTrue("BY", resultingLicenses.contains(BY));
		Assert.assertFalse("not BY NC", resultingLicenses.contains(BY_NC));
		Assert.assertFalse("not BY NC SA", resultingLicenses.contains(BY_NC_SA));

		// Test meta attributes

		licenses.add(BY_SA);
		resultingLicenses = removeMoreRestrictive(setting, licenses, false);
		Assert.assertTrue("BY SA", resultingLicenses.contains(BY_SA));
		resultingLicenses = removeMoreRestrictive(setting, licenses, true);
		Assert.assertFalse("not BY SA", resultingLicenses.contains(BY_SA));
	}

}