package org.dlewandowski.webserver.requests;

import com.intuit.karate.junit5.Karate;

class RequestsRunner {

	@Karate.Test
	Karate testUsers() {
		return new Karate().feature("requests").relativeTo(getClass());
	}

}
