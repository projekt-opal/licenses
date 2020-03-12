package org.dice_research.opal.licenses.old.generator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.dice_research.opal.common.utilities.FileHandler;
import org.dice_research.opal.licenses.old.Constants;

/**
 * Licence knowledge graph.
 * 
 * @author Jochen Vothknecht
 * @author Adrian Wilke
 */
public class LicencesGraph {

	public static final String EDP_MATRIX = "edp-matrix.csv";

	/**
	 * Main entry point.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		LicencesGraph odrl = new LicencesGraph().parseCsv(new File(EDP_MATRIX));

		// Print model
		if (Boolean.TRUE) {
			odrl.model.write(System.out, "TURTLE");
		}

		// Write model to file
		if (Boolean.FALSE) {
			try {
				File outFile = File.createTempFile("licences.", ".ttl");
				FileHandler.export(outFile, odrl.model);
				odrl.model.write(System.out, "TURTLE");
				System.out
						.println("Wrote model to: " + outFile.getAbsolutePath() + " " + LicencesGraph.class.getName());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	protected Model model;

	protected enum OptionalBoolean {
		UNDEFINED, FALSE, TRUE
	}

	/**
	 * Parses CSV file and creates {@link #model}.
	 */
	protected LicencesGraph parseCsv(File csvFile) {

		model = ModelFactory.createDefaultModel();
		model.setNsPrefix("opal", Constants.NS_OPAL_LICENSES);
		model.setNsPrefix("odrl", Constants.NS_ODRL);
		model.setNsPrefix("cc", Constants.NS_CC);

		try {
			Files.lines(csvFile.toPath()).forEach((String line) -> {
				String fields[] = line.split(",");

				// String licenseName = fields[0];
				String licenseURI = fields[1];

				OptionalBoolean bFields[] = new OptionalBoolean[fields.length - 2];

				for (int i = 2; i < fields.length; i++) {
					if ("Yes".equals(fields[i]))
						bFields[i - 2] = OptionalBoolean.TRUE;
					else if ("No".equals(fields[i]))
						bFields[i - 2] = OptionalBoolean.FALSE;
					else
						bFields[i - 2] = OptionalBoolean.UNDEFINED;
				}

				// Permissions
				OptionalBoolean reproduction = bFields[0];
				OptionalBoolean distribution = bFields[1];
				OptionalBoolean derivative = bFields[2];
				// OptionalBoolean sublicensing = bFields[3]; // not supported by ODRL / CC-REL
				// OptionalBoolean patentGrant = bFields[4]; // not supported by ODRL / CC-REL

				// Requirements
				OptionalBoolean notice = bFields[5]; // CC-REL only
				OptionalBoolean attribution = bFields[6]; // CC-REL only
				OptionalBoolean shareAlike = bFields[7]; // CC-REL only
				OptionalBoolean copyleft = bFields[8]; // CC-REL only
				OptionalBoolean lesserCopyleft = bFields[9]; // CC-REL only
				// OptionalBoolean stateChanges = bFields[10]; // not supported by CC-REL / ODRL

				// Prohibitions
				OptionalBoolean commercialUse = bFields[11]; // CC-REL only
				// OptionalBoolean useTrademark = bFields[12]; // not supported by CC-REL

				Resource license = model.createResource(licenseURI);
				license.addProperty(RDF.type, model.createResource(Constants.NS_ODRL + "policy"));

				Property permission = model.createProperty(Constants.NS_ODRL + "permission");
				Resource permissions = model.createResource();
				license.addProperty(permission, permissions);

				Property requirement = model.createProperty(Constants.NS_ODRL + "requirement");
				Resource requirements = model.createResource();
				license.addProperty(requirement, requirements);

				Property prohibition = model.createProperty(Constants.NS_ODRL + "prohibition");
				Resource prohibitions = model.createResource();
				license.addProperty(prohibition, prohibitions);

				Property action = model.createProperty(Constants.NS_ODRL + "action");

				if (reproduction == OptionalBoolean.TRUE)
					permissions.addProperty(action, model.createProperty(Constants.NS_ODRL + "reproduce"));
				if (distribution == OptionalBoolean.TRUE)
					permissions.addProperty(action, model.createProperty(Constants.NS_ODRL + "distribute"));
				if (derivative == OptionalBoolean.TRUE)
					permissions.addProperty(action, model.createProperty(Constants.NS_ODRL + "derive"));

				if (notice == OptionalBoolean.TRUE)
					requirements.addProperty(action, model.createProperty(Constants.NS_CC + "Notice"));
				if (attribution == OptionalBoolean.TRUE)
					requirements.addProperty(action, model.createProperty(Constants.NS_CC + "Attribution"));
				if (shareAlike == OptionalBoolean.TRUE)
					requirements.addProperty(action, model.createProperty(Constants.NS_CC + "ShareAlike"));
				if (copyleft == OptionalBoolean.TRUE)
					requirements.addProperty(action, model.createProperty(Constants.NS_CC + "Copyleft"));
				if (lesserCopyleft == OptionalBoolean.TRUE)
					requirements.addProperty(action, model.createProperty(Constants.NS_CC + "LesserCopyleft"));

				if (commercialUse == OptionalBoolean.TRUE)
					prohibitions.addProperty(action, model.createProperty(Constants.NS_CC + "CommercialUse"));

			});
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return this;
	}
}