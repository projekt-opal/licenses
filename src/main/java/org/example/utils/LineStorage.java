package org.example.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Reads and writes lines from/to files in temporary directory.
 *
 * @author Adrian Wilke
 */
public abstract class LineStorage {

	private static final Logger LOGGER = LogManager.getLogger();

	public static File getFile(String id) {
		return new File(System.getProperty("java.io.tmpdir"), LineStorage.class.getPackage().getName() + "." + id);
	}

	public static List<String> read(String id) {
		try {
			File file = getFile(id);
			List<String> lines = FileUtils.readLines(file, StandardCharsets.UTF_8.toString());
			LOGGER.info("Read " + lines.size() + " lines from " + file.getAbsolutePath());
			return lines;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void write(String id, List<String> lines) {
		try {
			File file = getFile(id);
			FileUtils.writeLines(file, StandardCharsets.UTF_8.toString(), lines);
			LOGGER.info("Wrote: " + lines.size() + " lines to " + file.getAbsolutePath());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean exists(String id) {
		return getFile(id).exists();
	}

	public static boolean deleteFile(String id) {
		return getFile(id).delete();
	}

}