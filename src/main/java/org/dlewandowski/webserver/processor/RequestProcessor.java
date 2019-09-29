package org.dlewandowski.webserver.processor;

import java.io.IOException;

public interface RequestProcessor {

	void process() throws IOException;
}
