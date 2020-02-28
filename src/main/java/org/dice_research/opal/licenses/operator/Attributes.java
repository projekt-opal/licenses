package org.dice_research.opal.licenses.operator;

import java.util.List;

/**
 * Attributes.
 *
 * @author Adrian Wilke
 */
public class Attributes {

	public Attributes addAttribute(Attribute attribute) {
		// General way of adding per/pro/req
		// TODO Auto-generated method stub
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

	public List<Attribute> getAttributes() {
		// TODO
		return null;
	}

	public List<Permission> getPermissions() {
		// TODO
		return null;
	}

	public List<Prohibition> getProhibitions() {
		// TODO
		return null;
	}

	public List<Requirement> getRequirements() {
		// TODO
		return null;
	}

}