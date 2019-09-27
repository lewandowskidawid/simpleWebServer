package org.dlewandowski.webserver.resources;

import java.io.File;

public class ResourceProvider {

	private final String directoryPath;

	private final String requestedResource;

	public ResourceProvider(String directoryPath, String requestedResource) {
		this.directoryPath = directoryPath;
		this.requestedResource = requestedResource;
	}

	public File getResource() {
		File result = new File(directoryPath + requestedResource);
		if (result.isDirectory()) {
			File indexFile = result.toPath().resolve("index.html").toFile();
			if (indexFile.exists()) {
				result = indexFile;
			}
		}
		return result;
	}
}
