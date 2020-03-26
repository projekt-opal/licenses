package org.dice_research.opal.licenses.cc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Data of License Compatibility Chart.
 * 
 * @see https://wiki.creativecommons.org/index.php?title=Wiki/cc_license_compatibility&oldid=70058
 * 
 * @author Adrian Wilke
 */
public class CcMatrix {

	private static final String PREFIX = "http://creativecommons.org/";
	public static final String I0_MARK = PREFIX + "publicdomain/mark/1.0/";
	public static final String I1_ZERO = PREFIX + "publicdomain/zero/1.0/";
	public static final String I2_BY = PREFIX + "licenses/by/4.0/";
	public static final String I3_BY_SA = PREFIX + "licenses/by-sa/4.0/";
	public static final String I4_BY_NC = PREFIX + "licenses/by-nc/4.0/";
	public static final String I5_BY_ND = PREFIX + "licenses/by-nd/4.0/";
	public static final String I6_BY_NC_SA = PREFIX + "licenses/by-nc-sa/4.0/";
	public static final String I7_BY_NC_ND = PREFIX + "licenses/by-nc-nd/4.0/";

	public static void main(String[] args) {
		// Just print the matrix
		System.out.println(new CcMatrix());
	}

	private int[][] matrix;

	private LinkedHashMap<String, Integer> uriToIndex;

	public CcMatrix() {
		initialize();
	}

	private void add(int[][] matrix, int index, int... values) {
		matrix[index] = values;
	}

	public boolean[] getBoolean(String uri) {
		Integer index = uriToIndex.get(uri);
		boolean[] array = new boolean[matrix.length];
		for (int i = 0; i < array.length; i++) {
			array[i] = (matrix[index][i] == 0) ? false : true;
		}
		return array;
	}

	public boolean getBoolean(String uriA, String uriB) {
		if (getInteger(uriA, uriB) == 0) {
			return false;
		} else {
			return true;
		}
	}

	public int[] getInteger(String uri) {
		return matrix[uriToIndex.get(uri)];
	}

	public int getInteger(String uriA, String uriB) {
		return matrix[uriToIndex.get(uriA)][uriToIndex.get(uriB)];
	}

	public List<String> getUris() {
		return new ArrayList<>(uriToIndex.keySet());
	}

	private void initialize() {

		int index = 0;
		uriToIndex = new LinkedHashMap<>();
		uriToIndex.put(I0_MARK, index++);
		uriToIndex.put(I1_ZERO, index++);
		uriToIndex.put(I2_BY, index++);
		uriToIndex.put(I3_BY_SA, index++);
		uriToIndex.put(I4_BY_NC, index++);
		uriToIndex.put(I5_BY_ND, index++);
		uriToIndex.put(I6_BY_NC_SA, index++);
		uriToIndex.put(I7_BY_NC_ND, index++);

		index = 0;
		matrix = new int[uriToIndex.size()][uriToIndex.size()];
		add(matrix, index++, 1, 1, 1, 1, 1, 0, 1, 0);
		add(matrix, index++, 1, 1, 1, 1, 1, 0, 1, 0);
		add(matrix, index++, 1, 1, 1, 1, 1, 0, 1, 0);
		add(matrix, index++, 1, 1, 1, 1, 0, 0, 0, 0);
		add(matrix, index++, 1, 1, 1, 0, 1, 0, 1, 0);
		add(matrix, index++, 0, 0, 0, 0, 0, 0, 0, 0);
		add(matrix, index++, 1, 1, 1, 0, 1, 0, 1, 0);
		add(matrix, index++, 0, 0, 0, 0, 0, 0, 0, 0);
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		List<String> uris = getUris();
		stringBuilder.append(uris);
		stringBuilder.append(System.lineSeparator());
		for (int i = 0; i < matrix.length; i++) {
			stringBuilder.append(Arrays.toString(matrix[i]));
			stringBuilder.append(" ");
			stringBuilder.append(uris.get(i));
			stringBuilder.append(System.lineSeparator());
		}
		return stringBuilder.toString();
	}
}