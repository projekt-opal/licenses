package org.dice_research.opal.licenses.edplcm;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.dice_research.opal.licenses.Attribute;
import org.dice_research.opal.licenses.AttributeFactory;
import org.dice_research.opal.licenses.KnowledgeBase;
import org.dice_research.opal.licenses.License;
import org.dice_research.opal.licenses.Permission;
import org.dice_research.opal.licenses.Prohibition;
import org.dice_research.opal.licenses.Requirement;


public class EdpOwlKnowledgeBase extends KnowledgeBase {
	
	public static final String OWL_FILE = "/home/fxk8y/git/OPAL/licenses/od-licenses.owl";

	public static final String PERMITS = "http://europeandataportal.eu/ontologies/copyright#permits";
	public static final String REQUIRES = "http://europeandataportal.eu/ontologies/copyright#requires";
	public static final String PROHIBITS = "http://europeandataportal.eu/ontologies/copyright#prohibits";
	
	private OntModel model = ModelFactory.createOntologyModel();
	
	private void addAttributes(String uri, String typ) {
		Property cp = model.createProperty(uri);
		
		NodeIterator ri = model.listObjectsOfProperty(cp);
		while (ri.hasNext()) {
			RDFNode node = ri.next();
			
			Attribute attr = AttributeFactory.get().createAttribute(typ, node.toString(), false);
			addAttribute(attr);
		}
	}
	
	public EdpOwlKnowledgeBase() {
		try {
			model.read(new FileInputStream(new File(OWL_FILE)), null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		addAttributes(PERMITS, Permission.TYPE);
		addAttributes(REQUIRES, Requirement.TYPE);
		addAttributes(PROHIBITS, Prohibition.TYPE);
		
		//Property cp = model.createProperty("copyright", "compatibleWith");
		
		//model.write(System.out, "N-TRIPLE");
		
		// Add licenses
		StmtIterator si = model.listStatements();
		while (si.hasNext()) {
			Statement stmt = si.next();
			
			if (stmt.getPredicate().toString().equals("http://europeandataportal.eu/ontologies/copyright#compatibleWith")) {
				String uri = stmt.getObject().toString();
				
				if (getLicense(uri) == null) {
					License l = new License();
					l.setUri(uri);
					addLicense(l);
				}
			}
		}
		
		// Add name to licenses
		// TODO: deduplicate!
		si = model.listStatements();
		while (si.hasNext()) {
			Statement stmt = si.next();
			
			if (stmt.getPredicate().toString().equals("http://europeandataportal.eu/ontologies/top#name")) {
				String uri = stmt.getSubject().toString();
				
				License l = getLicense(uri);
				if (l != null) {
					String name = stmt.getObject().toString();
					l.setName(name);
				}
			}
		}

		
		// Add attributes to licenses
		// TODO: deduplicate!
		getLicenses().forEach(license -> {
			StmtIterator _si = model.listStatements();

			Property cp = model.createProperty(PERMITS);
			
			while (_si.hasNext()) {
				Statement stmt = _si.next();

				if (stmt.getSubject().toString().equals(license.getUri())) {
					Attribute attr = AttributeFactory.get().createAttribute(Permission.TYPE, stmt.getObject().toString(), true);
					license.getAttributes().addAttribute(attr);
				}
			}

		
		
			cp = model.createProperty(REQUIRES);
			
			while (_si.hasNext()) {
				Statement stmt = _si.next();

				if (stmt.getSubject().toString().equals(license.getUri())) {
					Attribute attr = AttributeFactory.get().createAttribute(Requirement.TYPE, stmt.getObject().toString(), true);
					license.getAttributes().addAttribute(attr);
				}
			}

		
		
		
			cp = model.createProperty(PROHIBITS);
			
			while (_si.hasNext()) {
				Statement stmt = _si.next();

				if (stmt.getSubject().toString().equals(license.getUri())) {
					Attribute attr = AttributeFactory.get().createAttribute(Prohibition.TYPE, stmt.getObject().toString(), true);
					license.getAttributes().addAttribute(attr);
				}
			}
		
		});
	}
}
