/**
 * Copyright 2013 Red Hat, Inc.
 * 
 * Red Hat licenses this file to you under the Apache License, version
 * 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 * 
 */
package org.jboss.fuse.sap.flightbookingdemo.processor;

import org.apache.camel.Exchange;
import org.fusesource.camel.component.sap.SapSynchronousRfcDestinationEndpoint;
import org.fusesource.camel.component.sap.model.rfc.Structure;
import org.jboss.fuse.sap.flightbookingdemo.jaxb.BookFlightRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Processor that builds SAP Request Object for BAPI_FLCUST_GETLIST RFC call.
 * 
 * @author William Collins <punkhornsw@gmail.com>
 *
 */
public class CreateFlightCustomerGetListRequest {

	private static final Logger LOG = LoggerFactory.getLogger(CreateFlightCustomerGetListRequest.class);

	/**
	 * Builds SAP Request Object for BAPI_FLCUST_GETLIST call using data from
	 * the BOOK_FLIGHT request.
	 * 
	 * @param exchange
	 * @throws Exception
	 */
	public void create(Exchange exchange) throws Exception {

		// Get BOOK_FLIGHT Request JAXB Bean object.
		BookFlightRequest bookFlightRequest = exchange.getIn().getBody(BookFlightRequest.class);

		// Create SAP Request object from target endpoint.
		SapSynchronousRfcDestinationEndpoint endpoint = exchange.getContext().getEndpoint("sap-srfc-destination:flbkdemoDest:BAPI_FLCUST_GETLIST", SapSynchronousRfcDestinationEndpoint.class);
		Structure request = endpoint.createRequest();
		
		// Add Customer Name to request if set
		if (bookFlightRequest.getCustomerName() != null && bookFlightRequest.getCustomerName().length() > 0) {
			request.put("CUSTOMER_NAME", bookFlightRequest.getCustomerName());
			if (LOG.isDebugEnabled()) {
				LOG.debug("Added CUSTOMER_NAME = '{}' to request", bookFlightRequest.getCustomerName());
			}
		} else {
			throw new Exception("No Customer Name");
		}
		
		// Put request object into body of exchange message.
		exchange.getIn().setBody(request);
		
	}	

}
