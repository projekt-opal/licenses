package org.dice_research.opal.licenses;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.dice_research.opal.licenses.edplcm.EdpLcmKnowledgeBase;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Test;

/**
 * Tests {@link EdpLcmKnowledgeBase}.
 *
 * @author Adrian Wilke
 */
public class EdpLcmKnowledgeBaseTest {

	/**
	 * Config: Execute {@link #print()}.
	 */
	public static final boolean PRINT = false;

	// Does not include attribute 'Sublicensing' as it contains a 'N.A.' value.
	public static final int NUMBER_OF_ATTRIBUTES = 13;
	public static final int NUMBER_OF_LICENSES = 32;

	@Test
	public void testAttributes() {
		Attributes attributes = new EdpLcmKnowledgeBase().getAttributes();
		checkAttributes(attributes, false);
	}

	@Test
	public void testLicenses() {
		EdpLcmKnowledgeBase knowledgeBase = new EdpLcmKnowledgeBase();
		Collection<License> licenses = knowledgeBase.getUrisToLicenses().values();

		if (knowledgeBase.skipCcPdm) {
			Assert.assertEquals(NUMBER_OF_LICENSES - 1, licenses.size());
		} else {
			Assert.assertEquals(NUMBER_OF_LICENSES, licenses.size());
		}

		if (knowledgeBase.skipSublicensing) {
			Assert.assertEquals(NUMBER_OF_ATTRIBUTES - 1, knowledgeBase.getAttributes().getUriToAttributeMap().size());
		} else {
			Assert.assertEquals(NUMBER_OF_ATTRIBUTES, knowledgeBase.getAttributes().getUriToAttributeMap().size());
		}

		for (License license : licenses) {
			Assert.assertNotNull(license.getUri());
			Assert.assertFalse(license.getUri().isEmpty());
			Assert.assertNotNull(license.getName());
			Assert.assertFalse(license.getName().isEmpty());
			checkAttributes(license.getAttributes(), true);
		}
	}

	@Test
	public void testShareAlike() throws IOException {
		List<String> uris = new EdpLcmKnowledgeBase().load().getShareAlikeLicenses("http://www.formez.it/iodl/");
		Assert.assertEquals(4, uris.size());
	}

	@Test
	public void print() {
		Assume.assumeTrue(PRINT);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(getClass().getName());
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append(new EdpLcmKnowledgeBase().toLines());
		System.out.println(stringBuilder.toString());
	}

	protected void checkAttributes(Attributes attributes, boolean hasValue) {

		// Keys
		for (String key : attributes.getUris()) {
			Assert.assertNotNull(key);
			Assert.assertFalse(key.isEmpty());
		}

		// Values
		for (Attribute attribute : attributes.getList()) {
			Assert.assertNotNull(attribute.getUri());
			Assert.assertFalse(attribute.getUri().isEmpty());
			if (hasValue) {
				Assert.assertTrue(attribute.hasValue());
				Assert.assertNotNull(attribute.getUri());
				Assert.assertFalse(attribute.getUri().isEmpty());
			} else {
				Assert.assertFalse(attribute.hasValue());
			}
		}
	}
}