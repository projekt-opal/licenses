package org.dice_research.opal.licenses.transform;

import java.io.IOException;

import org.dice_research.opal.licenses.License;
import org.dice_research.opal.licenses.edplcm.EdpLcmKnowledgeBase;

/**
 * Creates constants for URIs.
 * 
 * @author Adrian Wilke
 */
public class EdpLcmCodeGen {

	public static final String PREFIX = "public static final String ";
	public static final String MIDFIX = " = \"";
	public static final String POSTFIX = "\";";

	public static final String CC0_1 = "https://creativecommons.org/publicdomain/zero/1.0/legalcode";

	public static void main(String[] args) throws IOException {
		EdpLcmKnowledgeBase knowledgeBase = new EdpLcmKnowledgeBase().load();
		for (License license : knowledgeBase.getLicenses()) {
			System.out.println(
					PREFIX + license.getName().replaceAll("[^A-Za-z0-9]", "_") + MIDFIX + license.getUri() + POSTFIX);
		}
	}

}