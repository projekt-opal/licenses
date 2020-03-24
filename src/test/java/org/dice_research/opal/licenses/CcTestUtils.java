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

	public static final String MARK = "http://creativecommons.org/publicdomain/mark/1.0/";
	public static final String ZERO = "http://creativecommons.org/publicdomain/zero/1.0/";
	public static final String BY = "http://creativecommons.org/licenses/by/4.0/";
	public static final String BY_SA = "http://creativecommons.org/licenses/by-sa/4.0/";
	public static final String BY_NC = "http://creativecommons.org/licenses/by-nc/4.0/";
	public static final String BY_ND = "http://creativecommons.org/licenses/by-nd/4.0/";
	public static final String BY_NC_SA = "http://creativecommons.org/licenses/by-nc-sa/4.0/";
	public static final String BY_NC_ND = "http://creativecommons.org/licenses/by-nc-nd/4.0/";

	public static KnowledgeBase getKnowledgeBase() {
		Assume.assumeTrue("Please make available: " + DATA_REPOSITORY, new File(DATA_DIRECTORY).exists());
		CcData data = new CcData();
		return data.setSourceDirectory(DATA_DIRECTORY).createKnowledgeBase(data.getMatixFiles());
	}

}