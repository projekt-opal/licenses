package org.dice_research.opal.licenses.transform;

import java.util.HashMap;
import java.util.Map;

import org.dice_research.opal.licenses.License;
import org.dice_research.opal.licenses.edplcm.EdpLcmKnowledgeBase;

public abstract class EdpLcmUriMapping {

	/**
	 * Maps license names to license URIs.
	 */
	public static Map<String, String> mapNamesToUris(EdpLcmKnowledgeBase edpLcmKnowledgeBase) {

		Map<String, String> namesToUris = new HashMap<>();
		for (License license : edpLcmKnowledgeBase.getLicenses()) {
			namesToUris.put(license.getName(), edpLcmKnowledgeBase.getUriForName(license.getName()));
		}
		return namesToUris;
	}

}