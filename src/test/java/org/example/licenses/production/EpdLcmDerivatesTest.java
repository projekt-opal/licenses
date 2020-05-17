package org.example.licenses.production;

import java.io.IOException;

import org.example.licenses.edplcm.EpdLcmDerivates;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests {@link EpdLcmDerivates}.
 *
 * @author 33a1cc8d616a72f953d8e15274194bcd5aac2b78fbe6b4a4d1a911e0f2ef00cd
 */
public class EpdLcmDerivatesTest {

	public static final String CC0_1_0 = "https://creativecommons.org/publicdomain/zero/1.0/legalcode";
	public static final String GFDL_1_3 = "https://gnu.org/licenses/fdl-1.3.en.html";

	@Test
	public void test() throws IOException {
		EpdLcmDerivates derivates = new EpdLcmDerivates();

		// Size
		Assert.assertEquals(32, derivates.getUris().size());

		// First value
		Assert.assertTrue(derivates.getValue(CC0_1_0, CC0_1_0));

		// Last value
		Assert.assertTrue(derivates.getValue(GFDL_1_3, GFDL_1_3));
	}

}
