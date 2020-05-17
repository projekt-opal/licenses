package org.example.licenses;

import java.text.ParseException;
import java.util.Objects;

/**
 * Abstract attribute to extend.
 * 
 * @see https://www.w3.org/TR/odrl-model/
 * @see https://www.w3.org/TR/odrl-vocab/
 * @see https://w3c.github.io/odrl/bp/
 *
 * @author 33a1cc8d616a72f953d8e15274194bcd5aac2b78fbe6b4a4d1a911e0f2ef00cd
 */
public abstract class Attribute extends MetaAttribute {

	/**
	 * Gets boolean representation of binary value.
	 * 
	 * @throws ParseException if not 0 or 1
	 */
	public static final boolean binaryToBoolean(int binary) throws ParseException {
		if (binary == 0) {
			return false;
		} else if (binary == 1) {
			return true;
		} else {
			throw new ParseException(Integer.toString(binary), 0);
		}
	}

	/**
	 * Gets binary representation of boolean value.
	 */
	public static final int booleanToBinary(boolean bool) {
		if (bool == false) {
			return 0;
		} else {
			return 1;
		}
	}

	private String uri = null;
	private Boolean value = null;

	/**
	 * Compares {@link Attribute} instances by URI.
	 */
	public int compareTo(Attribute o) {
		return getUri().compareTo(o.getUri());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			// Only obj is null
			return false;
		} else if (obj == this) {
			// Same instance
			return true;
		} else if (!(this.getClass().isInstance(obj))) {
			// Classes and subclasses
			return false;
		} else {
			// URI based
			return hashCode() == obj.hashCode();
		}
	}

	/**
	 * Maps attribute value to binary representation.
	 * 
	 * @throws NullPointerException if not set
	 */
	public int getBinaryValue() throws NullPointerException {
		if (getValue() == null) {
			throw new NullPointerException();
		} else {
			return booleanToBinary(getValue());
		}
	}

	/**
	 * Gets internal value for computation. (E.g. values of permissions are
	 * inverted.)
	 * 
	 * @throws NullPointerException if not set
	 */
	public Boolean getInternalValue() throws NullPointerException {
		if (value == null) {
			throw new NullPointerException();
		}
		return invertForComputation() ? !value : value;
	}

	/**
	 * Gets short representation of attribute.
	 */
	public abstract String getShortForm();

	/**
	 * Gets type of attribute.
	 */
	public abstract String getType();

	/**
	 * Gets URI identifying this attribute.
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
	 * Gets value of this attribute.
	 * 
	 * @throws NullPointerException if not set
	 */
	public Boolean getValue() throws NullPointerException {
		if (value == null) {
			throw new NullPointerException();
		}
		return value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(uri);
	}

	/**
	 * Checks, if the value is not null.
	 */
	public boolean hasValue() throws NullPointerException {
		return (value != null);
	}

	/**
	 * Gets type of attribute.
	 */
	public abstract boolean invertForComputation();

	/**
	 * Sets URI identifying this attribute.
	 * 
	 * @throws NullPointerException if given URI is null
	 */
	public Attribute setUri(String uri) {
		if (uri == null) {
			throw new NullPointerException();
		}
		this.uri = uri;
		return this;
	}

	/**
	 * Sets value of this attribute.
	 */
	public Attribute setValue(boolean value) {
		this.value = value;
		return this;
	}

	/**
	 * Parses binary value and returns instance.
	 * 
	 * @throws ParseException if not 0 or 1
	 */
	public Attribute setValue(int binary) throws ParseException {
		setValue(binaryToBoolean(binary));
		return this;
	}

	/**
	 * Sets value of by parsing the given internal value.
	 */
	public Attribute setValueByInternal(boolean internalValue) {
		this.value = invertForComputation() ? !internalValue : internalValue;
		return this;
	}

	@Override
	public String toString() {
		return getUri() + (value == null ? "" : "=" + value) + " (" + (isMetaAttribute() ? "Meta-" : "") + getType()
				+ ")";
	}

}