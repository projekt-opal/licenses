package org.dice_research.opal.licenses;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.dice_research.opal.licenses.exceptions.UnknownLicenseException;


public class LicenseCombinator implements LicenseCombinatorInterface {

	public static final HashMap<String, License> licenses;
	
	public static class License {
		
		/*
		 * Permissions
		 * 
		 * This are negative properties.
		 * If one license in a combination doesn't give you a permission, the resulting license can't give it to you either.
		 */
		public final boolean reproduction;
		public final boolean distribution;
		public final boolean derivative;
		public final boolean sublicensing;
		public final boolean patentGrant;
		
		/*
		 * Requirements
		 * 
		 * This are positive properties.
		 * If one license in a combination requires you to do something, the resulting license must require you to do this, too.
		 */
		public final boolean notice;
		public final boolean attribution;
		public final boolean shareAlike;
		public final boolean copyleft;
		public final boolean lesserCopyLeft;
		public final boolean stateChanges;
		
		// Prohibitions
		public final boolean commercial;
		public final boolean useTrademark;
		
		public final String name;
		public final String licenseURI;

		/*
		 * Caching Field-objects heavily speeds up reflective accesses
		 */
		private static final List<Field> booleanFields;
		
		static {
			List<Field> fields = Arrays.asList(License.class.getDeclaredFields());
			booleanFields = fields.stream().filter(field -> field.getType().equals(boolean.class)).collect(Collectors.toList());
		}
		
		public License(
				String name,
				String licenseURI,
				
				boolean reproduction,
				boolean distribution,
				boolean derivative,
				boolean sublicensing,
				boolean patentGrant,

				boolean notice,
				boolean attribution,
				boolean shareAlike,
				boolean copyLeft,
				boolean lesserCopyLeft,
				boolean stateChanges,

				boolean commercial,
				boolean useTrademark
		) {
			this.name = name;
			this.licenseURI = licenseURI;
			
			this.reproduction = reproduction;
			this.distribution= distribution;
			this.derivative = derivative;
			this.sublicensing = sublicensing;
			this.patentGrant = patentGrant;

			this.notice = notice;
			this.attribution = attribution;
			this.shareAlike = shareAlike;
			this.copyleft = copyLeft;
			this.lesserCopyLeft = lesserCopyLeft;
			this.stateChanges = stateChanges;

			this.commercial = commercial;
			this.useTrademark = useTrademark;
		}
		
		/**
		 * Creates a predicate for matching compatible licenses
		 * 
		 * @return the predicate
		 */
		public Predicate<License> createPredicate() {
			return (License other) -> {
				
				for (Field field : booleanFields) {
					try {
						if (field.getBoolean(this) && !field.getBoolean(other)) return false;
					} catch (Exception e) {
						e.printStackTrace();
						return false;
					}
				}

				return true;
			};
		}
	}
	
	static {
		licenses = new HashMap<String, License>();
		
		try {
			Files.lines(new File("edp-matrix.csv").toPath()).forEach((String line) -> {
				String fields[] = line.split(",");
				
				String licenseName = fields[0];
				String licenseURI = fields[1];
				
				boolean bFields[] = new boolean[fields.length - 2];
				
				for (int i = 2; i < fields.length; i++) {
					bFields[i - 2] = "Yes".equals(fields[i]);
				}
				
				licenses.put(licenseURI, new License(
						licenseName, licenseURI,
						bFields[0], bFields[1],
						bFields[2], bFields[3],
						bFields[4], bFields[5],
						bFields[6], bFields[7],
						bFields[8], bFields[9],
						bFields[10], bFields[11],
						bFields[12]
				));
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	public static License getLicense(String uri) throws UnknownLicenseException {
		License license = licenses.get(uri);
		
		if (license == null) throw new UnknownLicenseException(uri);
		
		return license;
	}
	
	public Collection<License> getLicenses(Collection<String> licenseURIs) throws UnknownLicenseException {
		try {
			return licenseURIs.stream().map(uri -> {
				try {
					return getLicense(uri);
				} catch (UnknownLicenseException ule) {
					throw new RuntimeException(ule);
				}
			}).collect(Collectors.toSet());
		} catch (RuntimeException re) {
			Throwable cause = re.getCause();
			
			if (cause instanceof UnknownLicenseException) {
				throw (UnknownLicenseException)cause;
			} else {
				throw re;
			}
		}
	}

	@Override
	public List<String> getLicenseSuggestions(Collection<String> usedLicenseUris) throws UnknownLicenseException {
		if (usedLicenseUris.size() < 1) return new LinkedList<String>();
		
		Collection<License> usedLicenses = getLicenses(usedLicenseUris);

		Iterator<License> licenseIterator = usedLicenses.iterator();
		Predicate<License> predicate = licenseIterator.next().createPredicate();
		while (licenseIterator.hasNext()) {
			predicate = predicate.and(licenseIterator.next().createPredicate());
		}
		
		List<License> applicableLicenses = LicenseCombinator.licenses.values().stream().filter(predicate).collect(Collectors.toList());
		
		applicableLicenses.sort(new Comparator<License>() {
			
			private boolean licenseArrayContains(License license) {
				for (License l : usedLicenses) {
					if (license.name.equals(l.name)) return true;
				}
				
				return false;
			}
			
			/**
			 * compares two licenses simply by the fact that they are contained in input set or not
			 */
			@Override
			public int compare(License l0, License l1) {
				boolean containsL0 = licenseArrayContains(l0);
				boolean containsL1 = licenseArrayContains(l1);
				
				if (containsL0 && containsL1) return 0;
				else if (containsL0) return -1;
				else return 1;
			}
		});
		
		return applicableLicenses.stream().map(license -> license.licenseURI).collect(Collectors.toList());
	}

	@Override
	public Map<String, Boolean> getLicenseAttributes(Collection<String> usedLicenseUris) throws UnknownLicenseException {
		if (usedLicenseUris.size() < 1) throw new IllegalArgumentException();
		
		HashMap<String, Boolean> out = new HashMap<String, Boolean>();

		
		
		return out;
	}
}
