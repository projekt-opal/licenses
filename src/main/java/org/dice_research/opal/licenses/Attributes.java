package org.dice_research.opal.licenses;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 * Attributes.
 *
 * @author Adrian Wilke
 */
public class Attributes {

	/**
	 * Gets array of internal attribute values for computation. (E.g. values of
	 * permissions are inverted.)
	 */
	public boolean[] getInternalValuesArray() {
		boolean[] array = new boolean[attributes.size()];
		int counter = 0;
		for (Attribute attribute : attributes.values()) {
			array[counter++] = attribute.getInternalValue();
		}
		return array;
	}

	/**
	 * Gets array of original attribute values.
	 */
	public boolean[] getValuesArray() {
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
	private LinkedHashMap<String, Attribute> attributes = new LinkedHashMap<>();

	public Attributes addAttribute(Attribute attribute) {
		attributes.put(attribute.getUri(), attribute);
		return this;
	}

	public Attribute getAttribute(String uri) {
		return attributes.get(uri);
	}

	public LinkedHashMap<String, Attribute> getUriToAttributeMap() {
		return this.attributes;
	}

	public List<Attribute> getObjects() {
		return new ArrayList<Attribute>(this.attributes.values());
	}

	public Set<String> getUris() {
		return this.attributes.keySet();
	}

	@Override
	public String toString() {
		return attributes.keySet().toString();
	}

	public String toLines() {
		StringBuilder stringBuilder = new StringBuilder();
		for (Attribute attribute : getObjects()) {
			stringBuilder.append(attribute);
			stringBuilder.append(System.lineSeparator());
		}
		return stringBuilder.toString();
	}
}