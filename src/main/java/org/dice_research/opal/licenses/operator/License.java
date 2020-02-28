package org.dice_research.opal.licenses.operator;

import java.util.ArrayList;

/**
 * License identified by its URI and related attributes.
 *
 * @author Adrian Wilke
 */
public class License {

	private Attributes attributes;

	public Attributes getAttributes() {
		return attributes;
	}

	/**
	 * Gets the attributes specified in configuration and maps the attributes to
	 * boolean values.
	 */
	public boolean[] mapToBoolean(Configuration configuration) {
		ArrayList<String> attributeUris = new ArrayList<String>(configuration.getAttributes().getMap().keySet());
		boolean[] bool = new boolean[attributeUris.size()];
		for (int i = 0; i < attributeUris.size(); i++) {
			bool[i] = attributes.getMap().get(attributeUris.get(i)).mapToBoolean();
		}
		return bool;
	}

	/**
	 * Gets URI identifying this license.
	 * 
	 * @throws NullPointerException if not set
	 */
	public String getUri() throws NullPointerException {
		// TODO Auto-generated method stub
		return null;
	}

	public License setAttributes(Attributes attributes) {
		this.attributes = attributes;
		return this;
	}

	/**
	 * Sets URI identifying this license.
	 * 
	 * @throws NullPointerException if given URI is null
	 */
	public License setUri(String uri) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public String toString() {
		return getUri();
	}
}