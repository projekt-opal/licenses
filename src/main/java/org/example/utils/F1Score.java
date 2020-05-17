package org.example.utils;

/**
 * Calculates F1score (also known as F-score or F-measure).
 * 
 * @see https://en.wikipedia.org/wiki/F1_score
 * 
 * @author 33a1cc8d616a72f953d8e15274194bcd5aac2b78fbe6b4a4d1a911e0f2ef00cd
 */
public class F1Score {

	private long falseNegative = 0;
	private long falsePositive = 0;
	private long trueNegative = 0;
	private long truePositive = 0;

	public F1Score falseNegative() {
		falseNegative++;
		return this;
	}

	public F1Score falsePositive() {
		falsePositive++;
		return this;
	}

	public long getAll() {
		return falseNegative + falsePositive + trueNegative + truePositive;
	}

	/**
	 * F1score / F-score / F-measure.
	 */
	public double getF1score() {
		double precision = getPrecision();
		double recall = getRecall();
		// If no precision and no recall given, set F1score to 0
		if (precision == 0d && recall == 0d) {
			return 0d;
		} else {
			return 2d * ((precision * recall) / (precision + recall));
		}
	}

	public long getFalse() {
		return falseNegative + falsePositive;
	}

	public long getFalseNegative() {
		return falseNegative;
	}

	public long getFalsePositive() {
		return falsePositive;
	}

	public long getNegative() {
		return falseNegative + trueNegative;
	}

	public double getPercentageFalse() {
		return 1d * getFalse() / getAll();
	}

	public double getPercentageNegative() {
		return 1d * getNegative() / getAll();
	}

	public double getPercentagePositive() {
		return 1d * getPositive() / getAll();
	}

	public double getPercentageTrue() {
		return 1d * getTrue() / getAll();
	}

	public long getPositive() {
		return falsePositive + truePositive;
	}

	/**
	 * Precision / positive predictive value (PPV).
	 */
	public double getPrecision() {
		float precisionDenomiator = truePositive + falsePositive;
		// If division by zero, set fraction to 1
		if (precisionDenomiator == 0d) {
			return 1d;
		} else {
			return truePositive / precisionDenomiator;
		}
	}

	/**
	 * Recall / sensitivity / true positive rate (TPR).
	 */
	public double getRecall() {
		float recallDenominator = truePositive + falseNegative;
		// If division by zero, set fraction to 1
		if (recallDenominator == 0d) {
			return 1d;
		} else {
			return truePositive / recallDenominator;
		}
	}

	public long getTrue() {
		return trueNegative + truePositive;
	}

	public long getTrueNegative() {
		return trueNegative;
	}

	public long getTruePositive() {
		return truePositive;
	}

	public F1Score trueNegative() {
		trueNegative++;
		return this;
	}

	public F1Score truePositive() {
		truePositive++;
		return this;
	}
}