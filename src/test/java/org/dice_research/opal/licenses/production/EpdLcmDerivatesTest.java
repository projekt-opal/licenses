package org.dice_research.opal.licenses.production;

import java.io.IOException;

import org.dice_research.opal.licenses.edplcm.EpdLcmDerivates;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests {@link EpdLcmDerivates}.
 *
 * @author Adrian Wilke
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
