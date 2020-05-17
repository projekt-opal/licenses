package org.example.utils;

/**
 * Configuration values via system properties.
 * 
 * Usage: To set a system property using the command line, use
 * '-Dproperty=value'.
 *
 * @see https://docs.oracle.com/javase/8/docs/technotes/tools/windows/java.html
 *
 * @author 33a1cc8d616a72f953d8e15274194bcd5aac2b78fbe6b4a4d1a911e0f2ef00cd
 */
public abstract class Cfg {

	public static final String KEY_CC_LICENSERDF = "cc.licenserdf";
	public static final String KEY_RUN_EDP_LCM_TESTS = "run.edp.lcm.tests";

	/**
	 * Gets value for system property key.
	 * 
	 * @throws RuntimeException if system property key not found
	 */
	public static final String get(String key) throws Exception {
		String value = System.getProperty(key);
		if (value == null) {
			throw new Exception("Could not find system property " + key);
		}
		return value;
	}

	/**
	 * Gets directory of CC.LicenseRDF.
	 * 
	 * @throws RuntimeException if system property key not found
	 */
	public static final String getCcLicenseRdf() throws Exception {
		return get(KEY_CC_LICENSERDF);
	}

	/**
	 * Returns, if EDP LCM test should be executed.
	 * 
	 * @throws RuntimeException if system property key not found
	 */
	public static final String getRunEdpLcmTests() throws Exception {
		return get(KEY_RUN_EDP_LCM_TESTS);
	}

}