package org.dice_research.opal.licenses;

import java.util.List;

import org.dice_research.opal.licenses.operator.Attribute;
import org.dice_research.opal.licenses.operator.Attributes;
import org.dice_research.opal.licenses.operator.EdpKnowledgeBase;
import org.dice_research.opal.licenses.operator.License;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests {@link EdpKnowledgeBase}.
 *
 * @author Adrian Wilke
 */
public class EdpKnowledgeBaseTest {

	// Does not include attribute 'Sublicensing' as it contains a 'N.A.' value.
	public static final int NUMBER_OF_ATTRIBUTES = 13 - 1;
	public static final int NUMBER_OF_LICENSES = 32;

	@Test
	public void testAttributes() {
		Attributes attributes = new EdpKnowledgeBase().getAttributes();
		checkAttributes(attributes, false);
	}

	@Test
	public void testLicenses() {
		List<License> licenses = new EdpKnowledgeBase().getLicenses();
		Assert.assertEquals(NUMBER_OF_LICENSES, licenses.size());
		for (License license : licenses) {
			Assert.assertNotNull(license.getUri());
			Assert.assertFalse(license.getUri().isEmpty());
			Assert.assertNotNull(license.getName());
			Assert.assertFalse(license.getName().isEmpty());
			checkAttributes(license.getAttributes(), true);
		}
	}

	protected void checkAttributes(Attributes attributes, boolean hasValue) {

		Assert.assertEquals(NUMBER_OF_ATTRIBUTES, attributes.getMap().size());

		// Keys
		for (String key : attributes.getMap().keySet()) {
			Assert.assertNotNull(key);
			Assert.assertFalse(key.isEmpty());
		}

		// Values
		for (Attribute attribute : attributes.getMap().values()) {
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
