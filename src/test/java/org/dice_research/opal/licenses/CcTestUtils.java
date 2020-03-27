package org.dice_research.opal.licenses;

import java.io.File;

import org.dice_research.opal.licenses.cc.CcData;
import org.junit.Assume;

/**
 * Data for CC tests.
 * 
 * @author Adrian Wilke
 */
public abstract class CcTestUtils {

	/**
	 * Test configuration: CC data.
	 */
	public static final String DATA_DIRECTORY = "../cc.licenserdf/cc/licenserdf/licenses/";

	public static final String DATA_REPOSITORY = "https://github.com/projekt-opal/cc.licenserdf";

	public static KnowledgeBase getKnowledgeBase(CcData ccDdata) {
		Assume.assumeTrue("Please make available: " + DATA_REPOSITORY, new File(DATA_DIRECTORY).exists());
		return ccDdata.createKnowledgeBase(ccDdata.getMatixFiles());
	}

	public static CcData getCcData() {
		Assume.assumeTrue("Please make available: " + DATA_REPOSITORY, new File(DATA_DIRECTORY).exists());
		return new CcData().setSourceDirectory(DATA_DIRECTORY);
	}

}