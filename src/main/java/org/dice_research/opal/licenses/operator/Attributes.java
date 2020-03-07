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
	 * Insertion-ordered map: Attribute-URI to Attribute.
	 */
	private Map<String, Attribute> attributes = new LinkedHashMap<>();

	public Attributes addAttribute(Attribute attribute) {
		attributes.put(attribute.getUri(), attribute);
		return this;
	}

	/**
	 * @see https://w3c.github.io/odrl/bp/ §1 How to Represent a General Permission
	 */
	public Attributes addPermission(Permission attribute) {
		// TODO: Implement
		return this;
	}

	/**
	 * @see https://w3c.github.io/odrl/bp/ §3 How to represent a prohibition
	 */
	public Attributes addProhibition(Prohibition attribute) {
		// TODO: Implement
		return this;
	}

	/**
	 * @see https://w3c.github.io/odrl/bp/ §2 How to represent an obligation
	 * 
	 *      TODO: Obligation/Duty
	 */
	public Attributes addRequirement(Requirement attribute) {
		// TODO: Implement
		return this;
	}

	public Map<String, Attribute> getMap() {
		return this.attributes;
	}

	public List<Permission> getPermissions() {
		// TODO: Maybe not needed?
		return null;
	}

	public List<Prohibition> getProhibitions() {
		// TODO: Maybe not needed?
		return null;
	}

	public List<Requirement> getRequirements() {
		// TODO: Maybe not needed?
		return null;
	}

	@Override
	public String toString() {
		return attributes.keySet().toString();
	}
}