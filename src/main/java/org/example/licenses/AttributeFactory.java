package org.example.licenses;

/**
 * Creates attributes.
 * 
 * @author 33a1cc8d616a72f953d8e15274194bcd5aac2b78fbe6b4a4d1a911e0f2ef00cd
 */
public enum AttributeFactory {

	INSTANCE;

	public static AttributeFactory get() {
		return INSTANCE;
	}

	public Attribute createAttribute(String type) {
		if (type == null) {
			throw new NullPointerException("Type of attribute is null");
		} else if (type.equals(Permission.TYPE)) {
			return new Permission();
		} else if (type.equals(Prohibition.TYPE)) {
			return new Prohibition();
		} else if (type.equals(Requirement.TYPE)) {
			return new Requirement();
		} else {
			throw new RuntimeException("Unknown type: " + type);
		}
	}

	public Attribute createAttribute(String type, String uri) {
		if (uri == null) {
			throw new NullPointerException("URI of attribute is null");
		} else {
			return createAttribute(type).setUri(uri);
		}
	}

	public Attribute createAttribute(String type, String uri, Boolean value) {
		if (value == null) {
			throw new NullPointerException("Value of attribute is null");
		} else {
			return createAttribute(type, uri).setValue(value);
		}
	}

	public Attribute createAttribute(Attribute attribute, boolean includeValue) {
		if (attribute == null) {
			throw new NullPointerException("Attribute is null");
		} else {
			Attribute newAttribute = null;

			if (includeValue) {
				if (!attribute.hasValue()) {
					throw new RuntimeException("No value given: " + attribute.toString());
				} else {
					newAttribute = createAttribute(attribute.getType(), attribute.getUri(), attribute.getValue());
				}
			} else {
				newAttribute = createAttribute(attribute.getType(), attribute.getUri());
			}

			newAttribute.setIsTypePermissionOfDerivates(attribute.isTypePermissionOfDerivates());
			newAttribute.setIsTypeRequirementShareAlike(attribute.isTypeRequirementShareAlike());

			return newAttribute;
		}
	}
}