package org.dice_research.opal.licenses.operator;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Attributes.
 *
 * @author Adrian Wilke
 */
public class Attributes {

	/**
	 * Gets array of attribute values.
	 */
	public boolean[] getArray() {
		boolean[] array = new boolean[attributes.size()];
		int counter = 0;
		for (Attribute attribute : attributes.values()) {
			array[counter++] = attribute.getValue();
		}
		return array;
	}

	/**
	 * Insertion-ordered map: Attribute-URI to Attribute.
	 */
	private Map<String, Attribute> attributes = new LinkedHashMap<>();

	public Attributes addAttribute(Attribute attribute) {
		attributes.put(attribute.getUri(), attribute);
		return this;
	}

	public Map<String, Attribute> getMap() {
		return this.attributes;
	}

	@Override
	public String toString() {
		return attributes.keySet().toString();
	}
}