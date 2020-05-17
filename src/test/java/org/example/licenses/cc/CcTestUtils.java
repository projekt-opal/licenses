package org.example.licenses.cc;

import java.io.File;

import org.example.licenses.KnowledgeBase;
import org.example.licenses.cc.CcData;
import org.example.utils.Cfg;
import org.junit.Assume;

/**
 * Data for CC tests.
 * 
 * @author Adrian Wilke
 */
public abstract class CcTestUtils {

	public static final String DATA_REPOSITORY = "https://github.com/creativecommons/cc.licenserdf";

	public static KnowledgeBase getKnowledgeBase(CcData ccDdata) {
		getDataDirectory();
		return ccDdata.createKnowledgeBase(ccDdata.getMatixFiles());
	}

	public static CcData getCcData() {
		return new CcData().setSourceDirectory(getDataDirectory());
	}

	/**
	 * Checks if data directory is available and returns it.
	 */
	private static String getDataDirectory() {

		String directory = null;
		try {
			directory = Cfg.getCcLicenseRdf();
		} catch (Exception e) {
			Assume.assumeNotNull(e.getMessage(), directory);
		}

		Assume.assumeTrue("Please make available: " + DATA_REPOSITORY, new File(directory).exists());

		return directory;
	}

}