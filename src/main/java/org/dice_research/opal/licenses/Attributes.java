package org.dice_research.opal.licenses;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Attributes.
 *
 * @author Adrian Wilke
 */
public class Attributes {

	/**
	 * Gets array of attribute values for computation. (E.g. values of permissions
	 * are inverted.)
	 */
	public boolean[] getInternalArray() {
		boolean[] array = new boolean[attributes.size()];
		int counter = 0;
		for (Attribute attribute : attributes.values()) {
			array[counter++] = attribute.invertForComputation() ? !attribute.getValue() : attribute.getValue();
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

	public LinkedHashMap<String, Attribute> getUriToAttributeMap() {
		return this.attributes;
	}

	public Collection<Attribute> getObjects() {
		return this.attributes.values();
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