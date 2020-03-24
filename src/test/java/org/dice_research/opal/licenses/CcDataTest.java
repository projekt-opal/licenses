package org.dice_research.opal.licenses;

import org.dice_research.opal.licenses.cc.CcData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests {@link CcData}.
 * 
 * @author Adrian Wilke
 */
public class CcDataTest {

	private KnowledgeBase knowledgeBase;

	/**
	 * Checks data directory. Creates objects.
	 */
	@Before
	public void setUp() {
		knowledgeBase = CcTestUtils.getKnowledgeBase();
	}

	@Test
	public void test() {
		for (License license : knowledgeBase.getLicenses()) {
			Assert.assertEquals(license.toString(), license.getAttributes().getAttribute(CcData.SHARE_ALIKE).getValue(),
					license.isShareAlike());
		}
	}

}