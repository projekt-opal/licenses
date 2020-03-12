package org.dice_research.opal.licenses.edplcm;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;

import org.dice_research.opal.licenses.Attribute;
import org.dice_research.opal.licenses.License;
import org.dice_research.opal.licenses.Operator;
import org.dice_research.opal.licenses.Permission;
import org.dice_research.opal.licenses.Prohibition;
import org.dice_research.opal.licenses.Requirement;

/**
 * Reads compatibility information of the "European Data Portal Licence
 * Compatibility Matrix".
 * 
 * Usage: Use {@link #getUris()} and {@link #getValue(String, String)}.
 * 
 * @author Adrian Wilke
 */
public class EdpLcmExperiment {

	public static final String RESOURCE_CSV = "edp-licence-compatibility-matrix-simple-derivative.csv";
	protected Vector<Vector<Boolean>> matrix = new Vector<>();
	protected LinkedHashMap<String, Integer> uris = new LinkedHashMap<>();
	protected boolean isLoaded = false;
	protected EpdLcmDerivates derivates = new EpdLcmDerivates();

	// TODO: Remove when completed
	public static void main(String[] args) throws IOException {
//		new EdpExperimentCompatibility().runExperiment();
		new EdpLcmExperiment().checkEdpMatrix();
	}

	protected void checkEdpMatrix() throws IOException {

		// TODO
		// Current state: There are special attributes, like "derivatesAllowed" which
		// can not be handeled like other attributes.
		// Attribute "derivatesAllowed" was included into the License class.
		// -> OK
		//
		// Other attributes have to be checked (e.g. share alike)
		// Problem: here are lots of cases:
		// https://creativecommons.org/share-your-work/licensing-considerations/compatible-licenses

		EdpLcmKnowledgeBase kb = new EdpLcmKnowledgeBase();
		kb.load();

		for (License license : kb.getLicenses()) {
			List<String> compatible = derivates.getCompatibleUris(license.getUri());
			List<License> matching = kb.getMatchingLicenses(license);
			System.out.print(license.getName());
			if (license.shareAlike) {
				System.out.print("  shareAlike");
			}
			if (!license.derivatesAllowed) {
				System.out.print("  noDerivates");
			}
			System.out.println();
			System.out.println(license.getUri());
			System.out.println("Comp (EDP): " + compatible);
			System.out.println("Match     : " + matching.stream().map(l -> l.getUri()).collect(Collectors.toList()));
			System.out.println();
		}

	}

	protected void runExperiment() throws IOException {
		EdpLcmKnowledgeBase kb = new EdpLcmKnowledgeBase();

		StringBuilder stringBuilder = new StringBuilder();

		for (Attribute attribute : kb.getAttributes().getObjects()) {
			System.out.println(attribute.getClass().getSimpleName());
			System.out.println(attribute.getUri());
			System.out.println();
		}

		for (String uriLicenseA : derivates.getUris()) {
			License licenseA = kb.getUrisToLicenses().get(uriLicenseA);
			for (String uriLicenseB : derivates.getUris()) {
				License licenseB = kb.getUrisToLicenses().get(uriLicenseB);
				boolean[] result = new Operator().compute(licenseA.getAttributes().getInternalArray(),
						licenseB.getAttributes().getInternalArray());
				List<License> resultingLicenses = kb.getMatchingLicenses(result);
				addResult(stringBuilder, licenseA, licenseB, result, resultingLicenses);
			}
		}

		// TODO: Investigate

		System.out.println(stringBuilder);
	}

	protected void addResult(StringBuilder stringBuilder, License licenseA, License licenseB, boolean[] result,
			List<License> resultingLicenses) throws NullPointerException, IOException {

		for (Attribute license : licenseA.getAttributes().getObjects()) {
			if (license instanceof Permission) {
				stringBuilder.append("Per ");
			} else if (license instanceof Prohibition) {
				stringBuilder.append("Pro ");

			} else if (license instanceof Requirement) {
				stringBuilder.append("Req ");
			}
		}
		stringBuilder.append(System.lineSeparator());

		stringBuilder.append(licenseA.getName());
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append(Arrays.toString(licenseA.getAttributes().getInternalArray()));
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append(licenseB.getName());
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append(Arrays.toString(licenseB.getAttributes().getInternalArray()));
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append("Result");
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append(Arrays.toString(result));
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append("Result (");
		stringBuilder.append(resultingLicenses.size());
		stringBuilder.append("): ");
		stringBuilder.append(resultingLicenses);
		stringBuilder.append(System.lineSeparator());

		// See https://www.baeldung.com/java-lists-intersection
		Set<String> compatibleUris = derivates.getCompatibleUris(licenseA.getUri()).stream().distinct()
				.filter(derivates.getCompatibleUris(licenseB.getUri())::contains).collect(Collectors.toSet());

		stringBuilder.append("Compatible (");
		stringBuilder.append(compatibleUris.size());
		stringBuilder.append("): ");
		stringBuilder.append(compatibleUris);
		stringBuilder.append(System.lineSeparator());

		Set<String> resultingUris = new HashSet<>();
		for (License license : resultingLicenses) {
			resultingUris.add(license.getUri());
		}

		if (compatibleUris.equals(resultingUris)) {
			stringBuilder.append("ok");
		} else {
			stringBuilder.append("fail");
		}

		// TODO: Instead of the following test, directly get the intersection of
		// compatible
		// licenses of A and B.
//		if ((Boolean.FALSE && resultingLicenses.contains(licenseA)) && resultingLicenses.contains(licenseB)) {
//			stringBuilder.append("ok");
//		} else {
//			stringBuilder.append("fail");
//		}
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append(System.lineSeparator());
	}

}