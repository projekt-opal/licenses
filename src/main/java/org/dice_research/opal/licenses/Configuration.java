package org.dice_research.opal.licenses;

/**
 * A configuration comprises a list of attributes, which is used for
 * computations and a knowledge base containing license information.
 *
 * @author Adrian Wilke
 */
public class Configuration {

	private Attributes attributes = new Attributes();

	public Configuration addAttribute(Attribute attribute) {
		attributes.addAttribute(attribute);
		return this;
	}

	public Attributes getAttributes() {
		return attributes;
	}

}