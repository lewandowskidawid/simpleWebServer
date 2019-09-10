package org.dlewandowski.webserver;

import java.io.OutputStream;

public interface Action {

	void sendResponse(OutputStream stream);
}
