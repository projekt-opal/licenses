package org.dice_research.opal.licenses;

import java.io.File;

import org.dice_research.opal.licenses.cc.CcExperiment;
import org.dice_research.opal.licenses.cc.CcExperimentTriples;
import org.dice_research.opal.licenses.cc.CcExperimentTuples;
import org.dice_research.opal.licenses.utils.Cfg;

/**
 * Command line interface for integrated experiments.
 *
 * @author Adrian Wilke
 */
public class Main {

	public static final String CC_EXP = "cc1";
	public static final String CC_EXP_2 = "cc2";
	public static final String CC_EXP_3 = "cc3";

	public static void main(String[] args) throws Exception {
		if (args.length == 1) {

			if (args[0].equals(CC_EXP)) {
				new CcExperiment().execute(Cfg.getCcLicenseRdf());

			} else if (args[0].equals(CC_EXP_2)) {
				new CcExperimentTuples().loadData(Cfg.getCcLicenseRdf()).execute().printSpecialCases();

			} else if (args[0].equals(CC_EXP_3)) {
				new CcExperimentTriples().loadData(Cfg.getCcLicenseRdf()).execute()
						.printResults(new File("").getAbsolutePath());

			} else {
				printInfo();
				System.exit(2);
			}

		} else {
			printInfo();
			System.exit(1);
		}
	}

	private static void printInfo() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Please provide the experiment to execute:");
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append("- " + CC_EXP + " Creative Commons License Compatibility Chart");
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append("- " + CC_EXP_2 + " Creative Commons cc.licenserdf, two input licenses");
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append("- " + CC_EXP_3 + " Creative Commons cc.licenserdf, three input licenses");
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append("  (runs around 10 hours)");
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append("Additionally, the path to RDF license files of cc.licenserdf have to be provided.");
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append("Example:");
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append("java -Dcc.licenserdf=../../cc.licenserdf/cc/licenserdf/licenses/ \\");
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append(" -jar licenses-jar-with-dependencies.jar cc1");
		System.out.println(stringBuilder.toString());
	}
}