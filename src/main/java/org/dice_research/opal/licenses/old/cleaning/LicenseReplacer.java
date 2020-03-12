package org.dice_research.opal.licenses.old.cleaning;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Simple regex-replacing utility class
 * @author Jochen Vothknecht
 */
public class LicenseReplacer {

	private Pattern pattern;
	private String replace;
	
	public LicenseReplacer(String pattern, String replace) {
		this.pattern = Pattern.compile(pattern);
		this.replace = replace;
	}
	
	/**
	 * Replaces the string if it matches the pattern
	 * @param license license URI
	 * @return either the original or the modified string
	 */
	public String replace(String license) {
		Matcher m = pattern.matcher(license);
		
		if (m.matches()) {
			return m.replaceAll(replace);
		} else {
			return license;
		}
	}
}
