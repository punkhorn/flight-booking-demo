package org.jboss.fuse.sap.flightbookingdemo.processor;

import org.apache.camel.Exchange;
import org.fusesource.camel.component.sap.SapRfcDestinationEndpoint;
import org.fusesource.camel.component.sap.model.rfc.Structure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateTRfcQinRestartRequest {

	private static final Logger LOG = LoggerFactory.getLogger(CreateTRfcQinRestartRequest.class);
	
	public void create(Exchange exchange) throws Exception {
		
		LOG.info("Creating destination:flbkdemoDest:TRFC_QIN_RESTART request and adding to exchange");
		
		// Create SAP Request object from target endpoint.
		SapRfcDestinationEndpoint endpoint = exchange.getContext().getEndpoint("sap-trfc-destination:flbkdemoDest:TRFC_QIN_RESTART", SapRfcDestinationEndpoint.class);
		Structure request = endpoint.createRequest();
		
		// Add queue name to the request
		request.put("QNAME", "FLBKDEMOQUEUE");

		// Put request object into body of exchange message.
		exchange.getIn().setBody(request);
	}
	
}
