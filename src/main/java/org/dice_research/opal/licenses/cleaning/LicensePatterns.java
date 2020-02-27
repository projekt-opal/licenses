package org.dice_research.opal.licenses.cleaning;

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
import org.dice_research.opal.licenses.Strings;


/**
 * 
 * Manager of a RDF Model which stores regexes to unify license URIs
 * 
 * @author Jochen Vothknecht
 */
public class LicensePatterns {
	
	/**
	 * The default list of LicenseReplacers
	 */
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
	
	/**
	 * Processes a given URI by the LicenseReplacers
	 * @param license the license URI to process
	 * @return the processed URI
	 */
	public static String replace(final String license) {
		String out = license;
		
		for (LicenseReplacer licRep : LicensePatterns.replacers) {
			out = licRep.replace(out);
		}
		
		return out;
	}
	
	/**
	 * Reads the patterns model from file
	 * @return the model
	 */
	public static Model getModel() {
		Model m = ModelFactory.createDefaultModel();
		m.read("LicensePatterns.ttl");
		return m;
	}
	
	/**
	 * Adds a URI pattern to specified model
	 * @param m the model
	 * @param name the name for the pattern
	 * @param pattern the regex
	 * @param replacement the replacement pattern
	 */
	public static void addPatternToModel(Model m, String name, String pattern, String replacement) {
		Resource rpat = m.createResource(Strings.NS_OPAL_LICENSES + name);
		Property plpat = m.createProperty(Strings.NS_OPAL_LICENSES + "licensePattern");
		Property prpat = m.createProperty(Strings.NS_OPAL_LICENSES + "licenseReplacement");
		Literal lpat = m.createLiteral(pattern);
		Literal lrep = m.createLiteral(replacement);
		
		m.add(m.createStatement(rpat, plpat, lpat));
		m.add(m.createStatement(rpat, prpat, lrep));
	}

	/**
	 * Creates a default model and writes it to disk
	 * @param args ignored
	 */
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
		
		//Model unused = ODRL.model;
	}
}
