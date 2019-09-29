package org.dlewandowski.webserver.request;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class RequestInfoTest {

	@ParameterizedTest
	@MethodSource("provideArguments")
	public void testObjectCreation(String method, String url, String httpVersion, String expectedMethod, String expectedPath,
			String expectedVersion) {
		RequestInfo tester = new RequestInfo(method, url, httpVersion);

		assertEquals(expectedMethod, tester.getMethod());
		assertEquals(expectedPath, tester.getResourcePath());
		assertEquals(expectedVersion, tester.getHttpVersion());
	}

	private static Stream<Arguments> provideArguments() {
		return Stream.of(
				Arguments.of("GET", "/", "1.1", "GET", "/", "1.1"),
				Arguments.of("PUT", "/?test=123", "httpVersion", "PUT", "/", "httpVersion"),
				Arguments.of("DELETE", "/directory/file.html?queryString=true", "1.1", "DELETE", "/directory/file.html", "1.1"),
				Arguments.of("GET", "/file%20with%20spaces.html", "1.1", "GET", "/file with spaces.html", "1.1"),
				Arguments.of("GET", "/file.html#anchor1", "1.1", "GET", "/file.html", "1.1"),
				Arguments.of("GET", "/%24.html", "1.1", "GET", "/$.html", "1.1"),
				Arguments.of("GET", "/%26.html", "1.1", "GET", "/&.html", "1.1"),
				Arguments.of("GET", "/%60.html", "1.1", "GET", "/`.html", "1.1"),
				Arguments.of("GET", "/%3A.html", "1.1", "GET", "/:.html", "1.1"),
				Arguments.of("GET", "/%5B.html", "1.1", "GET", "/[.html", "1.1"),
				Arguments.of("GET", "/%5D.html", "1.1", "GET", "/].html", "1.1"),
				Arguments.of("GET", "/%7B.html", "1.1", "GET", "/{.html", "1.1"),
				Arguments.of("GET", "/%7D.html", "1.1", "GET", "/}.html", "1.1"),
				Arguments.of("GET", "/%22.html", "1.1", "GET", "/\".html", "1.1"),
				Arguments.of("GET", "/%2B.html", "1.1", "GET", "/+.html", "1.1"),
				Arguments.of("GET", "/%23.html", "1.1", "GET", "/#.html", "1.1"),
				Arguments.of("GET", "/%25.html", "1.1", "GET", "/%.html", "1.1"),
				Arguments.of("GET", "/%40.html", "1.1", "GET", "/@.html", "1.1"),
				Arguments.of("GET", "/%3B.html", "1.1", "GET", "/;.html", "1.1"),
				Arguments.of("GET", "/%3D.html", "1.1", "GET", "/=.html", "1.1"),
				Arguments.of("GET", "/%5E.html", "1.1", "GET", "/^.html", "1.1"),
				Arguments.of("GET", "/%7E.html", "1.1", "GET", "/~.html", "1.1"),
				Arguments.of("GET", "/%27.html", "1.1", "GET", "/'.html", "1.1"),
				Arguments.of("GET", "/%2C.html", "1.1", "GET", "/,.html", "1.1")
		);
	}

}