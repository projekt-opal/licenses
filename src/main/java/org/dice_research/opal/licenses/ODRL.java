package org.dice_research.opal.licenses;

import java.io.File;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;

import org.apache.jena.rdf.model.Bag;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;


public class ODRL {
	
	public static final Model model;
	
	private enum OptionalBoolean {
		UNDEFINED, FALSE, TRUE
	}
	
	static {
		model = ModelFactory.createDefaultModel();
		
		model.setNsPrefix("opal", Strings.NS_OPAL_LICENSES);
		model.setNsPrefix("odrl", Strings.NS_ODRL);
		model.setNsPrefix("cc", Strings.NS_CC);
		// model.setNsPrefix("rdf", Strings.NS_RDF);		
		
		try {
			//model.read(new FileInputStream(new File("src/main/resources/ODRL22.ttl")), null, "TURTLE");

			Files.lines(new File("edp-matrix.csv").toPath()).forEach((String line) -> {
				String fields[] = line.split(",");
				
				String licenseName = fields[0];
				String licenseURI = fields[1];
				
				OptionalBoolean bFields[] = new OptionalBoolean[fields.length - 2];
				
				for (int i = 2; i < fields.length; i++) {
					if ("Yes".equals(fields[i])) bFields[i - 2] = OptionalBoolean.TRUE;
					else if ("No".equals(fields[i])) bFields[i - 2] = OptionalBoolean.FALSE;
					else bFields[i - 2] = OptionalBoolean.UNDEFINED;
				}
				
				// Permissions
				OptionalBoolean reproduction = bFields[0];
				OptionalBoolean distribution = bFields[1];
				OptionalBoolean derivative = bFields[2];
				OptionalBoolean sublicensing = bFields[3];  // not supported by ODRL / CC-REL
				OptionalBoolean patentGrant = bFields[4];  // not supported by ODRL / CC-REL
				
				// Requirements
				OptionalBoolean notice = bFields[5];  // CC-REL only
				OptionalBoolean attribution = bFields[6];
				OptionalBoolean shareAlike = bFields[7];
				OptionalBoolean copyleft = bFields[8];
				OptionalBoolean lesserCopyleft = bFields[9];
				// OptionalBoolean stateChanges = bFields[10];  // not supported by CC-REL / ODRL
				
				// Prohibitions
				OptionalBoolean commercialUse = bFields[11];
				// OptionalBoolean useTrademark = bFields[12];  // not supported by CC-REL
				
				// ODRL.addTriple(model, licenseURI, Strings.NS_RDF + "type", Strings.NS_ODRL + "policy");
				Resource license = model.createResource(licenseURI);
				license.addProperty(RDF.type, model.createResource(Strings.NS_ODRL + "policy"));
				
				Property permission = model.createProperty(Strings.NS_ODRL + "permission");				
				Resource permissions = model.createResource();
				license.addProperty(permission, permissions);

				Property requirement = model.createProperty(Strings.NS_ODRL + "requirement");				
				Resource requirements = model.createResource();
				license.addProperty(requirement, requirements);

				Property prohibition = model.createProperty(Strings.NS_ODRL + "prohibition");				
				Resource prohibitions = model.createResource();
				license.addProperty(prohibition, prohibitions);

				Property action = model.createProperty(Strings.NS_ODRL + "action");
				
				if (reproduction == OptionalBoolean.TRUE) permissions.addProperty(action, model.createProperty(Strings.NS_ODRL + "reproduce"));
				if (distribution == OptionalBoolean.TRUE) permissions.addProperty(action, model.createProperty(Strings.NS_ODRL + "distribute"));
				if (derivative == OptionalBoolean.TRUE) permissions.addProperty(action, model.createProperty(Strings.NS_ODRL + "derive"));
				
				if (notice == OptionalBoolean.TRUE) requirements.addProperty(action, model.createProperty(Strings.NS_CC + "Notice"));
				if (attribution == OptionalBoolean.TRUE) requirements.addProperty(action, model.createProperty(Strings.NS_CC + "Attribution"));
				if (shareAlike == OptionalBoolean.TRUE) requirements.addProperty(action, model.createProperty(Strings.NS_CC + "ShareAlike"));
				if (copyleft == OptionalBoolean.TRUE) requirements.addProperty(action, model.createProperty(Strings.NS_CC + "Copyleft"));
				if (lesserCopyleft == OptionalBoolean.TRUE) requirements.addProperty(action, model.createProperty(Strings.NS_CC + "LesserCopyleft"));

				if (commercialUse == OptionalBoolean.TRUE) prohibitions.addProperty(action, model.createProperty(Strings.NS_CC + "CommercialUse"));

			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//try {
			// model.write(new FileOutputStream(new File("licenses.ttl")), "TURTLE");
			model.write(System.out, "TURTLE");
		//} catch (FileNotFoundException e) {
		//	e.printStackTrace();
		//}
	}
}
