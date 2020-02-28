package org.dice_research.opal.licenses.operator;

import java.util.Objects;

/**
 * Abstract attribute to extend.
 * 
 * @see https://www.w3.org/TR/odrl-model/
 * @see https://www.w3.org/TR/odrl-vocab/
 * @see https://w3c.github.io/odrl/bp/
 *
 * @author Adrian Wilke
 */
public abstract class Attribute {

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
	public boolean getValue(boolean isSet) throws NullPointerException {
		if (value == null) {
			throw new NullPointerException();
		}
		return isSet;
	}

	@Override
	public int hashCode() {
		return Objects.hash(uri);
	}

	/**
	 * Maps attribute value to boolean representation.
	 * 
	 * @throws NullPointerException if not set
	 */
	public abstract boolean mapToBoolean() throws NullPointerException;

	/**
	 * Parses boolean value and returns instance.
	 */
	public abstract Attribute parseBoolean(boolean bool);

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

	@Override
	public String toString() {
		return getUri();
	}

}