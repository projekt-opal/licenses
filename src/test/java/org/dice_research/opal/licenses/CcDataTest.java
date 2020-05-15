package org.dice_research.opal.licenses;

import java.io.IOException;

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

	public static final boolean PRINT_KNOWLEDGEBASE = false;

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
	public void testCcData() throws IOException {
		Assert.assertEquals(614, ccData.readDirectory().getAllRdfFiles().size());
	}

	@Test
	public void testKnowledgeBase() {

		for (License license : knowledgeBase.getLicenses()) {
			Assert.assertEquals(license.toString(), license.getAttributes().getAttribute(CcData.SHARE_ALIKE).getValue(),
					license.isShareAlike());
		}

		Assert.assertEquals(8, knowledgeBase.getLicenses().size());

		Assert.assertEquals(7, knowledgeBase.getAttributes().getList().size());

		if (PRINT_KNOWLEDGEBASE) {
			System.out.println(knowledgeBase.toLines());
		}

	}

}