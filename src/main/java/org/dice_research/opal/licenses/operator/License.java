package org.dice_research.opal.licenses.operator;

import java.util.ArrayList;

/**
 * License identified by its URI and related attributes.
 *
 * @author Adrian Wilke
 */
public class License {

	private Attributes attributes;
	private String name;
	private String uri;

	public Attributes getAttributes() {
		return attributes;
	}

	/**
	 * Gets name of license.
	 * 
	 * @throws NullPointerException if not set
	 */
	public String getName() {
		if (name == null) {
			throw new NullPointerException();
		}
		return name;
	}

	/**
	 * Gets URI identifying this license.
	 * 
	 * @throws NullPointerException if not set
	 */
	public String getUri() throws NullPointerException {
		if (uri == null) {
			throw new NullPointerException();
		}
		return uri;
	}

	/**
	 * Gets the attributes specified in configuration and maps the attributes to
	 * boolean values.
	 */
	public boolean[] mapToBoolean(Configuration configuration) {
		ArrayList<String> attributeUris = new ArrayList<String>(configuration.getAttributes().getUris());
		boolean[] bool = new boolean[attributeUris.size()];
		for (int i = 0; i < attributeUris.size(); i++) {
			bool[i] = attributes.getUriToAttributeMap().get(attributeUris.get(i)).mapToBoolean();
		}
		return bool;
	}

	public License setAttributes(Attributes attributes) {
		this.attributes = attributes;
		return this;
	}

	/**
	 * Sets name of license.
	 * 
	 * @throws NullPointerException if given URI is null
	 */
	public License setName(String name) {
		if (name == null) {
			throw new NullPointerException();
		}
		this.name = name;
		return this;
	}

	/**
	 * Sets URI identifying this license.
	 * 
	 * @throws NullPointerException if given URI is null
	 */
	public License setUri(String uri) {
		if (uri == null) {
			throw new NullPointerException();
		}
		this.uri = uri;
		return this;
	}

	@Override
	public String toString() {
		return getUri() + (name == null ? "" : " (" + name + ")");
	}
}