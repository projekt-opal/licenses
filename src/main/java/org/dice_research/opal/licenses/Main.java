package org.dice_research.opal.licenses;

import org.dice_research.opal.licenses.cc.CcExperiment;
import org.dice_research.opal.licenses.cc.CcExperimentTriples;
import org.dice_research.opal.licenses.cc.CcExperimentTuples;

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
				CcExperiment.main(args);

			} else if (args[0].equals(CC_EXP)) {
				CcExperimentTuples.main(args);

			} else if (args[0].equals(CC_EXP)) {
				CcExperimentTriples.main(args);

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
		stringBuilder.append("java -jar licenses.java cc1 -Dcc.licenserdf=../cc.licenserdf/cc/licenserdf/licenses/");

		System.out.println(stringBuilder.toString());
	}
}