package org.dice_research.opal.licenses;

import java.util.Comparator;

/**
 * Compares attributes based on their types and URIs.
 * 
 * @author Adrian Wilke
 */
public class AttributeComparator implements Comparator<Attribute> {

	@Override
	public int compare(Attribute a1, Attribute a2) {
		if (a1.isMetaAttribute() && !a2.isMetaAttribute()) {
			return 1;
		} else if (!a1.isMetaAttribute() && a2.isMetaAttribute()) {
			return -1;
		}
		int type = a1.getType().compareTo(a2.getType());
		if (type != 0) {
			return type;
		} else {
			return a1.getUri().compareTo(a2.getUri());
		}
	}
}