package org.dice_research.opal.licenses;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;


public class CreativeCommonsRightsExpressionLanguage {
	
	public static final Model model;
	
	static {
		model = ModelFactory.createDefaultModel();
		
		try {
			model.read(new FileInputStream(new File("src/main/resources/cc.rdf")), null);

			Files.lines(new File("edp-matrix.csv").toPath()).forEach((String line) -> {
				String fields[] = line.split(",");
				
				String licenseName = fields[0];
				String licenseURI = fields[1];
				
				boolean bFields[] = new boolean[fields.length - 2];
				
				for (int i = 2; i < fields.length; i++) {
					if ("Yes".equals(fields[i])) bFields[i - 2] = true;
					else if ("No".equals(fields[i])) bFields[i - 2] = false;
					else throw new IllegalArgumentException("Field must bei either 'Yes' or 'No'");
				}
				
				// Permissions
				boolean reproduction = bFields[2];
				boolean distribution = bFields[3];
				boolean derivative = bFields[4];
				// boolean sublicensing = false;  // not supported by CC-REL
				// boolean patentGrant = false;  // not supported by CC-REL
				
				// Requirements
				boolean notice = bFields[7];
				boolean attribution = bFields[8];
				boolean shareAlike = bFields[9];
				// boolean sourceCode = false;  // not in input data
				boolean copyleft = bFields[10];
				boolean lesserCopyleft = bFields[11];
				// boolean stateChanges = false;  // not supported by CC-REL
				
				// Prohibitions
				boolean commercialUse = bFields[13];
				// boolean highIncomeNationUse = false;  // not in input data
				// boolean useTrademark = false;  // not supported by CC-REL
				
				
				
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(model);
	}
}
