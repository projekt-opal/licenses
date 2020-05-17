package org.example.licenses.transform;

import java.io.IOException;

import org.example.licenses.License;
import org.example.licenses.edplcm.EdpLcmKnowledgeBase;

/**
 * Creates constants for URIs.
 * 
 * @author 33a1cc8d616a72f953d8e15274194bcd5aac2b78fbe6b4a4d1a911e0f2ef00cd
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