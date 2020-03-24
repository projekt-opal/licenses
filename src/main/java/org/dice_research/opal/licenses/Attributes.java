package org.dice_research.opal.licenses;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Attributes management.
 *
 * @author Adrian Wilke
 */
public class Attributes {

	public boolean isShareAlike() {
		for (Attribute attribute : getList()) {
			if (attribute.isTypeRequirementShareAlike() && attribute.getValue()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Insertion-ordered map: Attribute-URI to Attribute.
	 */
	private LinkedHashMap<String, Attribute> attributes = new LinkedHashMap<>();

	/**
	 * Adds attribute to index.
	 */
	public Attributes addAttribute(Attribute attribute) {
		attributes.put(attribute.getUri(), attribute);
		return this;
	}

	/**
	 * Gets attribute from index.
	 */
	public Attribute getAttribute(String uri) {
		return attributes.get(uri);
	}

	/**
	 * TODO: old implementation including meta attributes
	 * 
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
	 * Gets array of internal attribute values for computation. (E.g. values of
	 * permissions are inverted.)
	 * 
	 * Does not include special attributes definded in {@link MetaAttribute}.
	 */
	public boolean[] getInternalValuesArrayNew() {
		// Sort attributes to maintain same order for each license
		List<Attribute> attributesList = getList();
		attributesList.sort(new AttributeComparator());

		// Get non-meta attributes
		List<Boolean> values = new LinkedList<>();
		for (Attribute attribute : attributesList) {
			if (!attribute.isMetaAttribute()) {
				values.add(attribute.getInternalValue());
			}
		}

		// Put values to array
		boolean[] array = new boolean[values.size()];
		int counter = 0;
		for (Boolean value : values) {
			array[counter++] = value;
		}
		return array;
	}

	/**
	 * Gets list of containing attributes.
	 */
	public List<Attribute> getList() {
		return new ArrayList<Attribute>(this.attributes.values());
	}

	/**
	 * Gets URIs of attributes (keys of index)
	 */
	public Set<String> getUris() {
		return this.attributes.keySet();
	}

	/**
	 * Gets index.
	 */
	public LinkedHashMap<String, Attribute> getUriToAttributeMap() {
		return this.attributes;
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
	 * Sorts attributes by types and URIs.
	 */
	public Attributes sort() {
		List<Attribute> sortedAttributes = getList();
		sortedAttributes.sort(new AttributeComparator());
		attributes = new LinkedHashMap<>();
		for (Attribute attribute : sortedAttributes) {
			attributes.put(attribute.getUri(), attribute);
		}
		return this;
	}

	/**
	 * Gets multiline string of containing attributes.
	 */
	public String toLines() {
		StringBuilder stringBuilder = new StringBuilder();
		for (Attribute attribute : getList()) {
			stringBuilder.append(attribute);
			stringBuilder.append(System.lineSeparator());
		}
		return stringBuilder.toString();
	}

	/**
	 * Gets license URIs (keys of index).
	 */
	@Override
	public String toString() {
		return attributes.keySet().toString();
	}
}