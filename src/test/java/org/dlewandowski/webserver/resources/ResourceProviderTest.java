package org.dlewandowski.webserver.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ResourceProviderTest {

	@ParameterizedTest
	@MethodSource("provideArguments")
	public void testResourceProvider(String directory, String resource, String expectedResourcePath) {
		ResourceProvider tester = new ResourceProvider(directory, resource);
		File currentFile = tester.getResource();

		File expectedFile = new File(expectedResourcePath);

		assertEquals(expectedFile.getPath(), currentFile.getPath());
	}

	private static Stream<Arguments> provideArguments() {
		return Stream.of(
				Arguments.of("./src/test/resources", "/", "./src/test/resources/index.html"),
				Arguments.of("./src/test/resources", "/about.html", "./src/test/resources/about.html"),
				Arguments.of("./src/test/resources", "/js", "./src/test/resources/js"),
				Arguments.of("./src/test/resources", "/nonExistingResource", "./src/test/resources/nonExistingResource")
		);
	}
}