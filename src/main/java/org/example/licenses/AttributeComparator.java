package org.example.licenses;

import java.util.Comparator;

/**
 * Compares attributes based on their types and URIs.
 * 
 * @author 33a1cc8d616a72f953d8e15274194bcd5aac2b78fbe6b4a4d1a911e0f2ef00cd
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