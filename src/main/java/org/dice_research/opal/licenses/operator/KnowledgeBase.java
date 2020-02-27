package org.dice_research.opal.licenses.operator;

import java.util.List;

/**
 * TODO: Obligation/Duty https://w3c.github.io/odrl/bp/ §2 How to represent an
 * obligation
 * 
 * TODO: getters
 *
 * @author Adrian Wilke
 */
public class KnowledgeBase {

	/**
	 * @see https://w3c.github.io/odrl/bp/ §1 How to Represent a General Permission
	 */
	public KnowledgeBase addPermission(String uri) {
		// TODO: Implement
		return this;
	}

	/**
	 * @see https://w3c.github.io/odrl/bp/ §3 How to represent a prohibition
	 */
	public KnowledgeBase addProhibition(String uri) {
		// TODO: Implement
		return this;
	}

	List<Permission> getPermissions() {
		// TODO
		return null;
	}

	List<Permission> getProhibitions() {
		// TODO
		return null;
	}

}