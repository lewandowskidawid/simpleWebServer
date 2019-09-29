package org.dlewandowski.webserver.processor;

import java.io.IOException;

/**
 * Classes implementing that interface are exposed in {@link RequestProcessorProvider} class. Any implementation of the interface supports particular type of
 * requests and responds to them.
 */
public interface RequestProcessor {

	/**
	 * Invocation of the method sends requested data to {@link java.net.Socket}
	 *
	 * @throws IOException
	 */
	void process() throws IOException;
}
