package org.dice_research.opal.licenses;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 
 * @author Jochen Vothknecht
 */
public class LicenseReplacer {

	private Pattern pattern;
	private String replace;
	
	public LicenseReplacer(String pattern, String replace) {
		this.pattern = Pattern.compile(pattern);
		this.replace = replace;
	}
	
	public String replace(String license) {
		Matcher m = pattern.matcher(license);
		
		if (m.matches()) {
			return m.replaceAll(replace);
		} else {
			return license;
		}
	}
}
