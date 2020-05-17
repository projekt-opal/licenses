package org.example.utils;

import java.util.Arrays;

public abstract class ArrayUtil {

	public static int[] booleanToInt(boolean[] booleanArray) {
		int[] integerArray = new int[booleanArray.length];
		for (int i = 0; i < booleanArray.length; i++) {
			integerArray[i] = booleanArray[i] ? 1 : 0;
		}
		return integerArray;
	}

	public static String intString(boolean[] booleanArray) {
		return Arrays.toString(booleanToInt(booleanArray));
	}

}