/**
 * Copyright 2015 Red Hat, Inc.
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
import org.fusesource.camel.component.sap.SapRfcDestinationEndpoint;
import org.fusesource.camel.component.sap.model.rfc.Structure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Create Request to Reset Flight Data Model.
 * 
 * @author William Collins <punkhornsw@gmail.com>
 *
 */
public class CreateZResetFlightDataModelRequest {

	private static final Logger LOG = LoggerFactory.getLogger(CreateZResetFlightDataModelRequest.class);
	
	public void create(Exchange exchange) throws Exception {
		
		LOG.info("Creating ZRESET_FLIGHT_DATA_MODEL request and adding to exchange");
		
		// Create SAP Request object from target endpoint.
		SapRfcDestinationEndpoint endpoint = exchange.getContext().getEndpoint("sap-qrfc-destination:flbkdemoDest:FLBKDEMOQUEUE:ZRESET_FLIGHT_DATA_MODEL", SapRfcDestinationEndpoint.class);
		Structure request = endpoint.createRequest();

		// Put request object into body of exchange message.
		exchange.getIn().setBody(request);
	}
	
}
