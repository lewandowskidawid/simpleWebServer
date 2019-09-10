package org.dlewandowski.webserver.response;

import org.dlewandowski.webserver.Action;
import org.dlewandowski.webserver.request.HttpMethod;

public class ActionFactory {

	public static Action calculateActionType(HttpMethod httpMethod) {
		switch (httpMethod) {
			case GET:
				return new GetAction();
			default:
				return new UnsupportedAction();
		}
	}

}
