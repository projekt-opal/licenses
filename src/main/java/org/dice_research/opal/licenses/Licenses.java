package org.dice_research.opal.licenses;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.jena.graph.Triple;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.dice_research.opal.licenses.exceptions.LicensesException;

public class Licenses {
	
	private static Pattern europeandataportalPattern = Pattern.compile("http[s]?://(?:www\\.)?europeandataportal\\.eu/content/show-license\\?license_id=(.*)");
	private static String europeandataportalBaseURI = "http://europeandataportal.eu/content/show-license?license_id=";
	private String europeandataportalEUHandler(String license) {
		Matcher m = Licenses.europeandataportalPattern.matcher(license);
		
		if (m.matches()) {
			return Licenses.europeandataportalBaseURI + m.group(1);
		} else {
			return null;
		}
	}
	
	private Statement mapLicenses(Model m, Statement stmt) {
		Triple t = stmt.asTriple();
		String license = t.getObject().toString();
		
		// Just for testing! Will deploy some kind of handler registry later!
		String handled = europeandataportalEUHandler(license);
		
		if (handled != null) {
			Resource res = m.createResource(t.getSubject().toString());
			Property prop = m.createProperty(t.getPredicate().toString());
			
			return m.createStatement(res, prop, handled);
		} else {
			return stmt;
		}
	}

	public Model process(Model model) throws LicensesException {
		Model returnModel = ModelFactory.createDefaultModel();
		
		StmtIterator stmts = model.listStatements();
		
		while (stmts.hasNext()) {
			Statement stmt = stmts.next();
			
			if (Strings.DCT_LICENSE.equals(stmt.asTriple().getPredicate().toString())) stmt = mapLicenses(returnModel, stmt);
			returnModel.add(stmt);
		}
		
		return returnModel;
	}

}