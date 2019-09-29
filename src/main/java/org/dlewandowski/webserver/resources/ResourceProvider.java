package org.dlewandowski.webserver.resources;

import java.io.File;

/**
 * Calculates and provides requested file.
 */
public class ResourceProvider {

	private final String directoryPath;

	public ResourceProvider(String directoryPath) {
		this.directoryPath = directoryPath;
	}

	/**
	 * When <code>resource</code> points to a file the it is returned. When resource points to a directory containing <code>index.html</code> file then the index file is returned
	 * otherwise the directory file is returned
	 *
	 * @param resource relative path to the file
	 * @return requested <code>resource</code> as {@link File} object
	 */
	public File getResource(String resource) {
		File result = new File(directoryPath + resource);
		if (result.isDirectory()) {
			File indexFile = result.toPath().resolve("index.html").toFile();
			if (indexFile.exists()) {
				result = indexFile;
			}
		}
		return result;
	}
}
