package org.dlewandowski.webserver.resources;

import java.io.File;

public class ResourceProvider {

	private final String directoryPath;

	public ResourceProvider(String directoryPath) {
		this.directoryPath = directoryPath;
	}

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
