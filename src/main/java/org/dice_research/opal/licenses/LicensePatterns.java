package org.dice_research.opal.licenses;

import java.io.FileOutputStream;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Selector;
import org.apache.jena.rdf.model.SimpleSelector;


/**
 * 
 * Manager of a RDF Model which stores regexes to unify license URIs
 * 
 * @author Jochen Vothknecht
 */
public class LicensePatterns {
	
	public static Collection<LicenseReplacer> replacers;
	
	static {
		LicensePatterns.replacers = new LinkedList<>();
		
		Model m = LicensePatterns.getModel();
		
		Property plpat = m.createProperty("opal", ":licensePattern");
		Property prpat = m.createProperty("opal", ":licenseReplacement");

		ResIterator iterator = m.listResourcesWithProperty(plpat);
		while (iterator.hasNext()) {
			Resource res = iterator.next();
			
			Selector sel = new SimpleSelector(res, (Property)null, (RDFNode)null);
			Model tmp = m.query(sel);

			NodeIterator it = tmp.listObjectsOfProperty(plpat);
			String pattern = it.next().toString();

			it = tmp.listObjectsOfProperty(prpat);
			String replace = it.next().toString();
			
			LicensePatterns.replacers.add(new LicenseReplacer(pattern, replace));
		}
		
	}
	
	public static String replace(final String license) {
		String out = license;
		
		for (LicenseReplacer licRep : LicensePatterns.replacers) {
			out = licRep.replace(out);
		}
		
		return out;
	}
	
	public static Model getModel() {
		Model m = ModelFactory.createDefaultModel();
		m.read("LicensePatterns.ttl");
		return m;
	}
	
	public static void addPatternToModel(Model m, String name, String pattern, String replacement) {
		Resource rpat = m.createResource(Strings.NS_OPAL_LICENSES + name);
		Property plpat = m.createProperty("opal", ":licensePattern");
		Property prpat = m.createProperty("opal", ":licenseReplacement");
		Literal lpat = m.createLiteral(pattern);
		Literal lrep = m.createLiteral(replacement);
		
		m.add(m.createStatement(rpat, plpat, lpat));
		m.add(m.createStatement(rpat, prpat, lrep));
	}

	public static void main(String[] args) {
		Model m = ModelFactory.createDefaultModel();
		m.setNsPrefix("opal", Strings.NS_OPAL_LICENSES);
		
		
		LicensePatterns.addPatternToModel(m,
				"europeandataportal",
				"http[s]?://(?:www\\.)?europeandataportal\\.eu/content/show-license\\?license_id=(.*)",
				"http://europeandataportal.eu/content/show-license?license_id=$1");
		
		
		
		
		try {
			FileOutputStream fos = new FileOutputStream("LicensePatterns.ttl");
			m.write(fos, "TURTLE");
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
