package org.dlewandowski.webserver.functional;

import com.intuit.karate.junit5.Karate;

class RequestsRunner {

	@Karate.Test
	Karate testRequests() {
		return new Karate().feature("requests").relativeTo(getClass());
	}

}
