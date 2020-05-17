package org.example.licenses.transform;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.example.licenses.Attribute;
import org.example.licenses.License;
import org.example.licenses.edplcm.EdpLcmKnowledgeBase;
import org.example.licenses.edplcm.EpdLcmDerivates;

/**
 * Loads data of "European Data Portal Licence Compatibility Matrix". Extracts
 * all allowed derivates for licenses with share-alike attribute. Creates
 * resource file {@value #OUTPUT_FILE}.
 * 
 * Run with {@link #main(String[])}.
 * 
 * @author Adrian Wilke
 */
public class EdpLcmShareAlike {

	public static final String URI_SHARE_ALIKE = "http://example.org/Share-Alike";

	public static final String DIR_RESOURCES = "src/main/resources/";
	public static final String OUTPUT_FILE = "edp-licence-compatibility-matrix-share-alike.txt";

	public static void main(String[] args) throws IOException {

		// Get license and attributes data
		EdpLcmKnowledgeBase knowledgeBase = new EdpLcmKnowledgeBase();
		knowledgeBase.load();

		// Map license names to URIs
		Map<String, String> namesToUris = new HashMap<>();
		for (License license : knowledgeBase.getLicenses()) {
			namesToUris.put(license.getName(), knowledgeBase.getUriForName(license.getName()));
		}

		// Extract licenses with share-alike attribute
		List<License> shareAlikeLicenses = new LinkedList<>();
		for (License license : knowledgeBase.getLicenses()) {
			for (Attribute attribute : license.getAttributes().getList()) {
				if (attribute.getUri().equals(URI_SHARE_ALIKE) && attribute.getValue()) {
					shareAlikeLicenses.add(license);
				}
			}
		}

		// Get allowed derivates for share-alike licenses
		Map<License, List<String>> allowedDerivates = new HashMap<>();
		EpdLcmDerivates derivates = new EpdLcmDerivates();

		for (License license : shareAlikeLicenses) {
			String uri = knowledgeBase.getUriForName(license.getName());
			allowedDerivates.put(license, derivates.getCompatibleUris(uri));
		}

		// Serialize
		StringBuilder stringBuilder = new StringBuilder();
		for (Entry<License, List<String>> entry : allowedDerivates.entrySet()) {
			stringBuilder.append(entry.getKey().getUri());
			stringBuilder.append(System.lineSeparator());
			for (String licenseUri : entry.getValue()) {
				stringBuilder.append(licenseUri);
				stringBuilder.append(System.lineSeparator());
			}
			stringBuilder.append(System.lineSeparator());
		}

		// Write
		File file = new File(DIR_RESOURCES, OUTPUT_FILE);
		FileUtils.write(file, stringBuilder.toString(), StandardCharsets.UTF_8);
		System.out.println("Wrote: " + file.getAbsolutePath());
	}

}