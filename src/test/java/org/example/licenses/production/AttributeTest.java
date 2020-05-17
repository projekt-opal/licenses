package org.example.licenses.production;

import org.example.licenses.Attribute;
import org.example.licenses.Permission;
import org.example.licenses.Requirement;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests {@link Attribute}.
 *
 * @author 33a1cc8d616a72f953d8e15274194bcd5aac2b78fbe6b4a4d1a911e0f2ef00cd
 */
public class AttributeTest {

	/**
	 * Tests equals method based on URIs of {@link Attribute}s
	 */
	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void testEquals() {

		// Test types

		Permission permission = new Permission();
		Assert.assertTrue(permission.equals(permission));

		Assert.assertTrue(new Permission().equals(new Permission()));

		Assert.assertFalse(new Permission().equals(new Requirement()));

		// Test URIs

		Permission permission1 = new Permission();
		permission1.setUri("http://example.com/1");
		Permission permission2 = new Permission();
		permission2.setUri("http://example.com/2");
		Permission permissionEmptyUri = new Permission();
		permissionEmptyUri.setUri("");

		Assert.assertTrue(permission1.equals(new Permission().setUri(permission1.getUri())));

		Assert.assertTrue(permission.equals(permissionEmptyUri));
		Assert.assertTrue(permissionEmptyUri.equals(permission));

		Assert.assertFalse(permission1.equals(permission));
		Assert.assertFalse(permission.equals(permission1));

		Assert.assertFalse(permission1.equals(permissionEmptyUri));
		Assert.assertFalse(permissionEmptyUri.equals(permission1));

		Assert.assertFalse(permission1.equals(permission2));
		Assert.assertFalse(permission2.equals(permission1));
	}

}