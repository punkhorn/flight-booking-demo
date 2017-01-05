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

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.jboss.fuse.sap.flightbookingdemo.bean.FlightConnectionInfo;
import org.jboss.fuse.sap.flightbookingdemo.bean.FlightHop;
import org.jboss.fuse.sap.flightbookingdemo.bean.PassengerInfo;
import org.jboss.fuse.sap.flightbookingdemo.jaxb.BookFlightResponse;
import org.jboss.fuse.sap.flightbookingdemo.jaxb.ConnectionInfo;
import org.jboss.fuse.sap.flightbookingdemo.jaxb.ConnectionInfoTable;
import org.jboss.fuse.sap.flightbookingdemo.jaxb.FlightInfo;

/**
 * Processor that builds Flight Trip Response bean.
 * 
 * @author William Collins <punkhornsw@gmail.com>
 *
 */
public class ReturnFlightTripResponse {

	/**
	 * Builds Flight Trip Response bean from BAPI_FLTRIP_CREATE response in
	 * exchange message body and flight connection info and passenger info beans
	 * in exchange message headers and adds to exchange message body. 
	 * 
	 * @param exchange
	 * @throws Exception
	 */
	public void createFlightBookingResponse(Exchange exchange) throws Exception {

		// Retrieve flight connection and passenger info from exchange message headers. 
		FlightConnectionInfo flightConnectionInfo = exchange.getIn().getHeader("flightConnectionInfo", FlightConnectionInfo.class);
		PassengerInfo passengerInfo = exchange.getIn().getHeader("passengerInfo", PassengerInfo.class);

		// Create bean to hold Flight Booking data.
		BookFlightResponse response = new BookFlightResponse();

		// Passenger Info
		// 	Form
		response.setPassengerFormOfAddress(passengerInfo.getFormOfAddress());
		//  Name
		response.setPassengerName(passengerInfo.getName());
		//  DOB
		response.setPassengerDateOfBirth(passengerInfo.getDateOfBirth());
		
		// Flight Info
		FlightInfo flightInfo = new FlightInfo();
		//  Flight Time
		flightInfo.setFlightTime(flightConnectionInfo.getFlightTime());
		//  Departure City
		flightInfo.setCityFrom(flightConnectionInfo.getDepartureCity());
		//  Departure Date
		flightInfo.setDepartureDate(flightConnectionInfo.getDepartureDate());
		//  Departure Time
		flightInfo.setDepartureTime(flightConnectionInfo.getDepartureTime());
		//  Arrival City
		flightInfo.setCityTo(flightConnectionInfo.getArrivalCity());
		//  Arrival Date
		flightInfo.setArrivalDate(flightConnectionInfo.getArrivalDate());
		//  Arrival Time
		flightInfo.setArrivalTime(flightConnectionInfo.getArrivalTime());
		response.setFlightInfo(flightInfo);

		ConnectionInfoTable connectionInfoTable = new ConnectionInfoTable();
		List<ConnectionInfo> rows = new ArrayList<ConnectionInfo>();
		for (FlightHop flightHop: flightConnectionInfo.getFlightHopList()) {
			// Connection Info
			ConnectionInfo connection = new ConnectionInfo();
			//  Connection ID
			connection.setConnectionId(flightHop.getHopNumber());
			//  Airline
			connection.setAirline(flightHop.getAirlineName());
			//  Plane Type
			connection.setPlaneType(flightHop.getAircraftType());
			//  Departure City
			connection.setCityFrom(flightHop.getDepatureCity());
			//  Departure Date
			connection.setDepartureDate(flightHop.getDepatureDate());
			//  Departure Time
			connection.setDepartureTime(flightHop.getDepatureTime());
			//  Arrival City
			connection.setCityTo(flightHop.getArrivalCity());
			//  Arrival Date
			connection.setArrivalDate(flightHop.getArrivalDate());
			//  Arrival Time
			connection.setArrivalTime(flightHop.getArrivalTime());
			rows.add(connection);
		}
		connectionInfoTable.setRows(rows);
		response.setConnectionInfo(connectionInfoTable);
		
		exchange.getIn().setBody(response);

	}
}
