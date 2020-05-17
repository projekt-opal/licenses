package org.example.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;

/**
 * Input/Output utilities.
 *
 * @author 33a1cc8d616a72f953d8e15274194bcd5aac2b78fbe6b4a4d1a911e0f2ef00cd
 */
public abstract class Io {

	/**
	 * Copies URL stream to file stream.
	 */
	public static void download(URL url, File file) throws IOException {
		InputStream inputStream = url.openStream();
		FileOutputStream outputStream = new FileOutputStream(file);
		IOUtils.copy(inputStream, outputStream);
		inputStream.close();
		outputStream.close();
	}
}