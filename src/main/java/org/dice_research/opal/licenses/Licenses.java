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
		
	private Statement mapLicenses(Model m, Statement stmt) {
		Triple t = stmt.asTriple();
		String license = t.getObject().toString();
		
		license = LicensePatterns.replace(license);
		
		Resource res = m.createResource(t.getSubject().toString());
		Property prop = m.createProperty(t.getPredicate().toString());
		
		return m.createStatement(res, prop, license);
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