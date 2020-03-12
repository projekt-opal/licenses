package org.dice_research.opal.licenses.operator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * EDP knowledge base.
 * 
 * Does not include attribute 'Sublicensing' as it contains a 'N.A.' value.
 * 
 * Usage: Use {@link #getAttributes()} or {@link #getLicenses()}.
 * 
 * Based on "European Data Portal Licence Compatibility Matrix" and sheet
 * "Licence Descriptions".
 * 
 * Source file:
 * https://www.europeandataportal.eu/en/content/licence-assistant-european-data-portal-licence-compatibility-matrix
 * http://www.europeandataportal.eu/sites/default/files/edp-licence-compatibility-published_v1_0.xlsx
 *
 * @author Adrian Wilke
 */
public class EdpKnowledgeBase extends KnowledgeBase {

	private static final Logger LOGGER = LogManager.getLogger();

	public static final String RESOURCE_CSV = "edp-licence-compatibility-matrix-licence-descriptions.csv";
	public static final String URI_PREFIX = "http://example.org/";

	public static final String ATTRIBUTE_ID_SUBLICENSING = attributeIdToUri("Sublicensing");

	/**
	 * Config: Use IDs as URIs (true) or use KB URIs (false)
	 */
	public boolean useIdsAsUris = false;

	protected boolean isLoaded = false;
	protected boolean skipSublicensing = true;

	@Override
	public Attributes getAttributes() {
		if (!isLoaded) {
			try {
				load();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return super.getAttributes();
	}

	@Override
	public Map<String, License> getLicenses() {
		if (!isLoaded) {
			try {
				load();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return super.getLicenses();
	}

	protected void load() throws IOException {
		List<String> attributeUris = new LinkedList<>();
		boolean idsParsed = false;
		boolean typesParsed = false;

		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(RESOURCE_CSV);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		Iterable<CSVRecord> csvRecords = CSVFormat.DEFAULT.parse(bufferedReader);
		for (CSVRecord csvRecord : csvRecords) {

			// First line: Collect IDs
			if (!idsParsed) {
				for (int i = 0; i < csvRecord.size() - 2; i++) {
					attributeUris.add(attributeIdToUri(csvRecord.get(i)));
				}
				idsParsed = true;
			}

			// Second line: Create attributes
			else if (!typesParsed) {
				for (int i = 0; i < csvRecord.size() - 2; i++) {
					if (skipSublicensing && attributeUris.get(i).equals(ATTRIBUTE_ID_SUBLICENSING)) {
						continue;
					}
					addAttribute(createAttribute(attributeUris.get(i), csvRecord.get(i)));
				}
				typesParsed = true;
			}

			// Values
			else {
				Attributes attributes = new Attributes();
				for (int i = 0; i < csvRecord.size() - 2; i++) {
					// Create new attribute based on KB
					if (skipSublicensing && attributeUris.get(i).equals(ATTRIBUTE_ID_SUBLICENSING)) {
						continue;
					}
					Attribute attribute = createAttribute(
							super.getAttributes().getUriToAttributeMap().get(attributeUris.get(i)));
					try {
						addAttributeValue(attribute, csvRecord.get(i));
					} catch (IllegalArgumentException e) {
						// Ignore attribute with value '-1'
						LOGGER.warn(e);
						continue;
					}
					attributes.addAttribute(attribute);
				}

				String uri = null;
				if (useIdsAsUris) {
					uri = attributeIdToUri(csvRecord.get(csvRecord.size() - 1));
				} else {
					uri = csvRecord.get(csvRecord.size() - 2);
				}

				addLicense(new License().setUri(uri).setName(csvRecord.get(csvRecord.size() - 1))
						.setAttributes(attributes));
			}
		}

		isLoaded = true;
	}

	/**
	 * Creates Attribute based on type.
	 * 
	 * @throws IllegalArgumentException if the given type is unknown
	 */
	protected Attribute createAttribute(String uri, String type) {
		Attribute attribute;
		if (type.equals(Permission.TYPE)) {
			attribute = new Permission();
		} else if (type.equals(Prohibition.TYPE)) {
			attribute = new Prohibition();
		} else if (type.equals(Requirement.TYPE)) {
			attribute = new Requirement();
		} else {
			throw new IllegalArgumentException("Unkown type: " + type + ", URI: " + uri);
		}
		return attribute.setUri(uri);
	}

	/**
	 * Creates new attribute instance based on given attribute.
	 * 
	 * @throws IllegalArgumentException if the given type is unknown
	 */
	protected Attribute createAttribute(Attribute attribute) {
		Attribute newAttribute;
		if (attribute instanceof Permission) {
			newAttribute = new Permission();
		} else if (attribute instanceof Prohibition) {
			newAttribute = new Prohibition();
		} else if (attribute instanceof Requirement) {
			newAttribute = new Requirement();
		} else {
			throw new IllegalArgumentException("Unkown type: " + attribute);
		}
		if (attribute.hasValue()) {
			newAttribute.setValue(attribute.getValue());
		}
		return newAttribute.setUri(attribute.getUri());
	}

	/**
	 * Checks if value is 0 or 1 and adds value to attribute.
	 * 
	 * @throws IllegalArgumentException if value could not be parsed
	 */
	protected void addAttributeValue(Attribute attribute, String value) {
		if (!value.equals("0") && !value.equals("1")) {
			// E.g. if value is -1
			throw new IllegalArgumentException("Value is " + value + ", " + attribute);
		} else {
			attribute.setValue(value.equals("0") ? false : true);
		}
	}

	/**
	 * Creates URI from id.
	 */
	public static String attributeIdToUri(String id) {
		try {
			return new URI(URI_PREFIX + id.replaceAll(" ", "-")).toString();
		} catch (URISyntaxException e) {
			LOGGER.warn(e);
			return id;
		}
	}

}