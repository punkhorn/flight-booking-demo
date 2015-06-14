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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.camel.Exchange;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.fusesource.camel.component.sap.SapRfcDestinationEndpoint;
import org.fusesource.camel.component.sap.model.rfc.Structure;
import org.fusesource.camel.component.sap.model.rfc.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Create Request to Load Flight Data Model Data.
 * 
 * @author William Collins <punkhornsw@gmail.com>
 *
 */
@SuppressWarnings("unchecked")
public class CreateZLoadFlightDataModelDataRequest {

	private static final Logger LOG = LoggerFactory.getLogger(CreateZLoadFlightDataModelDataRequest.class);

	private static final String ISCURR_GDATU = "20150531";
	private static final String ISCURR_KURST = "M";
	private static final String ISCURR_TCURR = "EUR";
	
	private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");

	public Table<Structure> iscurxTable;

	public Table<Structure> iscurrTable;

	public Table<Structure> isgeocityTable;

	private Table<Structure> isairportTable;

	private Table<Structure> iscitairpTable;

	private Table<Structure> isaplaneTable;

	private Table<Structure> iscustomTable;
	
	private Table<Structure> istravelagTable;
	
	private Table<Structure> ispfliTable;
	
	private Table<Structure> isflconnposTable;
	
	private Table<Structure> iscarplanTable;

	public void create(Exchange exchange) throws Exception {

		LOG.info("Creating ZLOAD_FLIGHT_DATA_MODEL_DATA request and adding to exchange");

		// Create SAP Request object from target endpoint.
		SapRfcDestinationEndpoint endpoint = exchange.getContext().getEndpoint("sap-qrfc-destination:flbkdemoDest:FLBKDEMOQUEUE:ZLOAD_FLIGHT_DATA_MODEL_DATA",
				SapRfcDestinationEndpoint.class);
		Structure request = endpoint.createRequest();

		// Add Currency Data
		iscurxTable = request.get("TP_SCURX", Table.class);
		addCurrencyData();

		// Add Currency Conversion Data
		iscurrTable = request.get("TP_SCURR", Table.class);
		addCurrencyConversionData();

		// Add Currency Conversion Data
		isgeocityTable = request.get("TP_SGEOCITY", Table.class);
		addGeocityData();

		// Add Airport Data
		isairportTable = request.get("TP_SAIRPORT", Table.class);
		addAirportData();
		
		// Add City to Airport Mapping Data
		iscitairpTable = request.get("TP_SCITAIRP", Table.class);
		addCityAirportMappingData();

		// Add Airplane Data
		isaplaneTable = request.get("TP_SAPLANE", Table.class);
		addPlaneData();

		// Add Customer Data
		iscustomTable = request.get("TP_SCUSTOM", Table.class);
		addCustomerData();

		// Add Travel Agency Data
		istravelagTable = request.get("TP_STRAVELAG", Table.class);
		addTravelAgencyData();
		
		// Add Flight Schedule Data
		ispfliTable = request.get("TP_SPFLI", Table.class);
		addFligtScheduleData();
		
		// Add Flight Connection Data
		isflconnposTable = request.get("TP_SFLCONNPOS", Table.class);
		addFlightConnectionData();

		// Add Airline Plane Data
		iscarplanTable = request.get("TP_SCARPLAN", Table.class);
		addAirlinePlaneData();

		// Put request object into body of exchange message.
		exchange.getIn().setBody(request);
	}
	
	public void addAirlinePlaneData() {
		Structure row;
		
		iscarplanTable.clear();
		
		row = iscarplanTable.add();
		row.put("CARRID", "AA");
		row.put("PLANETYPE", "747-400");
		row.put("SNUMBER", new BigDecimal(3));
		
		row = iscarplanTable.add();
		row.put("CARRID", "AA");
		row.put("PLANETYPE", "A310-300");
		row.put("SNUMBER", new BigDecimal(4));
		
		row = iscarplanTable.add();
		row.put("CARRID", "AZ");
		row.put("PLANETYPE", "747-400");
		row.put("SNUMBER", new BigDecimal(5));
		
		row = iscarplanTable.add();
		row.put("CARRID", "AZ");
		row.put("PLANETYPE", "A319");
		row.put("SNUMBER", new BigDecimal(2));
		
		row = iscarplanTable.add();
		row.put("CARRID", "AZ");
		row.put("PLANETYPE", "DC-10-10");
		row.put("SNUMBER", new BigDecimal(7));
		
		row = iscarplanTable.add();
		row.put("CARRID", "DL");
		row.put("PLANETYPE", "A310-300");
		row.put("SNUMBER", new BigDecimal(6));
		
		row = iscarplanTable.add();
		row.put("CARRID", "DL");
		row.put("PLANETYPE", "A319");
		row.put("SNUMBER", new BigDecimal(7));
		
		row = iscarplanTable.add();
		row.put("CARRID", "DL");
		row.put("PLANETYPE", "DC-10-10");
		row.put("SNUMBER", new BigDecimal(1));
		
		row = iscarplanTable.add();
		row.put("CARRID", "JL");
		row.put("PLANETYPE", "747-400");
		row.put("SNUMBER", new BigDecimal(5));
		
		row = iscarplanTable.add();
		row.put("CARRID", "JL");
		row.put("PLANETYPE", "DC-10.10");
		row.put("SNUMBER", new BigDecimal(1));
		
		row = iscarplanTable.add();
		row.put("CARRID", "LH");
		row.put("PLANETYPE", "727-200");
		row.put("SNUMBER", new BigDecimal(18));
		
		row = iscarplanTable.add();
		row.put("CARRID", "LH");
		row.put("PLANETYPE", "737-200");
		row.put("SNUMBER", new BigDecimal(3));
		
		row = iscarplanTable.add();
		row.put("CARRID", "LH");
		row.put("PLANETYPE", "747-200F");
		row.put("SNUMBER", new BigDecimal(12));
		
		row = iscarplanTable.add();
		row.put("CARRID", "LH");
		row.put("PLANETYPE", "747-400");
		row.put("SNUMBER", new BigDecimal(3));
		
		row = iscarplanTable.add();
		row.put("CARRID", "LH");
		row.put("PLANETYPE", "757F");
		row.put("SNUMBER", new BigDecimal(17));
		
		row = iscarplanTable.add();
		row.put("CARRID", "LH");
		row.put("PLANETYPE", "767-200");
		row.put("SNUMBER", new BigDecimal(2));
		
		row = iscarplanTable.add();
		row.put("CARRID", "LH");
		row.put("PLANETYPE", "767-300");
		row.put("SNUMBER", new BigDecimal(3));
		
		row = iscarplanTable.add();
		row.put("CARRID", "LH");
		row.put("PLANETYPE", "A310-200");
		row.put("SNUMBER", new BigDecimal(5));
		
		row = iscarplanTable.add();
		row.put("CARRID", "LH");
		row.put("PLANETYPE", "A310-300");
		row.put("SNUMBER", new BigDecimal(14));
		
		row = iscarplanTable.add();
		row.put("CARRID", "LH");
		row.put("PLANETYPE", "A319");
		row.put("SNUMBER", new BigDecimal(26));
		
		row = iscarplanTable.add();
		row.put("CARRID", "LH");
		row.put("PLANETYPE", "A321");
		row.put("SNUMBER", new BigDecimal(3));
		
		row = iscarplanTable.add();
		row.put("CARRID", "LH");
		row.put("PLANETYPE", "A330-300");
		row.put("SNUMBER", new BigDecimal(10));
		
		row = iscarplanTable.add();
		row.put("CARRID", "LH");
		row.put("PLANETYPE", "DC-10-10");
		row.put("SNUMBER", new BigDecimal(5));
		
		row = iscarplanTable.add();
		row.put("CARRID", "QF");
		row.put("PLANETYPE", "A310-300");
		row.put("SNUMBER", new BigDecimal(1));
		
		row = iscarplanTable.add();
		row.put("CARRID", "QF");
		row.put("PLANETYPE", "A319");
		row.put("SNUMBER", new BigDecimal(1));
		
		row = iscarplanTable.add();
		row.put("CARRID", "SQ");
		row.put("PLANETYPE", "727-200");
		row.put("SNUMBER", new BigDecimal(2));
		
		row = iscarplanTable.add();
		row.put("CARRID", "SQ");
		row.put("PLANETYPE", "A310-300");
		row.put("SNUMBER", new BigDecimal(4));
		
		row = iscarplanTable.add();
		row.put("CARRID", "SQ");
		row.put("PLANETYPE", "DC-10-10");
		row.put("SNUMBER", new BigDecimal(9));
		
		row = iscarplanTable.add();
		row.put("CARRID", "UA");
		row.put("PLANETYPE", "747-400");
		row.put("SNUMBER", new BigDecimal(5));
		
		row = iscarplanTable.add();
		row.put("CARRID", "UA");
		row.put("PLANETYPE", "A319");
		row.put("SNUMBER", new BigDecimal(2));
		
		row = iscarplanTable.add();
		row.put("CARRID", "UA");
		row.put("PLANETYPE", "DC-10-10");
		row.put("SNUMBER", new BigDecimal(7));
	}
	
	public void addFlightConnectionData() {
		Structure row;
		
		isflconnposTable.clear();
		
		row = isflconnposTable.add();
		row.put("AGENCYNUM", "110");
		row.put("FLCONN", "0001");
		row.put("HOP", "1");
		row.put("CARRID", "AA");
		row.put("CONNID", "0017");
		row.put("DAYSLATER", 0);
		
		row = isflconnposTable.add();
		row.put("AGENCYNUM", "110");
		row.put("FLCONN", "0001");
		row.put("HOP", "2");
		row.put("CARRID", "SQ");
		row.put("CONNID", "0015");
		row.put("DAYSLATER", 0);
		
		row = isflconnposTable.add();
		row.put("AGENCYNUM", "110");
		row.put("FLCONN", "0002");
		row.put("HOP", "1");
		row.put("CARRID", "SQ");
		row.put("CONNID", "0015");
		row.put("DAYSLATER", 0);

		row = isflconnposTable.add();
		row.put("AGENCYNUM", "110");
		row.put("FLCONN", "0002");
		row.put("HOP", "2");
		row.put("CARRID", "QF");
		row.put("CONNID", "0005");
		row.put("DAYSLATER", 1);
		
		row = isflconnposTable.add();
		row.put("AGENCYNUM", "110");
		row.put("FLCONN", "0021");
		row.put("HOP", "1");
		row.put("CARRID", "AA");
		row.put("CONNID", "0017");
		row.put("DAYSLATER", 0);

		row = isflconnposTable.add();
		row.put("AGENCYNUM", "110");
		row.put("FLCONN", "0022");
		row.put("HOP", "1");
		row.put("CARRID", "DL");
		row.put("CONNID", "0106");
		row.put("DAYSLATER", 0);

		row = isflconnposTable.add();
		row.put("AGENCYNUM", "110");
		row.put("FLCONN", "0023");
		row.put("HOP", "1");
		row.put("CARRID", "LH");
		row.put("CONNID", "0400");
		row.put("DAYSLATER", 0);

		row = isflconnposTable.add();
		row.put("AGENCYNUM", "110");
		row.put("FLCONN", "0024");
		row.put("HOP", "1");
		row.put("CARRID", "LH");
		row.put("CONNID", "0401");
		row.put("DAYSLATER", 0);
		
		row = isflconnposTable.add();
		row.put("AGENCYNUM", "110");
		row.put("FLCONN", "0025");
		row.put("HOP", "1");
		row.put("CARRID", "LH");
		row.put("CONNID", "0402");
		row.put("DAYSLATER", 0);

		row = isflconnposTable.add();
		row.put("AGENCYNUM", "110");
		row.put("FLCONN", "0026");
		row.put("HOP", "1");
		row.put("CARRID", "LH");
		row.put("CONNID", "2402");
		row.put("DAYSLATER", 0);

		row = isflconnposTable.add();
		row.put("AGENCYNUM", "110");
		row.put("FLCONN", "0027");
		row.put("HOP", "1");
		row.put("CARRID", "QF");
		row.put("CONNID", "0005");
		row.put("DAYSLATER", 0);

		row = isflconnposTable.add();
		row.put("AGENCYNUM", "110");
		row.put("FLCONN", "0028");
		row.put("HOP", "1");
		row.put("CARRID", "SQ");
		row.put("CONNID", "0002");
		row.put("DAYSLATER", 0);
		
		row = isflconnposTable.add();
		row.put("AGENCYNUM", "110");
		row.put("FLCONN", "0029");
		row.put("HOP", "1");
		row.put("CARRID", "SQ");
		row.put("CONNID", "0015");
		row.put("DAYSLATER", 0);

		row = isflconnposTable.add();
		row.put("AGENCYNUM", "110");
		row.put("FLCONN", "0030");
		row.put("HOP", "1");
		row.put("CARRID", "UA");
		row.put("CONNID", "0941");
		row.put("DAYSLATER", 0);

		row = isflconnposTable.add();
		row.put("AGENCYNUM", "110");
		row.put("FLCONN", "0003");
		row.put("HOP", "1");
		row.put("CARRID", "QF");
		row.put("CONNID", "0006");
		row.put("DAYSLATER", 0);

		row = isflconnposTable.add();
		row.put("AGENCYNUM", "110");
		row.put("FLCONN", "0003");
		row.put("HOP", "2");
		row.put("CARRID", "SQ");
		row.put("CONNID", "0002");
		row.put("DAYSLATER", 2);
		
		row = isflconnposTable.add();
		row.put("AGENCYNUM", "110");
		row.put("FLCONN", "0004");
		row.put("HOP", "1");
		row.put("CARRID", "LH");
		row.put("CONNID", "0402");
		row.put("DAYSLATER", 0);

		row = isflconnposTable.add();
		row.put("AGENCYNUM", "110");
		row.put("FLCONN", "0004");
		row.put("HOP", "2");
		row.put("CARRID", "DL");
		row.put("CONNID", "1699");
		row.put("DAYSLATER", 0);

		row = isflconnposTable.add();
		row.put("AGENCYNUM", "110");
		row.put("FLCONN", "0005");
		row.put("HOP", "1");
		row.put("CARRID", "AA");
		row.put("CONNID", "0064");
		row.put("DAYSLATER", 0);

		row = isflconnposTable.add();
		row.put("AGENCYNUM", "110");
		row.put("FLCONN", "0005");
		row.put("HOP", "2");
		row.put("CARRID", "LH");
		row.put("CONNID", "0401");
		row.put("DAYSLATER", 0);

		row = isflconnposTable.add();
		row.put("AGENCYNUM", "110");
		row.put("FLCONN", "0006");
		row.put("HOP", "1");
		row.put("CARRID", "DL");
		row.put("CONNID", "1984");
		row.put("DAYSLATER", 0);

		row = isflconnposTable.add();
		row.put("AGENCYNUM", "110");
		row.put("FLCONN", "0006");
		row.put("HOP", "2");
		row.put("CARRID", "DL");
		row.put("CONNID", "0106");
		row.put("DAYSLATER", 0);

		row = isflconnposTable.add();
		row.put("AGENCYNUM", "110");
		row.put("FLCONN", "0007");
		row.put("HOP", "1");
		row.put("CARRID", "SQ");
		row.put("CONNID", "0002");
		row.put("DAYSLATER", 0);

		row = isflconnposTable.add();
		row.put("AGENCYNUM", "110");
		row.put("FLCONN", "0007");
		row.put("HOP", "2");
		row.put("CARRID", "AA");
		row.put("CONNID", "0064");
		row.put("DAYSLATER", 1);

		row = isflconnposTable.add();
		row.put("AGENCYNUM", "110");
		row.put("FLCONN", "0008");
		row.put("HOP", "1");
		row.put("CARRID", "AA");
		row.put("CONNID", "0064");
		row.put("DAYSLATER", 0);
		
		row = isflconnposTable.add();
		row.put("AGENCYNUM", "110");
		row.put("FLCONN", "0008");
		row.put("HOP", "2");
		row.put("CARRID", "LH");
		row.put("CONNID", "0401");
		row.put("DAYSLATER", 0);

		row = isflconnposTable.add();
		row.put("AGENCYNUM", "110");
		row.put("FLCONN", "0008");
		row.put("HOP", "3");
		row.put("CARRID", "LH");
		row.put("CONNID", "2402");
		row.put("DAYSLATER", 1);
		
		row = isflconnposTable.add();
		row.put("AGENCYNUM", "110");
		row.put("FLCONN", "0031");
		row.put("HOP", "1");
		row.put("CARRID", "AA");
		row.put("CONNID", "0064");
		row.put("DAYSLATER", 0);

		row = isflconnposTable.add();
		row.put("AGENCYNUM", "110");
		row.put("FLCONN", "0032");
		row.put("HOP", "1");
		row.put("CARRID", "DL");
		row.put("CONNID", "1699");
		row.put("DAYSLATER", 0);

		row = isflconnposTable.add();
		row.put("AGENCYNUM", "110");
		row.put("FLCONN", "0033");
		row.put("HOP", "1");
		row.put("CARRID", "DL");
		row.put("CONNID", "1984");
		row.put("DAYSLATER", 0);

		row = isflconnposTable.add();
		row.put("AGENCYNUM", "110");
		row.put("FLCONN", "0034");
		row.put("HOP", "1");
		row.put("CARRID", "LH");
		row.put("CONNID", "2407");
		row.put("DAYSLATER", 0);

		row = isflconnposTable.add();
		row.put("AGENCYNUM", "110");
		row.put("FLCONN", "0035");
		row.put("HOP", "1");
		row.put("CARRID", "QF");
		row.put("CONNID", "0006");
		row.put("DAYSLATER", 0);

		row = isflconnposTable.add();
		row.put("AGENCYNUM", "110");
		row.put("FLCONN", "0036");
		row.put("HOP", "1");
		row.put("CARRID", "UA");
		row.put("CONNID", "3504");
		row.put("DAYSLATER", 0);
		
	}
	
	public void addFligtScheduleData() throws ParseException {
		Structure row;
		
		ispfliTable.clear();
		
		row = ispfliTable.add();
		row.put("CARRID", "LH");
		row.put("CONNID", "0400");
		row.put("CITYFROM", "FRANKFURT           ");
		row.put("CITYTO", "NEW YORK            ");
		row.put("AIRPFROM", "FRA");
		row.put("AIRPTO", "JFK");
		row.put("DEPTIME", timeFormat.parse("101000"));
		row.put("ARRTIME", timeFormat.parse("113400"));
		row.put("DISTANCE", new BigDecimal(6162));
		row.put("DISTID", "KM ");
		row.put("PERIOD", 0);

		row = ispfliTable.add();
		row.put("CARRID", "AA");
		row.put("CONNID", "0017");
		row.put("CITYFROM", "NEW YORK            ");
		row.put("CITYTO", "SAN FRANCISCO       ");
		row.put("AIRPFROM", "JFK");
		row.put("AIRPTO", "SFO");
		row.put("DEPTIME", timeFormat.parse("110000"));
		row.put("ARRTIME", timeFormat.parse("140100"));
		row.put("DISTANCE", new BigDecimal(2572));
		row.put("DISTID", "MI");
		row.put("PERIOD", 0);

		row = ispfliTable.add();
		row.put("CARRID", "AZ");
		row.put("CONNID", "0555");
		row.put("CITYFROM", "ROME                ");
		row.put("CITYTO", "FRANKFURT           ");
		row.put("AIRPFROM", "FCO");
		row.put("AIRPTO", "FRA");
		row.put("DEPTIME", timeFormat.parse("190000"));
		row.put("ARRTIME", timeFormat.parse("210500"));
		row.put("DISTANCE", new BigDecimal(845));
		row.put("DISTID", "MI");
		row.put("PERIOD", 0);

		row = ispfliTable.add();
		row.put("CARRID", "LH");
		row.put("CONNID", "2402");
		row.put("CITYFROM", "FRANKFURT           ");
		row.put("CITYTO", "BERLIN              ");
		row.put("AIRPFROM", "FRA");
		row.put("AIRPTO", "SXF");
		row.put("DEPTIME", timeFormat.parse("103000"));
		row.put("ARRTIME", timeFormat.parse("113500"));
		row.put("DISTANCE", new BigDecimal(555));
		row.put("DISTID", "KM ");
		row.put("PERIOD", 0);

		row = ispfliTable.add();
		row.put("CARRID", "UA");
		row.put("CONNID", "0941");
		row.put("CITYFROM", "FRANKFURT           ");
		row.put("CITYTO", "SAN FRANCISCO       ");
		row.put("AIRPFROM", "FRA");
		row.put("AIRPTO", "SFO");
		row.put("DEPTIME", timeFormat.parse("143000"));
		row.put("ARRTIME", timeFormat.parse("170600"));
		row.put("DISTANCE", new BigDecimal(5685));
		row.put("DISTID", "MI");
		row.put("PERIOD", 0);

		row = ispfliTable.add();
		row.put("CARRID", "AZ");
		row.put("CONNID", "0789");
		row.put("CITYFROM", "TOKYO               ");
		row.put("CITYTO", "ROME                ");
		row.put("AIRPFROM", "TYO");
		row.put("AIRPTO", "FCO");
		row.put("DEPTIME", timeFormat.parse("114500"));
		row.put("ARRTIME", timeFormat.parse("192500"));
		row.put("DISTANCE", new BigDecimal(6130));
		row.put("DISTID", "MI");
		row.put("PERIOD", 0);

		row = ispfliTable.add();
		row.put("CARRID", "LH");
		row.put("CONNID", "0402");
		row.put("CITYFROM", "FRANKFURT           ");
		row.put("CITYTO", "NEW YORK            ");
		row.put("AIRPFROM", "FRA");
		row.put("AIRPTO", "JFK");
		row.put("DEPTIME", timeFormat.parse("133000"));
		row.put("ARRTIME", timeFormat.parse("150500"));
		row.put("DISTANCE", new BigDecimal(6162));
		row.put("DISTID", "KM ");
		row.put("PERIOD", 0);

		row = ispfliTable.add();
		row.put("CARRID", "QF");
		row.put("CONNID", "0005");
		row.put("CITYFROM", "SINGAPORE           ");
		row.put("CITYTO", "FRANKFURT           ");
		row.put("AIRPFROM", "SIN");
		row.put("AIRPTO", "FRA");
		row.put("DEPTIME", timeFormat.parse("225000"));
		row.put("ARRTIME", timeFormat.parse("053500"));
		row.put("DISTANCE", new BigDecimal(10000));
		row.put("DISTID", "KM ");
		row.put("PERIOD", 1);

		row = ispfliTable.add();
		row.put("CARRID", "SQ");
		row.put("CONNID", "0015");
		row.put("CITYFROM", "SAN FRANCISCO       ");
		row.put("CITYTO", "SINGAPORE           ");
		row.put("AIRPFROM", "SFO");
		row.put("AIRPTO", "SIN");
		row.put("DEPTIME", timeFormat.parse("160000"));
		row.put("ARRTIME", timeFormat.parse("024500"));
		row.put("DISTANCE", new BigDecimal(8452));
		row.put("DISTID", "MI");
		row.put("PERIOD", 2);

		row = ispfliTable.add();
		row.put("CARRID", "SQ");
		row.put("CONNID", "0002");
		row.put("CITYFROM", "SINGAPORE           ");
		row.put("CITYTO", "SAN FRANCISCO       ");
		row.put("AIRPFROM", "SIN");
		row.put("AIRPTO", "SFO");
		row.put("DEPTIME", timeFormat.parse("170000"));
		row.put("ARRTIME", timeFormat.parse("192500"));
		row.put("DISTANCE", new BigDecimal(8452));
		row.put("DISTID", "MI");
		row.put("PERIOD", 0);

		row = ispfliTable.add();
		row.put("CARRID", "LH");
		row.put("CONNID", "0401");
		row.put("CITYFROM", "NEW YORK            ");
		row.put("CITYTO", "FRANKFURT           ");
		row.put("AIRPFROM", "JFK");
		row.put("AIRPTO", "FRA");
		row.put("DEPTIME", timeFormat.parse("183000"));
		row.put("ARRTIME", timeFormat.parse("074500"));
		row.put("DISTANCE", new BigDecimal(6162));
		row.put("DISTID", "KM");
		row.put("PERIOD", 1);

		row = ispfliTable.add();
		row.put("CARRID", "DL");
		row.put("CONNID", "0106");
		row.put("CITYFROM", "NEW YORK            ");
		row.put("CITYTO", "FRANKFURT           ");
		row.put("AIRPFROM", "JFK");
		row.put("AIRPTO", "FRA");
		row.put("DEPTIME", timeFormat.parse("193500"));
		row.put("ARRTIME", timeFormat.parse("093000"));
		row.put("DISTANCE", new BigDecimal(3851));
		row.put("DISTID", "MI");
		row.put("PERIOD", 1);

		row = ispfliTable.add();
		row.put("CARRID", "JL");
		row.put("CONNID", "407");
		row.put("CITYFROM", "TOKYO");
		row.put("CITYTO", "FRANKFURT");
		row.put("AIRPFROM", "NRT");
		row.put("AIRPTO", "FRA");
		row.put("DEPTIME", timeFormat.parse("133000"));
		row.put("ARRTIME", timeFormat.parse("173500"));
		row.put("DISTANCE", new BigDecimal(9100));
		row.put("DISTID", "KM");
		row.put("PERIOD", 0);

		row = ispfliTable.add();
		row.put("CARRID", "JL");
		row.put("CONNID", "408");
		row.put("CITYFROM", "FRANKFURT");
		row.put("CITYTO", "TOKYO");
		row.put("AIRPFROM", "FRA");
		row.put("AIRPTO", "NRT");
		row.put("DEPTIME", timeFormat.parse("202500"));
		row.put("ARRTIME", timeFormat.parse("154000"));
		row.put("DISTANCE", new BigDecimal(9100));
		row.put("DISTID", "KM");
		row.put("PERIOD", 1);

		row = ispfliTable.add();
		row.put("CARRID", "AA");
		row.put("CONNID", "0064");
		row.put("CITYFROM", "SAN FRANCISCO       ");
		row.put("CITYTO", "NEW YORK            ");
		row.put("AIRPFROM", "SFO");
		row.put("AIRPTO", "JFK");
		row.put("DEPTIME", timeFormat.parse("090000"));
		row.put("ARRTIME", timeFormat.parse("172100"));
		row.put("DISTANCE", new BigDecimal(2572));
		row.put("DISTID", "MI");
		row.put("PERIOD", 0);

		row = ispfliTable.add();
		row.put("CARRID", "DL");
		row.put("CONNID", "1699");
		row.put("CITYFROM", "NEW YORK            ");
		row.put("CITYTO", "SAN FRANCISCO       ");
		row.put("AIRPFROM", "JFK");
		row.put("AIRPTO", "SFO");
		row.put("DEPTIME", timeFormat.parse("171500"));
		row.put("ARRTIME", timeFormat.parse("203700"));
		row.put("DISTANCE", new BigDecimal(2572));
		row.put("DISTID", "MI");
		row.put("PERIOD", 0);

		row = ispfliTable.add();
		row.put("CARRID", "DL");
		row.put("CONNID", "1984");
		row.put("CITYFROM", "SAN FRANCISCO       ");
		row.put("CITYTO", "NEW YORK            ");
		row.put("AIRPFROM", "SFO");
		row.put("AIRPTO", "JFK");
		row.put("DEPTIME", timeFormat.parse("100000"));
		row.put("ARRTIME", timeFormat.parse("182500"));
		row.put("DISTANCE", new BigDecimal(2572));
		row.put("DISTID", "MI");
		row.put("PERIOD", 0);

		row = ispfliTable.add();
		row.put("CARRID", "LH");
		row.put("CONNID", "2407");
		row.put("CITYFROM", "BERLIN              ");
		row.put("CITYTO", "FRANKFURT           ");
		row.put("AIRPFROM", "TXL");
		row.put("AIRPTO", "FRA");
		row.put("DEPTIME", timeFormat.parse("071000"));
		row.put("ARRTIME", timeFormat.parse("081500"));
		row.put("DISTANCE", new BigDecimal(555));
		row.put("DISTID", "KM ");
		row.put("PERIOD", 0);

		row = ispfliTable.add();
		row.put("CARRID", "UA");
		row.put("CONNID", "3504");
		row.put("CITYFROM", "SAN FRANCISCO       ");
		row.put("CITYTO", "FRANKFURT           ");
		row.put("AIRPFROM", "SFO");
		row.put("AIRPTO", "FRA");
		row.put("DEPTIME", timeFormat.parse("150000"));
		row.put("ARRTIME", timeFormat.parse("103000"));
		row.put("DISTANCE", new BigDecimal(5685));
		row.put("DISTID", "MI");
		row.put("PERIOD", 1);

		row = ispfliTable.add();
		row.put("CARRID", "AZ");
		row.put("CONNID", "0788");
		row.put("CITYFROM", "ROME                ");
		row.put("CITYTO", "TOKYO               ");
		row.put("AIRPFROM", "FCO");
		row.put("AIRPTO", "TYO");
		row.put("DEPTIME", timeFormat.parse("120000"));
		row.put("ARRTIME", timeFormat.parse("085500"));
		row.put("DISTANCE", new BigDecimal(6130));
		row.put("DISTID", "MI");
		row.put("PERIOD", 1);

		row = ispfliTable.add();
		row.put("CARRID", "AZ");
		row.put("CONNID", "0790");
		row.put("CITYFROM", "ROME                ");
		row.put("CITYTO", "OSAKA               ");
		row.put("AIRPFROM", "FCO");
		row.put("AIRPTO", "KIX");
		row.put("DEPTIME", timeFormat.parse("103500"));
		row.put("ARRTIME", timeFormat.parse("081000"));
		row.put("DISTANCE", new BigDecimal(6030));
		row.put("DISTID", "MI");
		row.put("PERIOD", 1);

		row = ispfliTable.add();
		row.put("CARRID", "QF");
		row.put("CONNID", "0006");
		row.put("CITYFROM", "FRANKFURT           ");
		row.put("CITYTO", "SINGAPORE           ");
		row.put("AIRPFROM", "FRA");
		row.put("AIRPTO", "SIN");
		row.put("DEPTIME", timeFormat.parse("205500"));
		row.put("ARRTIME", timeFormat.parse("150500"));
		row.put("DISTANCE", new BigDecimal(10000));
		row.put("DISTID", "KM ");
		row.put("PERIOD", 1);

		row = ispfliTable.add();
		row.put("CARRID", "SQ");
		row.put("CONNID", "0158");
		row.put("CITYFROM", "SINGAPORE           ");
		row.put("CITYTO", "JAKARTA             ");
		row.put("AIRPFROM", "SIN");
		row.put("AIRPTO", "JKT");
		row.put("DEPTIME", timeFormat.parse("152500"));
		row.put("ARRTIME", timeFormat.parse("160000"));
		row.put("DISTANCE", new BigDecimal(560));
		row.put("DISTID", "MI");
		row.put("PERIOD", 0);

		row = ispfliTable.add();
		row.put("CARRID", "SQ");
		row.put("CONNID", "0988");
		row.put("CITYFROM", "SINGAPORE           ");
		row.put("CITYTO", "TOKYO               ");
		row.put("AIRPFROM", "SIN");
		row.put("AIRPTO", "TYO");
		row.put("DEPTIME", timeFormat.parse("163500"));
		row.put("ARRTIME", timeFormat.parse("001500"));
		row.put("DISTANCE", new BigDecimal(3125));
		row.put("DISTID", "MI");
		row.put("PERIOD", 1);

		row = ispfliTable.add();
		row.put("CARRID", "UA");
		row.put("CONNID", "3516");
		row.put("CITYFROM", "NEW YORK");
		row.put("CITYTO", "FRANKFURT");
		row.put("AIRPFROM", "JFK");
		row.put("AIRPTO", "FRA");
		row.put("DEPTIME", timeFormat.parse("162000"));
		row.put("ARRTIME", timeFormat.parse("054500"));
		row.put("DISTANCE", new BigDecimal(6162));
		row.put("DISTID", "KM");
		row.put("PERIOD", 1);

		row = ispfliTable.add();
		row.put("CARRID", "UA");
		row.put("CONNID", "3517");
		row.put("CITYFROM", "FRANKFURT");
		row.put("CITYTO", "NEW YORK");
		row.put("AIRPFROM", "FRA");
		row.put("AIRPTO", "JFK");
		row.put("DEPTIME", timeFormat.parse("104000"));
		row.put("ARRTIME", timeFormat.parse("125500"));
		row.put("DISTANCE", new BigDecimal(6162));
		row.put("DISTID", "KM");
		row.put("PERIOD", 0);
		
	}
	
	public void addTravelAgencyData() {
		Structure row;
		
		istravelagTable.clear();
		
		row = istravelagTable.add();
		row.put("AGENCYNUM", "55");
		row.put("NAME", "Sunshine Travel");
		row.put("STREET", "134 West Street          ");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "54323                    ");
		row.put("CITY", "Rochester                ");
		row.put("COUNTRY", "US ");
		row.put("REGION", "NY                       ");
		row.put("TELEPHONE", "+1 901-632-5620             ");
		row.put("LANGU", "E                        ");
		row.put("URL", "http://www.sunshine-travel.sap               ");
		row.put("CURRENCY", "USD");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "61");
		row.put("NAME", "Fly High");
		row.put("STREET", "Berliner Allee 11        ");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "40880                    ");
		row.put("CITY", "Duesseldorf               ");
		row.put("COUNTRY", "DE ");
		row.put("REGION", "05                       ");
		row.put("TELEPHONE", "+49 2102 69555              ");
		row.put("LANGU", "D                        ");
		row.put("URL", "http://www.flyhigh.sap                       ");
		row.put("CURRENCY", "EUR");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "87");
		row.put("NAME", "Happy Hopping");
		row.put("STREET", "Calvinstr. 36            ");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "13467                    ");
		row.put("CITY", "Berlin                   ");
		row.put("COUNTRY", "DE ");
		row.put("REGION", "11                       ");
		row.put("TELEPHONE", "+49 30-8853-0               ");
		row.put("LANGU", "D                        ");
		row.put("URL", "http://www.haphop.sap                        ");
		row.put("CURRENCY", "EUR");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "93");
		row.put("NAME", "Pink Panther");
		row.put("STREET", "Auf der Schanz 54        ");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "65936                    ");
		row.put("CITY", "Frankfurt                ");
		row.put("COUNTRY", "DE ");
		row.put("REGION", "06                       ");
		row.put("TELEPHONE", "+49 69-467653-0             ");
		row.put("LANGU", "D                        ");
		row.put("URL", "http://www.pinkpanther.sap                    ");
		row.put("CURRENCY", "EUR");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "100");
		row.put("NAME", "Your Choice");
		row.put("STREET", "Gustav-Jung-Str. 425     ");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "90455");
		row.put("CITY", "Nuernberg");
		row.put("COUNTRY", "DE");
		row.put("REGION", "09");
		row.put("TELEPHONE", "+49 9256-4548-0");
		row.put("LANGU", "D");
		row.put("URL", "http://www.yc.sap");
		row.put("CURRENCY", "EUR");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "101");
		row.put("NAME", "Bella Italia");
		row.put("STREET", "Via Marconi 123");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "00139");
		row.put("CITY", "Roma");
		row.put("COUNTRY", "IT");
		row.put("REGION", "                       ");
		row.put("TELEPHONE", "+39 6 893546721");
		row.put("LANGU", "I");
		row.put("URL", "http://www.tours.it/Adventure/");
		row.put("CURRENCY", "EUR");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "102");
		row.put("NAME", "Hot Socks Travel");
		row.put("STREET", "224 Balnagask Rd          ");
		row.put("POSTBOX", "15 08 69");
		row.put("POSTCODE", "8053                    ");
		row.put("CITY", "Sydney");
		row.put("COUNTRY", "AU ");
		row.put("REGION", "NSW");
		row.put("TELEPHONE", "+61 2 2004 5000             ");
		row.put("LANGU", "E                        ");
		row.put("URL", "http://www.hst.co.au");
		row.put("CURRENCY", "AUD");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "103");
		row.put("NAME", "Burns Nuclear");
		row.put("STREET", "14 Science Park Drive");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "118228");
		row.put("CITY", "Singapore");
		row.put("COUNTRY", "SG");
		row.put("REGION", "   ");
		row.put("TELEPHONE", "+65 777-5566");
		row.put("LANGU", "E");
		row.put("URL", "http://www.burns-burns-burns.sg");
		row.put("CURRENCY", "SGD");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "104");
		row.put("NAME", "Honauer Reisen GmbH");
		row.put("STREET", "Baumgarten 8");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "4212");
		row.put("CITY", "Neumarkt");
		row.put("COUNTRY", "AT");
		row.put("REGION", "                       ");
		row.put("TELEPHONE", "+43 7941 8903");
		row.put("LANGU", "D");
		row.put("URL", "http://www.honauer.at");
		row.put("CURRENCY", "EUR");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "105");
		row.put("NAME", "Travel from Walldorf");
		row.put("STREET", "Altonaer Str. 24         ");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "10557                    ");
		row.put("CITY", "Berlin                   ");
		row.put("COUNTRY", "DE ");
		row.put("REGION", "11                       ");
		row.put("TELEPHONE", "+49 30-622860               ");
		row.put("LANGU", "D                        ");
		row.put("URL", "http://www.travel-from-walldorf");
		row.put("CURRENCY", "EUR");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "106");
		row.put("NAME", "Voyager Enterprises");
		row.put("STREET", "Gustavslundsvaegen 151");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "70563                    ");
		row.put("CITY", "Stockholm                ");
		row.put("COUNTRY", "SE ");
		row.put("REGION", "  ");
		row.put("TELEPHONE", "+46 8/ 587 70000");
		row.put("LANGU", "V");
		row.put("URL", "http://www.starfleet.ufp");
		row.put("CURRENCY", "SEK");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "107");
		row.put("NAME", "Ben McCloskey Ltd.");
		row.put("STREET", "74 Court Oak Rd");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "B17 9TN");
		row.put("CITY", "Birmingham");
		row.put("COUNTRY", "GB");
		row.put("REGION", "   ");
		row.put("TELEPHONE", "+44 121 365-2251              ");
		row.put("LANGU", "E");
		row.put("URL", "http://www.ben-mcCloskey.co.uk");
		row.put("CURRENCY", "GBP");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "108");
		row.put("NAME", "Pillepalle Trips");
		row.put("STREET", "Gorki Park 4             ");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "8008                   ");
		row.put("CITY", "Zuerich                   ");
		row.put("COUNTRY", "CH ");
		row.put("REGION", "                         ");
		row.put("TELEPHONE", "+41 1 345-5321            ");
		row.put("LANGU", "D                        ");
		row.put("URL", "http://www.pi-pa-tri.sap");
		row.put("CURRENCY", "CHF");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "109");
		row.put("NAME", "Kangeroos");
		row.put("STREET", "Lancaster drive 435      ");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "20001                    ");
		row.put("CITY", "London                   ");
		row.put("COUNTRY", "GB ");
		row.put("REGION", "LO                       ");
		row.put("TELEPHONE", "+44 171-2937638           ");
		row.put("LANGU", "E                        ");
		row.put("URL", "http://www.hopp.sap                          ");
		row.put("CURRENCY", "GBP");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "110");
		row.put("NAME", "Bavarian Castle");
		row.put("STREET", "Pilnizerstr. 241         ");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "01069                    ");
		row.put("CITY", "Dresden                  ");
		row.put("COUNTRY", "DE ");
		row.put("REGION", "                         ");
		row.put("TELEPHONE", "+49 98-32832732          ");
		row.put("LANGU", "D                        ");
		row.put("URL", "http://www.neu.schwanstein.sap               ");
		row.put("CURRENCY", "EUR");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "111");
		row.put("NAME", "Ali's Bazar");
		row.put("STREET", "45, Mac Arthur Boulevard ");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "19113                    ");
		row.put("CITY", "Boston                   ");
		row.put("COUNTRY", "US ");
		row.put("REGION", "MA                       ");
		row.put("TELEPHONE", "+1 508-692-5200             ");
		row.put("LANGU", "E                        ");
		row.put("URL", "http://www.ali.sap                           ");
		row.put("CURRENCY", "USD");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "112");
		row.put("NAME", "Super Agency");
		row.put("STREET", "50 Cranworth St");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "G12 8AG");
		row.put("CITY", "Glasgow");
		row.put("COUNTRY", "GB");
		row.put("REGION", "                       ");
		row.put("TELEPHONE", "+44 141 711-5643");
		row.put("LANGU", "E");
		row.put("URL", "http://www.super.sap");
		row.put("CURRENCY", "GBP");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "113");
		row.put("NAME", "Wang Chong");
		row.put("STREET", "Gagarine Park            ");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "150021                   ");
		row.put("CITY", "Moscow                   ");
		row.put("COUNTRY", "RU ");
		row.put("REGION", "                         ");
		row.put("TELEPHONE", "+7 3287-213321    ");
		row.put("LANGU", "E                        ");
		row.put("URL", "http://www.wang.chong.sap");
		row.put("CURRENCY", "USD");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "114");
		row.put("NAME", "Around the World");
		row.put("STREET", "An der Breiten Wiese 122 ");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "30625                    ");
		row.put("CITY", "Hannover                 ");
		row.put("COUNTRY", "DE ");
		row.put("REGION", "03                       ");
		row.put("TELEPHONE", "+49 511-347589-0            ");
		row.put("LANGU", "D                        ");
		row.put("URL", "http://www.atw.sap");
		row.put("CURRENCY", "EUR");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "115");
		row.put("NAME", "No Return");
		row.put("STREET", "Wahnheider Str. 57       ");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "51105                    ");
		row.put("CITY", "Koeln                     ");
		row.put("COUNTRY", "DE ");
		row.put("REGION", "05                       ");
		row.put("TELEPHONE", "+49 221-5689-100            ");
		row.put("LANGU", "D                        ");
		row.put("URL", "http://www.bye-bye.sap                       ");
		row.put("CURRENCY", "EUR");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "116");
		row.put("NAME", "Special Agency Peru");
		row.put("STREET", "Triberger Str. 42        ");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "70569                    ");
		row.put("CITY", "Stuttgart                ");
		row.put("COUNTRY", "DE ");
		row.put("REGION", "08                       ");
		row.put("TELEPHONE", "+49 711-7100                ");
		row.put("LANGU", "D                        ");
		row.put("URL", "http://www.sap.com                           ");
		row.put("CURRENCY", "EUR");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "117");
		row.put("NAME", "Caribian Dreams");
		row.put("STREET", "Deichstrasse 45           ");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "26721                    ");
		row.put("CITY", "Emden                    ");
		row.put("COUNTRY", "DE ");
		row.put("REGION", "03                       ");
		row.put("TELEPHONE", "+49 2670-8560-0             ");
		row.put("LANGU", "D                        ");
		row.put("URL", "http://www.cuba-libre.sap                   ");
		row.put("CURRENCY", "EUR");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "118");
		row.put("NAME", "Asia By Plane");
		row.put("STREET", "6-9 Iidabashi 7-chome");
		row.put("POSTBOX", " ");
		row.put("POSTCODE", "102-0072");
		row.put("CITY", "Tokyo                  ");
		row.put("COUNTRY", "JP");
		row.put("REGION", "  ");
		row.put("TELEPHONE", "+81 3-3239-3501 ");
		row.put("LANGU", "E");
		row.put("URL", "http://www.asia-by-plane.co.jp");
		row.put("CURRENCY", "JPY");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "119");
		row.put("NAME", "Everywhere");
		row.put("STREET", "Regensburger Platz 23    ");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "81679                    ");
		row.put("CITY", "Muenchen                  ");
		row.put("COUNTRY", "DE ");
		row.put("REGION", "09                       ");
		row.put("TELEPHONE", "+49 89-2499239              ");
		row.put("LANGU", "D                        ");
		row.put("URL", "http://www.everywhere.sap");
		row.put("CURRENCY", "EUR");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "120");
		row.put("NAME", "Happy Holiday");
		row.put("STREET", "Rastenburger Str. 12");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "28779                    ");
		row.put("CITY", "Bremen                   ");
		row.put("COUNTRY", "DE ");
		row.put("REGION", "04                       ");
		row.put("TELEPHONE", "+49 3266-288817             ");
		row.put("LANGU", "D                        ");
		row.put("URL", "http://www.haphol.sap");
		row.put("CURRENCY", "EUR");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "121");
		row.put("NAME", "No Name");
		row.put("STREET", "Schwalbenweg 43          ");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "52078                    ");
		row.put("CITY", "Aachen                   ");
		row.put("COUNTRY", "DE ");
		row.put("REGION", "05                       ");
		row.put("TELEPHONE", "+49 241-77729               ");
		row.put("LANGU", "D                        ");
		row.put("URL", "http://www.nn.sap");
		row.put("CURRENCY", "EUR");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "122");
		row.put("NAME", "Fly Low");
		row.put("STREET", "Chemnitzer Str. 42       ");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "01187                    ");
		row.put("CITY", "Dresden                  ");
		row.put("COUNTRY", "DE ");
		row.put("REGION", "14                       ");
		row.put("TELEPHONE", "+49 351-5423-00             ");
		row.put("LANGU", "D                        ");
		row.put("URL", "http://www.fly-low.sap");
		row.put("CURRENCY", "EUR");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "123");
		row.put("NAME", "Aussie Travel");
		row.put("STREET", "Queens Road              ");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "M8 7RYP                  ");
		row.put("CITY", "Manchester               ");
		row.put("COUNTRY", "GB ");
		row.put("REGION", "GM                       ");
		row.put("TELEPHONE", "+44 161 2052000           ");
		row.put("LANGU", "E                        ");
		row.put("URL", "http://www.down-under.sap");
		row.put("CURRENCY", "GBP");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "124");
		row.put("NAME", "Up 'n' Away");
		row.put("STREET", "Nackenbergerstr. 92      ");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "30625                    ");
		row.put("CITY", "Hannover                 ");
		row.put("COUNTRY", "DE ");
		row.put("REGION", "03                       ");
		row.put("TELEPHONE", "+49 511 403266-0            ");
		row.put("LANGU", "D                        ");
		row.put("URL", "http://www.una.sap                           ");
		row.put("CURRENCY", "EUR");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "125");
		row.put("NAME", "Trans World Travel");
		row.put("STREET", "100 Industrial Drive     ");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "60804                    ");
		row.put("CITY", "Chicago                  ");
		row.put("COUNTRY", "US ");
		row.put("REGION", "IL                       ");
		row.put("TELEPHONE", "+1 708-454-8723             ");
		row.put("LANGU", "E                        ");
		row.put("URL", "http://www.twt.sap                           ");
		row.put("CURRENCY", "USD");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "130");
		row.put("NAME", "Bright Side of Life");
		row.put("STREET", "340 State Street         ");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "30432                    ");
		row.put("CITY", "San Francisco            ");
		row.put("COUNTRY", "US ");
		row.put("REGION", "CA                       ");
		row.put("TELEPHONE", "+1 415-454-9877             ");
		row.put("LANGU", "E                        ");
		row.put("URL", "http://www.ruebennase.sap                    ");
		row.put("CURRENCY", "USD");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "152");
		row.put("NAME", "Sunny, Sunny, Sunny");
		row.put("STREET", "1300 State Street        ");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "19003                    ");
		row.put("CITY", "Philadelphia             ");
		row.put("COUNTRY", "US ");
		row.put("REGION", "PA                       ");
		row.put("TELEPHONE", "+1 215-090-7659             ");
		row.put("LANGU", "E                        ");
		row.put("URL", "http://www.s3.sap                           ");
		row.put("CURRENCY", "USD");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "188");
		row.put("NAME", "Fly & Smile");
		row.put("STREET", "Zeppelinstr. 17          ");
		row.put("POSTBOX", "16 05 29                 ");
		row.put("POSTCODE", "60318                    ");
		row.put("CITY", "Frankfurt                ");
		row.put("COUNTRY", "DE ");
		row.put("REGION", "06                       ");
		row.put("TELEPHONE", "+49 69-99-0                 ");
		row.put("LANGU", "D                        ");
		row.put("URL", "http://www.fly-and-smile.sap            ");
		row.put("CURRENCY", "EUR");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "222");
		row.put("NAME", "Supercheap");
		row.put("STREET", "1400, Washington Circle  ");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "30439                    ");
		row.put("CITY", "Los Angeles              ");
		row.put("COUNTRY", "US ");
		row.put("REGION", "CA                       ");
		row.put("TELEPHONE", "+1 251-369-2510             ");
		row.put("LANGU", "E                        ");
		row.put("URL", "http://www.supercheap.sap                    ");
		row.put("CURRENCY", "USD");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "224");
		row.put("NAME", "Hitchhiker");
		row.put("STREET", "21 Rue de Moselle        ");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "92132                    ");
		row.put("CITY", "Issy-les-Moulineaux      ");
		row.put("COUNTRY", "FR ");
		row.put("REGION", "57                       ");
		row.put("TELEPHONE", "+33 1-405-555-888         ");
		row.put("LANGU", "F                        ");
		row.put("URL", "http://www.42.sap                            ");
		row.put("CURRENCY", "EUR");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "229");
		row.put("NAME", "Fly Now, Pay Later");
		row.put("STREET", "100 Madison              ");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "11012                    ");
		row.put("CITY", "New York                 ");
		row.put("COUNTRY", "US ");
		row.put("REGION", "NY                       ");
		row.put("TELEPHONE", "+1 512 343-8543             ");
		row.put("LANGU", "E                        ");
		row.put("URL", "http://www.fn-pl.sap                         ");
		row.put("CURRENCY", "USD");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "233");
		row.put("NAME", "Real Weird Vacation");
		row.put("STREET", "949 5th Street           ");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "V6T 1Z4");
		row.put("CITY", "Vancouver");
		row.put("COUNTRY", "CA ");
		row.put("REGION", "                       ");
		row.put("TELEPHONE", "+1 604 827-8024");
		row.put("LANGU", "E                        ");
		row.put("URL", "http://www.reweva.sap                        ");
		row.put("CURRENCY", "CAD");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "254");
		row.put("NAME", "Cap Travels Ltd.");
		row.put("STREET", "10 Mandela St");
		row.put("POSTBOX", "16 05 29");
		row.put("POSTCODE", "2128");
		row.put("CITY", "Johannesburg");
		row.put("COUNTRY", "ZA");
		row.put("REGION", "06");
		row.put("TELEPHONE", "+27 11 886-8981");
		row.put("LANGU", "E");
		row.put("URL", "http://www.cap-travels.co.za");
		row.put("CURRENCY", "ZAR");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "284");
		row.put("NAME", "Rainy, Stormy, Cloudy");
		row.put("STREET", "Lindenstr. 462           ");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "70563                    ");
		row.put("CITY", "Stuttgart                ");
		row.put("COUNTRY", "DE ");
		row.put("REGION", "08                       ");
		row.put("TELEPHONE", "+49 711-7992-00             ");
		row.put("LANGU", "D                        ");
		row.put("URL", "http://www.windy.sap/rsc/                    ");
		row.put("CURRENCY", "EUR");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "291");
		row.put("NAME", "Women only");
		row.put("STREET", "Kirchstr. 53             ");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "55124                    ");
		row.put("CITY", "Mainz                    ");
		row.put("COUNTRY", "DE ");
		row.put("REGION", "07                       ");
		row.put("TELEPHONE", "+49 6131-543-00             ");
		row.put("LANGU", "D");
		row.put("URL", "http://www.women-only.sap                    ");
		row.put("CURRENCY", "EUR");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "294");
		row.put("NAME", "Maxitrip");
		row.put("STREET", "Flugfeld 17");
		row.put("POSTBOX", "11 06 68");
		row.put("POSTCODE", "65128");
		row.put("CITY", "Wiesbaden");
		row.put("COUNTRY", "DE");
		row.put("REGION", "05");
		row.put("TELEPHONE", "+49 611-55 66 77");
		row.put("LANGU", "D");
		row.put("URL", "http://www.maxitrip.sap");
		row.put("CURRENCY", "EUR");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "295");
		row.put("NAME", "The Ultimate Answer");
		row.put("STREET", "Manchester Rd 20         ");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "AB1 1SA                  ");
		row.put("CITY", "Avon                     ");
		row.put("COUNTRY", "GB ");
		row.put("REGION", "AV                       ");
		row.put("TELEPHONE", "+44 934-66799          ");
		row.put("LANGU", "E");
		row.put("URL", "http://www.thulan.sap                        ");
		row.put("CURRENCY", "GBP");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "297");
		row.put("NAME", "Intertravel");
		row.put("STREET", "Michigan Ave             ");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "60154                    ");
		row.put("CITY", "Chicago                  ");
		row.put("COUNTRY", "US ");
		row.put("REGION", "IL                       ");
		row.put("TELEPHONE", "+1 788 798-6555            ");
		row.put("LANGU", "E                        ");
		row.put("URL", "http://www.intertravel.sap                   ");
		row.put("CURRENCY", "USD");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "301");
		row.put("NAME", "Ultimate Goal");
		row.put("STREET", "300 Peach tree street Sou");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "01069                    ");
		row.put("CITY", "Atlanta                  ");
		row.put("COUNTRY", "US ");
		row.put("REGION", "GA                       ");
		row.put("TELEPHONE", "+1 874-654-6686");
		row.put("LANGU", "E                        ");
		row.put("URL", "http://www.ultimate-goal.sap                 ");
		row.put("CURRENCY", "USD");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "304");
		row.put("NAME", "Submit and Return");
		row.put("STREET", "20890 East Central Ave   ");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "30987                    ");
		row.put("CITY", "Palo Alto                ");
		row.put("COUNTRY", "US ");
		row.put("REGION", "CA                       ");
		row.put("TELEPHONE", "+1 652 645-5236               ");
		row.put("LANGU", "E                        ");
		row.put("URL", "http://www.sar.sap                           ");
		row.put("CURRENCY", "USD");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "310");
		row.put("NAME", "Hendrik's");
		row.put("STREET", "1200 Industrial Drive    ");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "60153                    ");
		row.put("CITY", "Chicago                  ");
		row.put("COUNTRY", "US ");
		row.put("REGION", "IL                       ");
		row.put("TELEPHONE", "+1 08-924-9884             ");
		row.put("LANGU", "E");
		row.put("URL", "http://www.essen.sap/150596                  ");
		row.put("CURRENCY", "USD");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "319");
		row.put("NAME", "All British Air Planes");
		row.put("STREET", "224 Tomato Lane          ");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "08965                    ");
		row.put("CITY", "Vineland                 ");
		row.put("COUNTRY", "US ");
		row.put("REGION", "NJ                       ");
		row.put("TELEPHONE", "+44 609-896-Moore            ");
		row.put("LANGU", "E                        ");
		row.put("URL", "http://www.abap.sap                           ");
		row.put("CURRENCY", "USD");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "320");
		row.put("NAME", "Rocky Horror Tours");
		row.put("STREET", "789 Santa Monica Blvd.   ");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "08934                    ");
		row.put("CITY", "Santa Monica             ");
		row.put("COUNTRY", "US ");
		row.put("REGION", "PA                       ");
		row.put("TELEPHONE", "+1 64351-6455-654          ");
		row.put("LANGU", "E                        ");
		row.put("URL", "http://www.frank.furter.sap                  ");
		row.put("CURRENCY", "USD");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "323");
		row.put("NAME", "Miles and More");
		row.put("STREET", "777 Arlington Blvd.      ");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "46515                    ");
		row.put("CITY", "Elkhart                  ");
		row.put("COUNTRY", "US ");
		row.put("REGION", "IN                       ");
		row.put("TELEPHONE", "+1 646 867-6888            ");
		row.put("LANGU", "E                        ");
		row.put("URL", "http://www.mam.sap");
		row.put("CURRENCY", "USD");

		row = istravelagTable.add();
		row.put("AGENCYNUM", "325");
		row.put("NAME", "Not Only By Bike");
		row.put("STREET", "Saalburgstr. 765         ");
		row.put("POSTBOX", "                         ");
		row.put("POSTCODE", "60385                    ");
		row.put("CITY", "Frankfurt                ");
		row.put("COUNTRY", "DE ");
		row.put("REGION", "06                       ");
		row.put("TELEPHONE", "+49 69 465789-0");
		row.put("LANGU", "D                        ");
		row.put("URL", "http://www.nobb.sap");
		row.put("CURRENCY", "EUR");

	}
	
	public void addCustomerData() {
		Structure row;
		
		iscustomTable.clear();
		
		row = iscustomTable.add();
		row.put("ID", "00000001");
		row.put("NAME", "SAP AG                   ");
		row.put("POSTCODE", "69190     ");
		row.put("CITY", "Walldorf                 ");
		row.put("CUSTTYPE", "B");
		row.put("DISCOUNT", "010");
		row.put("TELEPHONE", "06227-34-0                 ");
		row.put("FORM", "Firma          ");
		row.put("STREET", "Dietmar-Hopp-Allee 16           ");
		row.put("POSTBOX", "          ");
		row.put("REGION", "   ");
		row.put("COUNTRY", "DE ");
		row.put("LANGU", "D");
		row.put("EMAIL", "info@sap.de");

		row = iscustomTable.add();
		row.put("ID", "00000002");
		row.put("NAME", "Andreas Klotz             ");
		row.put("POSTCODE", "69190     ");
		row.put("CITY", "Walldorf                 ");
		row.put("CUSTTYPE", "P");
		row.put("DISCOUNT", "020");
		row.put("TELEPHONE", "05344-676792                  ");
		row.put("FORM", "Herr           ");
		row.put("STREET", "Pimpinellenweg 9         ");
		row.put("POSTBOX", "          ");
		row.put("REGION", "   ");
		row.put("COUNTRY", "DE ");
		row.put("LANGU", "D");
		row.put("EMAIL", "Andreas.Klotz@sap.com");

		row = iscustomTable.add();
		row.put("ID", "00000003");
		row.put("NAME", "Hans Bullinger           ");
		row.put("POSTCODE", "69119     ");
		row.put("CITY", "Heidelberg               ");
		row.put("CUSTTYPE", "P");
		row.put("DISCOUNT", "000");
		row.put("TELEPHONE", "02099-44591                   ");
		row.put("FORM", "Herr           ");
		row.put("STREET", "Veilchenstraße 12       ");
		row.put("POSTBOX", "          ");
		row.put("REGION", "   ");
		row.put("COUNTRY", "DE ");
		row.put("LANGU", "D");
		row.put("EMAIL", "Hansi@Bulli.de");

		row = iscustomTable.add();
		row.put("ID", "00000004");
		row.put("NAME", "Staerck");
		row.put("POSTCODE", "69115     ");
		row.put("CITY", "Heidelberg               ");
		row.put("CUSTTYPE", "P");
		row.put("DISCOUNT", "000");
		row.put("TELEPHONE", "07508-66-7886                 ");
		row.put("FORM", "Frau           ");
		row.put("STREET", "Kaiserstr. 55               ");
		row.put("POSTBOX", "          ");
		row.put("REGION", "   ");
		row.put("COUNTRY", "DE ");
		row.put("LANGU", "D");
		row.put("EMAIL", "Farbenblind@gmx.de");

		row = iscustomTable.add();
		row.put("ID", "00000005");
		row.put("NAME", "Martin                   ");
		row.put("POSTCODE", "L07 1AZ   ");
		row.put("CITY", "Liverpool                ");
		row.put("CUSTTYPE", "P");
		row.put("DISCOUNT", "000");
		row.put("TELEPHONE", "                              ");
		row.put("FORM", "Mister         ");
		row.put("STREET", "44 Harolds Road           ");
		row.put("POSTBOX", "          ");
		row.put("REGION", "   ");
		row.put("COUNTRY", "GB ");
		row.put("LANGU", "E");
		row.put("EMAIL", "Steve.Martin@hool.uk");

		row = iscustomTable.add();
		row.put("ID", "00000006");
		row.put("NAME", "Starr                    ");
		row.put("POSTCODE", "L07 3BC   ");
		row.put("CITY", "Liverpool                ");
		row.put("CUSTTYPE", "P");
		row.put("DISCOUNT", "000");
		row.put("TELEPHONE", "                              ");
		row.put("FORM", "Miss           ");
		row.put("STREET", "74 Kings Road             ");
		row.put("POSTBOX", "          ");
		row.put("REGION", "   ");
		row.put("COUNTRY", "GB ");
		row.put("LANGU", "E");
		row.put("EMAIL", "FCStar@unio.uk");

		row = iscustomTable.add();
		row.put("ID", "00000007");
		row.put("NAME", "King                     ");
		row.put("POSTCODE", "M21 7AB   ");
		row.put("CITY", "Manchester               ");
		row.put("CUSTTYPE", "P");
		row.put("DISCOUNT", "000");
		row.put("TELEPHONE", "                              ");
		row.put("FORM", "Mister         ");
		row.put("STREET", "Hyde Park 7              ");
		row.put("POSTBOX", "          ");
		row.put("REGION", "   ");
		row.put("COUNTRY", "GB ");
		row.put("LANGU", "E");
		row.put("EMAIL", "Burger@King.com");

		row = iscustomTable.add();
		row.put("ID", "00000008");
		row.put("NAME", "Moore                    ");
		row.put("POSTCODE", "SW3 4DE   ");
		row.put("CITY", "London                   ");
		row.put("CUSTTYPE", "P");
		row.put("DISCOUNT", "000");
		row.put("TELEPHONE", "                              ");
		row.put("FORM", "Company        ");
		row.put("STREET", "                         ");
		row.put("POSTBOX", "A-312     ");
		row.put("REGION", "   ");
		row.put("COUNTRY", "GB ");
		row.put("LANGU", "E");
		row.put("EMAIL", "Moore@abc.net");

		row = iscustomTable.add();
		row.put("ID", "00000009");
		row.put("NAME", "Everton FC               ");
		row.put("POSTCODE", "L08 5RZ   ");
		row.put("CITY", "Liverpool                ");
		row.put("CUSTTYPE", "B");
		row.put("DISCOUNT", "000");
		row.put("TELEPHONE", "332-5678-0                    ");
		row.put("FORM", "               ");
		row.put("STREET", "Sir Wellington Road      ");
		row.put("POSTBOX", "          ");
		row.put("REGION", "   ");
		row.put("COUNTRY", "GB ");
		row.put("LANGU", "E");
		row.put("EMAIL", "Football.Liverpool@sports.com");

		row = iscustomTable.add();
		row.put("ID", "00000010");
		row.put("NAME", "Raupp                    ");
		row.put("POSTCODE", "8002      ");
		row.put("CITY", "Zuerich                  ");
		row.put("CUSTTYPE", "P");
		row.put("DISCOUNT", "000");
		row.put("TELEPHONE", "                              ");
		row.put("FORM", "Herr           ");
		row.put("STREET", "Glockenblumenweg 5       ");
		row.put("POSTBOX", "          ");
		row.put("REGION", "   ");
		row.put("COUNTRY", "CH ");
		row.put("LANGU", "D");
		row.put("EMAIL", "HelmutDanielaRaupp@swiss.ch");

		row = iscustomTable.add();
		row.put("ID", "00000011");
		row.put("NAME", "Ballmann                 ");
		row.put("POSTCODE", "69115     ");
		row.put("CITY", "Heidelberg               ");
		row.put("CUSTTYPE", "P");
		row.put("DISCOUNT", "000");
		row.put("TELEPHONE", "                              ");
		row.put("FORM", "Herr           ");
		row.put("STREET", "Ballmanns Leiden 7       ");
		row.put("POSTBOX", "          ");
		row.put("REGION", "   ");
		row.put("COUNTRY", "DE ");
		row.put("LANGU", "D");
		row.put("EMAIL", "Ball@mann.net");

		row = iscustomTable.add();
		row.put("ID", "00000012");
		row.put("NAME", "Radetzky                 ");
		row.put("POSTCODE", "1000      ");
		row.put("CITY", "Wien                     ");
		row.put("CUSTTYPE", "P");
		row.put("DISCOUNT", "000");
		row.put("TELEPHONE", "                              ");
		row.put("FORM", "Frau           ");
		row.put("STREET", "Mozartstr. 102           ");
		row.put("POSTBOX", "          ");
		row.put("REGION", "   ");
		row.put("COUNTRY", "AT ");
		row.put("LANGU", "D");
		row.put("EMAIL", "Radetzky@Marsch.at");

		row = iscustomTable.add();
		row.put("ID", "00000013");
		row.put("NAME", "Mueller                  ");
		row.put("POSTCODE", "68159     ");
		row.put("CITY", "Mannheim                 ");
		row.put("CUSTTYPE", "B");
		row.put("DISCOUNT", "000");
		row.put("TELEPHONE", "                              ");
		row.put("FORM", "Firma          ");
		row.put("STREET", "                         ");
		row.put("POSTBOX", "44321     ");
		row.put("REGION", "   ");
		row.put("COUNTRY", "DE ");
		row.put("LANGU", "D");
		row.put("EMAIL", "Vitamine@mueller.net");

		row = iscustomTable.add();
		row.put("ID", "00000014");
		row.put("NAME", "Genee                    ");
		row.put("POSTCODE", "50733     ");
		row.put("CITY", "Koeln                    ");
		row.put("CUSTTYPE", "P");
		row.put("DISCOUNT", "000");
		row.put("TELEPHONE", "                              ");
		row.put("FORM", "Herr           ");
		row.put("STREET", "Albrecht-Duerer-Str. 4");
		row.put("POSTBOX", "          ");
		row.put("REGION", "   ");
		row.put("COUNTRY", "DE ");
		row.put("LANGU", "D");
		row.put("EMAIL", "Mausklick@cologne.net");

		row = iscustomTable.add();
		row.put("ID", "00000015");
		row.put("NAME", "Soehnen                  ");
		row.put("POSTCODE", "01099     ");
		row.put("CITY", "Dresden                  ");
		row.put("CUSTTYPE", "P");
		row.put("DISCOUNT", "000");
		row.put("TELEPHONE", "                              ");
		row.put("FORM", "Herr           ");
		row.put("STREET", "Veilchenstr. 6          ");
		row.put("POSTBOX", "          ");
		row.put("REGION", "   ");
		row.put("COUNTRY", "DE ");
		row.put("LANGU", "D");
		row.put("EMAIL", "Pastor.Soehnen@kirche.de");

		row = iscustomTable.add();
		row.put("ID", "00000016");
		row.put("NAME", "Leihinger Brauerei       ");
		row.put("POSTCODE", "80339     ");
		row.put("CITY", "Muenchen                 ");
		row.put("CUSTTYPE", "B");
		row.put("DISCOUNT", "005");
		row.put("TELEPHONE", "                              ");
		row.put("FORM", "Firma            ");
		row.put("STREET", "Leopoldsgasse 3          ");
		row.put("POSTBOX", "          ");
		row.put("REGION", "   ");
		row.put("COUNTRY", "DE ");
		row.put("LANGU", "D");
		row.put("EMAIL", "Gutes-Bier@gluck-gluck.de");

		row = iscustomTable.add();
		row.put("ID", "00000017");
		row.put("NAME", "Ruthenberg               ");
		row.put("POSTCODE", "69118     ");
		row.put("CITY", "Heidelberg               ");
		row.put("CUSTTYPE", "P");
		row.put("DISCOUNT", "000");
		row.put("TELEPHONE", "                              ");
		row.put("FORM", "Frau          ");
		row.put("STREET", "Hirschheimer Str. 6A     ");
		row.put("POSTBOX", "          ");
		row.put("REGION", "   ");
		row.put("COUNTRY", "DE ");
		row.put("LANGU", "D");
		row.put("EMAIL", "Briefkasten@ruthi.de");

		row = iscustomTable.add();
		row.put("ID", "00000018");
		row.put("NAME", "Miller                  ");
		row.put("POSTCODE", "46539     ");
		row.put("CITY", "Dinslaken                ");
		row.put("CUSTTYPE", "P");
		row.put("DISCOUNT", "000");
		row.put("TELEPHONE", "                              ");
		row.put("FORM", "Herr           ");
		row.put("STREET", "Am Sandberg 17           ");
		row.put("POSTBOX", "          ");
		row.put("REGION", "   ");
		row.put("COUNTRY", "DE ");
		row.put("LANGU", "E");
		row.put("EMAIL", "MisterMiller@box.com");

		row = iscustomTable.add();
		row.put("ID", "00000019");
		row.put("NAME", "Plum");
		row.put("POSTCODE", "69493     ");
		row.put("CITY", "Großsachsen              ");
		row.put("CUSTTYPE", "P");
		row.put("DISCOUNT", "000");
		row.put("TELEPHONE", "                              ");
		row.put("FORM", "Herr           ");
		row.put("STREET", "Dudenhoeferweg 26        ");
		row.put("POSTBOX", "          ");
		row.put("REGION", "   ");
		row.put("COUNTRY", "DE ");
		row.put("LANGU", "D");
		row.put("EMAIL", "Feuerrot@asdf.net");

		row = iscustomTable.add();
		row.put("ID", "00000020");
		row.put("NAME", "Becker                   ");
		row.put("POSTCODE", "69181     ");
		row.put("CITY", "Leimen                   ");
		row.put("CUSTTYPE", "P");
		row.put("DISCOUNT", "000");
		row.put("TELEPHONE", "                              ");
		row.put("FORM", "Herr           ");
		row.put("STREET", "Wimbledonallee 6         ");
		row.put("POSTBOX", "          ");
		row.put("REGION", "   ");
		row.put("COUNTRY", "DE ");
		row.put("LANGU", "D");
		row.put("EMAIL", "Tennisarm@sabrina.de");

		row = iscustomTable.add();
		row.put("ID", "00000021");
		row.put("NAME", "SAP Oesterreich          ");
		row.put("POSTCODE", "1221      ");
		row.put("CITY", "Wien                     ");
		row.put("CUSTTYPE", "B");
		row.put("DISCOUNT", "010");
		row.put("TELEPHONE", "                              ");
		row.put("FORM", "Firma             ");
		row.put("STREET", "                         ");
		row.put("POSTBOX", "25        ");
		row.put("REGION", "   ");
		row.put("COUNTRY", "AT ");
		row.put("LANGU", "D");
		row.put("EMAIL", "info@sap.at");

		row = iscustomTable.add();
		row.put("ID", "00000022");
		row.put("NAME", "SAP (Schweiz) AG         ");
		row.put("POSTCODE", "2500      ");
		row.put("CITY", "Biel                     ");
		row.put("CUSTTYPE", "B");
		row.put("DISCOUNT", "010");
		row.put("TELEPHONE", "                              ");
		row.put("FORM", "Firma             ");
		row.put("STREET", "Leugenestr. 6            ");
		row.put("POSTBOX", "1305      ");
		row.put("REGION", "   ");
		row.put("COUNTRY", "CH ");
		row.put("LANGU", "D");
		row.put("EMAIL", "info@sap.ch");

		row = iscustomTable.add();
		row.put("ID", "00000023");
		row.put("NAME", "SAP America Inc.         ");
		row.put("POSTCODE", "PA 19113  ");
		row.put("CITY", "Philadelphia");
		row.put("REGION", "PA");
		row.put("CUSTTYPE", "B");
		row.put("DISCOUNT", "015");
		row.put("TELEPHONE", "                              ");
		row.put("FORM", "               ");
		row.put("STREET", "701 Lee Road             ");
		row.put("POSTBOX", "          ");
		row.put("COUNTRY", "US ");
		row.put("LANGU", "E");
		row.put("EMAIL", "info@sap.us.com");

		row = iscustomTable.add();
		row.put("ID", "00000024");
		row.put("NAME", "SAP Thailand             ");
		row.put("POSTCODE", "10500     ");
		row.put("CITY", "Bangkok                  ");
		row.put("CUSTTYPE", "B");
		row.put("DISCOUNT", "005");
		row.put("TELEPHONE", "                              ");
		row.put("FORM", "               ");
		row.put("STREET", "287 Silom Road, Bangrak  ");
		row.put("POSTBOX", "          ");
		row.put("REGION", "   ");
		row.put("COUNTRY", "TH ");
		row.put("LANGU", "2");

		row = iscustomTable.add();
		row.put("ID", "00000025");
		row.put("NAME", "SAP Australia PTY.LTD.   ");
		row.put("POSTCODE", "NSW 2060  ");
		row.put("CITY", "Sydney                   ");
		row.put("CUSTTYPE", "B");
		row.put("DISCOUNT", "000");
		row.put("TELEPHONE", "                              ");
		row.put("FORM", "               ");
		row.put("STREET", "168 Walker Street        ");
		row.put("POSTBOX", "          ");
		row.put("REGION", "   ");
		row.put("COUNTRY", "AU ");
		row.put("LANGU", "E");

		row = iscustomTable.add();
		row.put("ID", "00000026");
		row.put("NAME", "SAP Belgium SA           ");
		row.put("POSTCODE", "1150      ");
		row.put("CITY", "Brussels                 ");
		row.put("CUSTTYPE", "B");
		row.put("DISCOUNT", "005");
		row.put("TELEPHONE", "                              ");
		row.put("FORM", "               ");
		row.put("STREET", "2, Bld. de la Woluwe     ");
		row.put("POSTBOX", "          ");
		row.put("REGION", "   ");
		row.put("COUNTRY", "BE ");
		row.put("LANGU", "F");

		row = iscustomTable.add();
		row.put("ID", "00000027");
		row.put("NAME", "SAP Canada Inc.          ");
		row.put("POSTCODE", "M2P 2B8   ");
		row.put("CITY", "North York Ontario       ");
		row.put("CUSTTYPE", "B");
		row.put("DISCOUNT", "005");
		row.put("TELEPHONE", "                              ");
		row.put("FORM", "               ");
		row.put("STREET", "4120 Yonge Street       ");
		row.put("POSTBOX", "          ");
		row.put("REGION", "   ");
		row.put("COUNTRY", "CA ");
		row.put("LANGU", "E");
		row.put("EMAIL", "info@sap.ca");

		row = iscustomTable.add();
		row.put("ID", "00000028");
		row.put("NAME", "SAP Danmark A/S          ");
		row.put("POSTCODE", "2605      ");
		row.put("CITY", "Broendy/Copenhagen       ");
		row.put("CUSTTYPE", "B");
		row.put("DISCOUNT", "005");
		row.put("TELEPHONE", "                              ");
		row.put("FORM", "               ");
		row.put("STREET", "Rinnager 4B              ");
		row.put("POSTBOX", "          ");
		row.put("REGION", "   ");
		row.put("COUNTRY", "DK ");
		row.put("LANGU", "K");
		row.put("EMAIL", "info@sap.dk");

		row = iscustomTable.add();
		row.put("ID", "00000029");
		row.put("NAME", "SAP France SA            ");
		row.put("POSTCODE", "94132     ");
		row.put("CITY", "Fontenay-Sous-Bois       ");
		row.put("CUSTTYPE", "B");
		row.put("DISCOUNT", "010");
		row.put("TELEPHONE", "                              ");
		row.put("FORM", "               ");
		row.put("STREET", "10 Avenue des Olympiades");
		row.put("POSTBOX", "          ");
		row.put("REGION", "   ");
		row.put("COUNTRY", "FR ");
		row.put("LANGU", "F");
		row.put("EMAIL", "info@sap.fr");

		row = iscustomTable.add();
		row.put("ID", "00000030");
		row.put("NAME", "SAP Italy SPA            ");
		row.put("POSTCODE", "20041     ");
		row.put("CITY", "Agrate Brianza/Milano    ");
		row.put("CUSTTYPE", "B");
		row.put("DISCOUNT", "010");
		row.put("TELEPHONE", "                              ");
		row.put("FORM", "               ");
		row.put("STREET", "Palazzo Orione 3         ");
		row.put("POSTBOX", "          ");
		row.put("REGION", "   ");
		row.put("COUNTRY", "IT ");
		row.put("LANGU", "I");

		row = iscustomTable.add();
		row.put("ID", "00000031");
		row.put("NAME", "SAP Nederland B.V.       ");
		row.put("POSTCODE", "5232      ");
		row.put("CITY", "AG 'S Hertogenbosch       ");
		row.put("CUSTTYPE", "B");
		row.put("DISCOUNT", "005");
		row.put("TELEPHONE", "                              ");
		row.put("FORM", "               ");
		row.put("STREET", "Bruistensingel 400       ");
		row.put("POSTBOX", "          ");
		row.put("REGION", "   ");
		row.put("COUNTRY", "NL ");
		row.put("LANGU", "N");

		row = iscustomTable.add();
		row.put("ID", "00000032");
		row.put("NAME", "SAP Espana y Portugal S.A");
		row.put("POSTCODE", "1250      ");
		row.put("CITY", "Lisabon                  ");
		row.put("CUSTTYPE", "B");
		row.put("DISCOUNT", "010");
		row.put("TELEPHONE", "                              ");
		row.put("FORM", "               ");
		row.put("STREET", "Av. da Libertade, 245-9C ");
		row.put("POSTBOX", "          ");
		row.put("REGION", "   ");
		row.put("COUNTRY", "ES ");
		row.put("LANGU", "S");

		row = iscustomTable.add();
		row.put("ID", "00000033");
		row.put("NAME", "Rolf Lau");
		row.put("POSTCODE", "69120     ");
		row.put("CITY", "Heidelberg               ");
		row.put("CUSTTYPE", "P");
		row.put("DISCOUNT", "000");
		row.put("TELEPHONE", "                              ");
		row.put("FORM", "Herr Dr.");
		row.put("STREET", "Antoniusweg 17              ");
		row.put("POSTBOX", "          ");
		row.put("REGION", "   ");
		row.put("COUNTRY", "DE ");
		row.put("LANGU", "D");
		row.put("EMAIL", "Brillenfrosch@physikus.de");

		row = iscustomTable.add();
		row.put("ID", "00000034");
		row.put("NAME", "Juergen Stahl");
		row.put("POSTCODE", "68222     ");
		row.put("CITY", "Mannheim                 ");
		row.put("CUSTTYPE", "P");
		row.put("DISCOUNT", "000");
		row.put("TELEPHONE", "                              ");
		row.put("FORM", "Herr Prof. Dr.");
		row.put("STREET", "Wuselbert-Ring 2         ");
		row.put("POSTBOX", "          ");
		row.put("REGION", "   ");
		row.put("COUNTRY", "DE ");
		row.put("LANGU", "D");
		row.put("EMAIL", "RomaAeterna@laura.de");

		row = iscustomTable.add();
		row.put("ID", "00000035");
		row.put("NAME", "Brucher                  ");
		row.put("POSTCODE", "69133     ");
		row.put("CITY", "Heidelberg               ");
		row.put("CUSTTYPE", "P");
		row.put("DISCOUNT", "000");
		row.put("TELEPHONE", "                              ");
		row.put("FORM", "Herr           ");
		row.put("STREET", "Bergstr. 20              ");
		row.put("POSTBOX", "          ");
		row.put("REGION", "   ");
		row.put("COUNTRY", "DE ");
		row.put("LANGU", "D");
		row.put("EMAIL", "Brucher@mail.net");

		row = iscustomTable.add();
		row.put("ID", "00000036");
		row.put("NAME", "Maggie Faratti");
		row.put("POSTCODE", "10583");
		row.put("CITY", "Scarsdale");
		row.put("CUSTTYPE", "P");
		row.put("DISCOUNT", "000");
		row.put("TELEPHONE", "914-723-0915");
		row.put("FORM", "Ms           ");
		row.put("STREET", "14 Oak Lane");
		row.put("POSTBOX", "          ");
		row.put("REGION", "NY");
		row.put("COUNTRY", "US ");
		row.put("LANGU", "E");
		row.put("EMAIL", "faratti@pipeline.com");

		row = iscustomTable.add();
		row.put("ID", "00000037");
		row.put("NAME", "Dampf                    ");
		row.put("POSTCODE", "10883     ");
		row.put("CITY", "Berlin                   ");
		row.put("CUSTTYPE", "P");
		row.put("DISCOUNT", "000");
		row.put("TELEPHONE", "                              ");
		row.put("FORM", "Herr           ");
		row.put("STREET", "Robert-Bosch-Str. 4   ");
		row.put("POSTBOX", "          ");
		row.put("REGION", "   ");
		row.put("COUNTRY", "DE ");
		row.put("LANGU", "D");
		row.put("EMAIL", "HansDampf@centigrade.com");

		row = iscustomTable.add();
		row.put("ID", "00000038");
		row.put("NAME", "Rolf-Dietmar Huder");
		row.put("POSTCODE", "10452     ");
		row.put("CITY", "Berlin                   ");
		row.put("CUSTTYPE", "P");
		row.put("DISCOUNT", "000");
		row.put("TELEPHONE", "                              ");
		row.put("FORM", "Frau           ");
		row.put("STREET", "Im Studienseminar 11      ");
		row.put("POSTBOX", "          ");
		row.put("REGION", "   ");
		row.put("COUNTRY", "DE ");
		row.put("LANGU", "D");
		row.put("EMAIL", "knallkopf@musik.de");

	}
	
	public void addPlaneData() {
		Structure row;
		
		isaplaneTable.clear();
		
		row = isaplaneTable.add();
		row.put("PLANETYPE", "146-200   ");
		row.put("SEATSMAX",       112 );
		row.put("SEATSMAX_B",       11 );
		row.put("SEATSMAX_F",       11 );
		row.put("CONSUM",  1.672000000E+03);
		row.put("CON_UNIT", "KGH");
		row.put("TANKCAP", new BigDecimal(     14000.000 ));
		row.put("CAP_UNIT", "L  ");
		row.put("WEIGHT", new BigDecimal(    24320 ));
		row.put("WEI_UNIT", "KG ");
		row.put("SPAN",  2.634000000E+01);
		row.put("SPAN_UNIT", "M  ");
		row.put("LENG",  2.855000000E+01);
		row.put("LENG_UNIT", "M  ");
		row.put("OP_SPEED", new BigDecimal(           361 ));
		row.put("SPEED_UNIT", "KT ");
		row.put("PRODUCER", "BA   ");

		row = isaplaneTable.add();
		row.put("PLANETYPE", "146-300   ");
		row.put("SEATSMAX",       128 );
		row.put("SEATSMAX_B",       11 );
		row.put("SEATSMAX_F",       11 );
		row.put("CONSUM",  1.724000000E+03);
		row.put("CON_UNIT", "KGH");
		row.put("TANKCAP", new BigDecimal(     12901.000 ));
		row.put("CAP_UNIT", "L  ");
		row.put("WEIGHT", new BigDecimal(    25458 ));
		row.put("WEI_UNIT", "KG ");
		row.put("SPAN",  2.634000000E+01);
		row.put("SPAN_UNIT", "M  ");
		row.put("LENG",  3.099000000E+01);
		row.put("LENG_UNIT", "M  ");
		row.put("OP_SPEED", new BigDecimal(           305 ));
		row.put("SPEED_UNIT", "KT ");
		row.put("PRODUCER", "BA   ");

		row = isaplaneTable.add();
		row.put("PLANETYPE", "727-200   ");
		row.put("SEATSMAX",       189 );
		row.put("SEATSMAX_B",       18 );
		row.put("SEATSMAX_F",       11 );
		row.put("CONSUM",  4.309000000E+03);
		row.put("CON_UNIT", "KGH");
		row.put("TANKCAP", new BigDecimal(    10300.000 ));
		row.put("CAP_UNIT", "L  ");
		row.put("WEIGHT", new BigDecimal(    46164 ));
		row.put("WEI_UNIT", "KG ");
		row.put("SPAN",  3.290000000E+01);
		row.put("SPAN_UNIT", "M  ");
		row.put("LENG",  4.670000000E+01);
		row.put("LENG_UNIT", "M  ");
		row.put("OP_SPEED", new BigDecimal(           467 ));
		row.put("SPEED_UNIT", "KT ");
		row.put("PRODUCER", "BOE  ");

		row = isaplaneTable.add();
		row.put("PLANETYPE", "737-200   ");
		row.put("SEATSMAX",       130 );
		row.put("SEATSMAX_B",       10 );
		row.put("SEATSMAX_F",        8 );
		row.put("CONSUM",  2.327000000E+03);
		row.put("CON_UNIT", "KGH");
		row.put("TANKCAP", new BigDecimal(     22598.000 ));
		row.put("CAP_UNIT", "L  ");
		row.put("WEIGHT", new BigDecimal(    27520 ));
		row.put("WEI_UNIT", "KG ");
		row.put("SPAN",  2.830000000E+01);
		row.put("SPAN_UNIT", "M  ");
		row.put("LENG",  3.053000000E+01);
		row.put("LENG_UNIT", "M  ");
		row.put("OP_SPEED", new BigDecimal(           350 ));
		row.put("SPEED_UNIT", "KT ");
		row.put("PRODUCER", "BOE  ");

		row = isaplaneTable.add();
		row.put("PLANETYPE", "747-200F  ");
		row.put("SEATSMAX",        171);
		row.put("SEATSMAX_B",       17 );
		row.put("SEATSMAX_F",       11 );
		row.put("CONSUM",  1.070000000E+04);
		row.put("CON_UNIT", "KGH");
		row.put("TANKCAP", new BigDecimal(     15000.000 ));
		row.put("CAP_UNIT", "L  ");
		row.put("WEIGHT", new BigDecimal(   155130 ));
		row.put("WEI_UNIT", "KG ");
		row.put("SPAN",  5.960000000E+01);
		row.put("SPAN_UNIT", "M  ");
		row.put("LENG",  7.070000000E+01);
		row.put("LENG_UNIT", "M  ");
		row.put("OP_SPEED", new BigDecimal(           484 ));
		row.put("SPEED_UNIT", "KT ");
		row.put("PRODUCER", "BOE  ");

		row = isaplaneTable.add();
		row.put("PLANETYPE", "747-400   ");
		row.put("SEATSMAX",       385 );
		row.put("SEATSMAX_B",       31 );
		row.put("SEATSMAX_F",       21 );
		row.put("CONSUM",  9.950000000E+03);
		row.put("CON_UNIT", "KGH");
		row.put("TANKCAP", new BigDecimal(     12345.000 ));
		row.put("CAP_UNIT", "L  ");
		row.put("WEIGHT", new BigDecimal(   177670 ));
		row.put("WEI_UNIT", "KG ");
		row.put("SPAN",  6.430000000E+01);
		row.put("SPAN_UNIT", "M  ");
		row.put("LENG",  7.070000000E+01);
		row.put("LENG_UNIT", "M  ");
		row.put("OP_SPEED", new BigDecimal(           490 ));
		row.put("SPEED_UNIT", "KT ");
		row.put("PRODUCER", "BOE  ");

		row = isaplaneTable.add();
		row.put("PLANETYPE", "757F      ");
		row.put("SEATSMAX",       239 );
		row.put("SEATSMAX_B",       21 );
		row.put("SEATSMAX_F",       16 );
		row.put("CONSUM",  3.470000000E+03);
		row.put("CON_UNIT", "KGH");
		row.put("TANKCAP", new BigDecimal(     42320.000 ));
		row.put("CAP_UNIT", "L  ");
		row.put("WEIGHT", new BigDecimal(    57067 ));
		row.put("WEI_UNIT", "KG ");
		row.put("SPAN",  3.805000000E+01);
		row.put("SPAN_UNIT", "M  ");
		row.put("LENG",  4.732000000E+01);
		row.put("LENG_UNIT", "M  ");
		row.put("OP_SPEED", new BigDecimal(           350 ));
		row.put("SPEED_UNIT", "KT ");
		row.put("PRODUCER", "BOE  ");

		row = isaplaneTable.add();
		row.put("PLANETYPE", "767-200  ");
		row.put("SEATSMAX",       290 );
		row.put("SEATSMAX_B",       21 );
		row.put("SEATSMAX_F",       11 );
		row.put("CONSUM",  3.770000000E+03);
		row.put("CON_UNIT", "KGH");
		row.put("TANKCAP", new BigDecimal(     43560.000 ));
		row.put("CAP_UNIT", "L  ");
		row.put("WEIGHT", new BigDecimal(    82282 ));
		row.put("WEI_UNIT", "KG ");
		row.put("SPAN",  4.757000000E+01);
		row.put("SPAN_UNIT", "M  ");
		row.put("LENG",  4.851000000E+01);
		row.put("LENG_UNIT", "M  ");
		row.put("OP_SPEED", new BigDecimal(           459 ));
		row.put("SPEED_UNIT", "KT ");
		row.put("PRODUCER", "BOE  ");

		row = isaplaneTable.add();
		row.put("PLANETYPE", "767-300   ");
		row.put("SEATSMAX",       345 );
		row.put("SEATSMAX_B",       31 );
		row.put("SEATSMAX_F",       11 );
		row.put("CONSUM",  3.869000000E+03);
		row.put("CON_UNIT", "KGH");
		row.put("TANKCAP", new BigDecimal(     30000.000 ));
		row.put("CAP_UNIT", "L  ");
		row.put("WEIGHT", new BigDecimal(    86455 ));
		row.put("WEI_UNIT", "KG ");
		row.put("SPAN",  4.757000000E+01);
		row.put("SPAN_UNIT", "M  ");
		row.put("LENG",  5.367000000E+01);
		row.put("LENG_UNIT", "M  ");
		row.put("OP_SPEED", new BigDecimal(           460 ));
		row.put("SPEED_UNIT", "KT ");
		row.put("PRODUCER", "BOE  ");

		row = isaplaneTable.add();
		row.put("PLANETYPE", "A310-200  ");
		row.put("SEATSMAX",       280 );
		row.put("SEATSMAX_B",       34 );
		row.put("SEATSMAX_F",       17 );
		row.put("CONSUM",  3.770000000E+03);
		row.put("CON_UNIT", "KGH");
		row.put("TANKCAP", new BigDecimal(     34560.000 ));
		row.put("CAP_UNIT", "L  ");
		row.put("WEIGHT", new BigDecimal(    79450 ));
		row.put("WEI_UNIT", "KG ");
		row.put("SPAN",  4.390000000E+01);
		row.put("SPAN_UNIT", "M  ");
		row.put("LENG",  4.666000000E+01);
		row.put("LENG_UNIT", "M  ");
		row.put("OP_SPEED", new BigDecimal(           459 ));
		row.put("SPEED_UNIT", "KT ");
		row.put("PRODUCER", "A    ");

		row = isaplaneTable.add();
		row.put("PLANETYPE", "A310-300  ");
		row.put("SEATSMAX",       280 );
		row.put("SEATSMAX_B",       22 );
		row.put("SEATSMAX_F",       10 );
		row.put("CONSUM",  3.730000000E+03);
		row.put("CON_UNIT", "KGH");
		row.put("TANKCAP", new BigDecimal(     45660.000 ));
		row.put("CAP_UNIT", "L  ");
		row.put("WEIGHT", new BigDecimal(    79666 ));
		row.put("WEI_UNIT", "KG ");
		row.put("SPAN",  4.390000000E+01);
		row.put("SPAN_UNIT", "M  ");
		row.put("LENG",  4.666000000E+01);
		row.put("LENG_UNIT", "M  ");
		row.put("OP_SPEED", new BigDecimal(           458 ));
		row.put("SPEED_UNIT", "KT ");
		row.put("PRODUCER", "A    ");

		row = isaplaneTable.add();
		row.put("PLANETYPE", "A319      ");
		row.put("SEATSMAX",       220 );
		row.put("SEATSMAX_B",       22 );
		row.put("SEATSMAX_F",       10 );
		row.put("CONSUM",  2.520000000E+03);
		row.put("CON_UNIT", "KGH");
		row.put("TANKCAP", new BigDecimal(     22340.000 ));
		row.put("CAP_UNIT", "L  ");
		row.put("WEIGHT", new BigDecimal(    46600 ));
		row.put("WEI_UNIT", "KG ");
		row.put("SPAN",  3.410000000E+01);
		row.put("SPAN_UNIT", "M  ");
		row.put("LENG",  4.451000000E+01);
		row.put("LENG_UNIT", "M  ");
		row.put("OP_SPEED", new BigDecimal(           453 ));
		row.put("SPEED_UNIT", "KT ");
		row.put("PRODUCER", "A    ");

		row = isaplaneTable.add();
		row.put("PLANETYPE", "A321      ");
		row.put("SEATSMAX",       220 );
		row.put("SEATSMAX_B",       22 );
		row.put("SEATSMAX_F",       11 );
		row.put("CONSUM",  2.520000000E+03);
		row.put("CON_UNIT", "KGH");
		row.put("TANKCAP", new BigDecimal(     34500.000 ));
		row.put("CAP_UNIT", "L  ");
		row.put("WEIGHT", new BigDecimal(    46600 ));
		row.put("WEI_UNIT", "KG ");
		row.put("SPAN",  3.410000000E+01);
		row.put("SPAN_UNIT", "M  ");
		row.put("LENG",  4.451000000E+01);
		row.put("LENG_UNIT", "M  ");
		row.put("OP_SPEED", new BigDecimal(           453 ));
		row.put("SPEED_UNIT", "KT ");
		row.put("PRODUCER", "A    ");

		row = isaplaneTable.add();
		row.put("PLANETYPE", "A330-300  ");
		row.put("SEATSMAX",       223 );
		row.put("SEATSMAX_B",       22 );
		row.put("SEATSMAX_F",       11 );
		row.put("CONSUM",  4.800000000E+03);
		row.put("CON_UNIT", "KGH");
		row.put("TANKCAP", new BigDecimal(     23450.000 ));
		row.put("CAP_UNIT", "L  ");
		row.put("WEIGHT", new BigDecimal(   117800 ));
		row.put("WEI_UNIT", "KG ");
		row.put("SPAN",  6.030000000E+01);
		row.put("SPAN_UNIT", "M  ");
		row.put("LENG",  6.370000000E+01);
		row.put("LENG_UNIT", "M  ");
		row.put("OP_SPEED", new BigDecimal(           465 ));
		row.put("SPEED_UNIT", "KT ");
		row.put("PRODUCER", "A    ");

		row = isaplaneTable.add();
		row.put("PLANETYPE", "DC-10-10  ");
		row.put("SEATSMAX",       380 );
		row.put("SEATSMAX_B",       41 );
		row.put("SEATSMAX_F",       18 );
		row.put("CONSUM",  6.920000000E+03);
		row.put("CON_UNIT", "KGH");
		row.put("TANKCAP", new BigDecimal(    100561.000 ));
		row.put("CAP_UNIT", "L  ");
		row.put("WEIGHT", new BigDecimal(   110223 ));
		row.put("WEI_UNIT", "KG ");
		row.put("SPAN",  4.734000000E+01);
		row.put("SPAN_UNIT", "M  ");
		row.put("LENG",  5.555000000E+01);
		row.put("LENG_UNIT", "M  ");
		row.put("OP_SPEED", new BigDecimal(           400 ));
		row.put("SPEED_UNIT", "KT ");
		row.put("PRODUCER", "DC   ");

		row = isaplaneTable.add();
		row.put("PLANETYPE", "DC-8-72   ");
		row.put("SEATSMAX",       201 );
		row.put("SEATSMAX_B",       21 );
		row.put("SEATSMAX_F",       11 );
		row.put("CONSUM",  0.000000000E+00);
		row.put("CON_UNIT", "KGH");
		row.put("TANKCAP", new BigDecimal(    100040.000 ));
		row.put("CAP_UNIT", "L  ");
		row.put("WEIGHT", new BigDecimal(    68800 ));
		row.put("WEI_UNIT", "KG ");
		row.put("SPAN",  4.530000000E+01);
		row.put("SPAN_UNIT", "M  ");
		row.put("LENG",  4.800000000E+01);
		row.put("LENG_UNIT", "M  ");
		row.put("OP_SPEED", new BigDecimal(           300 ));
		row.put("SPEED_UNIT", "KT ");
		row.put("PRODUCER", "DC   ");

		row = isaplaneTable.add();
		row.put("PLANETYPE", "FOKKER 100");
		row.put("SEATSMAX",       107 );
		row.put("SEATSMAX_B",       11 );
		row.put("SEATSMAX_F",       11 );
		row.put("CONSUM",  1.683000000E+03);
		row.put("CON_UNIT", "KGH");
		row.put("TANKCAP", new BigDecimal(     74450.000 ));
		row.put("CAP_UNIT", "L  ");
		row.put("WEIGHT", new BigDecimal(    24727 ));
		row.put("WEI_UNIT", "KG ");
		row.put("SPAN",  2.808000000E+01);
		row.put("SPAN_UNIT", "M  ");
		row.put("LENG",  3.553000000E+01);
		row.put("LENG_UNIT", "M  ");
		row.put("OP_SPEED", new BigDecimal(           408 ));
		row.put("SPEED_UNIT", "KT ");
		row.put("PRODUCER", "FOK  ");

		row = isaplaneTable.add();
		row.put("PLANETYPE", "FOKKER 70 ");
		row.put("SEATSMAX",        79 );
		row.put("SEATSMAX_B",        6 );
		row.put("SEATSMAX_F",        4 );
		row.put("CONSUM",  1.475000000E+03);
		row.put("CON_UNIT", "KGH");
		row.put("TANKCAP", new BigDecimal(     55550.000 ));
		row.put("CAP_UNIT", "L  ");
		row.put("WEIGHT", new BigDecimal(    22784 ));
		row.put("WEI_UNIT", "KG ");
		row.put("SPAN",  2.808000000E+01);
		row.put("SPAN_UNIT", "M  ");
		row.put("LENG",  3.091000000E+01);
		row.put("LENG_UNIT", "M  ");
		row.put("OP_SPEED", new BigDecimal(          1475 ));
		row.put("SPEED_UNIT", "KT ");
		row.put("PRODUCER", "FOK  ");

		row = isaplaneTable.add();
		row.put("PLANETYPE", "L-100-30  ");
		row.put("SEATSMAX",        97 );
		row.put("SEATSMAX_B",       11 );
		row.put("SEATSMAX_F",       11 );
		row.put("CONSUM",  1.900000000E+03);
		row.put("CON_UNIT", "KGH");
		row.put("TANKCAP", new BigDecimal(     36622.000 ));
		row.put("CAP_UNIT", "L  ");
		row.put("WEIGHT", new BigDecimal(    34620 ));
		row.put("WEI_UNIT", "KG ");
		row.put("SPAN",  4.041000000E+01);
		row.put("SPAN_UNIT", "M  ");
		row.put("LENG",  3.436000000E+01);
		row.put("LENG_UNIT", "M  ");
		row.put("OP_SPEED", new BigDecimal(           250 ));
		row.put("SPEED_UNIT", "KT ");
		row.put("PRODUCER", "LOC  ");

		row = isaplaneTable.add();
		row.put("PLANETYPE", "L-1011-100");
		row.put("SEATSMAX",       400 );
		row.put("SEATSMAX_B",       41 );
		row.put("SEATSMAX_F",       11 );
		row.put("CONSUM",  6.895000000E+03);
		row.put("CON_UNIT", "KGH");
		row.put("TANKCAP", new BigDecimal(    100000.000 ));
		row.put("CAP_UNIT", "L  ");
		row.put("WEIGHT", new BigDecimal(   111795 ));
		row.put("WEI_UNIT", "KG ");
		row.put("SPAN",  4.735000000E+01);
		row.put("SPAN_UNIT", "M  ");
		row.put("LENG",  5.420000000E+01);
		row.put("LENG_UNIT", "M  ");
		row.put("OP_SPEED", new BigDecimal(           375 ));
		row.put("SPEED_UNIT", "KT ");
		row.put("PRODUCER", "LOC  ");

		row = isaplaneTable.add();
		row.put("PLANETYPE", "RJ115     ");
		row.put("SEATSMAX",       128 );
		row.put("SEATSMAX_B",       11 );
		row.put("SEATSMAX_F",       11 );
		row.put("CONSUM",  2.028000000E+03);
		row.put("CON_UNIT", "KGH");
		row.put("TANKCAP", new BigDecimal(     74560.000 ));
		row.put("CAP_UNIT", "L  ");
		row.put("WEIGHT", new BigDecimal(    26160 ));
		row.put("WEI_UNIT", "KG ");
		row.put("SPAN",  2.621000000E+01);
		row.put("SPAN_UNIT", "M  ");
		row.put("LENG",  3.099000000E+01);
		row.put("LENG_UNIT", "M  ");
		row.put("OP_SPEED", new BigDecimal(           371 ));
		row.put("SPEED_UNIT", "KT ");
		row.put("PRODUCER", "AVR  ");

		row = isaplaneTable.add();
		row.put("PLANETYPE", "RJ85      ");
		row.put("SEATSMAX",       112 );
		row.put("SEATSMAX_B",       11 );
		row.put("SEATSMAX_F",       11 );
		row.put("CONSUM",  1.744000000E+03);
		row.put("CON_UNIT", "KGH");
		row.put("TANKCAP", new BigDecimal(      8476.000 ));
		row.put("CAP_UNIT", "L  ");
		row.put("WEIGHT", new BigDecimal(    24378 ));
		row.put("WEI_UNIT", "KG ");
		row.put("SPAN",  2.621000000E+01);
		row.put("SPAN_UNIT", "M  ");
		row.put("LENG",  2.860000000E+01);
		row.put("LENG_UNIT", "M  ");
		row.put("OP_SPEED", new BigDecimal(           356 ));
		row.put("SPEED_UNIT", "KT ");
		row.put("PRODUCER", "AVR  ");

		row = isaplaneTable.add();
		row.put("PLANETYPE", "YAK-242   ");
		row.put("SEATSMAX",       180 );
		row.put("SEATSMAX_B",       11 );
		row.put("SEATSMAX_F",       11 );
		row.put("CONSUM",  2.190000000E+03);
		row.put("CON_UNIT", "KGH");
		row.put("TANKCAP", new BigDecimal(     30100.000 ));
		row.put("CAP_UNIT", "L  ");
		row.put("WEIGHT", new BigDecimal(    38400 ));
		row.put("WEI_UNIT", "KG ");
		row.put("SPAN",  3.625000000E+01);
		row.put("SPAN_UNIT", "M  ");
		row.put("LENG",  3.825000000E+01);
		row.put("LENG_UNIT", "M  ");
		row.put("OP_SPEED", new BigDecimal(           324 ));
		row.put("SPEED_UNIT", "KT ");
		row.put("PRODUCER", "YAK  ");

		row = isaplaneTable.add();
		row.put("PLANETYPE", "A310-200F  ");
		row.put("SEATSMAX",       10 );
		row.put("SEATSMAX_B",        0 );
		row.put("SEATSMAX_F",        0 );
		row.put("CONSUM",  3.770000000E+03);
		row.put("CON_UNIT", "KGH");
		row.put("TANKCAP", new BigDecimal(     34500.000 ));
		row.put("CAP_UNIT", "L  ");
		row.put("WEIGHT", new BigDecimal(    79450 ));
		row.put("WEI_UNIT", "KG ");
		row.put("SPAN",  4.390000000E+01);
		row.put("SPAN_UNIT", "M  ");
		row.put("LENG",  4.666000000E+01);
		row.put("LENG_UNIT", "M  ");
		row.put("OP_SPEED", new BigDecimal(           459 ));
		row.put("SPEED_UNIT", "KT ");
		row.put("PRODUCER", "A    ");

		row = isaplaneTable.add();
		row.put("PLANETYPE", "A310-200ST  ");
		row.put("SEATSMAX",       8 );
		row.put("SEATSMAX_B",        0 );
		row.put("SEATSMAX_F",        0 );
		row.put("CONSUM",  3.770000000E+03);
		row.put("CON_UNIT", "KGH");
		row.put("TANKCAP", new BigDecimal(    156260.000 ));
		row.put("CAP_UNIT", "L  ");
		row.put("WEIGHT", new BigDecimal(    79450 ));
		row.put("WEI_UNIT", "KG ");
		row.put("SPAN",  4.390000000E+01);
		row.put("SPAN_UNIT", "M  ");
		row.put("LENG",  4.666000000E+01);
		row.put("LENG_UNIT", "M  ");
		row.put("OP_SPEED", new BigDecimal(           459 ));
		row.put("SPEED_UNIT", "KT ");
		row.put("PRODUCER", "A    ");

		row = isaplaneTable.add();
		row.put("PLANETYPE", "737-200SF ");
		row.put("SEATSMAX",        13 );
		row.put("SEATSMAX_B",        0 );
		row.put("SEATSMAX_F",        0 );
		row.put("CONSUM",  2.327000000E+03);
		row.put("CON_UNIT", "KGH");
		row.put("TANKCAP", new BigDecimal(     22598.000 ));
		row.put("CAP_UNIT", "L  ");
		row.put("WEIGHT", new BigDecimal(    27520 ));
		row.put("WEI_UNIT", "KG ");
		row.put("SPAN",  2.830000000E+01);
		row.put("SPAN_UNIT", "M  ");
		row.put("LENG",  3.053000000E+01);
		row.put("LENG_UNIT", "M  ");
		row.put("OP_SPEED", new BigDecimal(           350 ));
		row.put("SPEED_UNIT", "KT ");
		row.put("PRODUCER", "BOE  ");

	}
	
	public void addCityAirportMappingData() {
		Structure row;
		
		iscitairpTable.clear();
		
		row = iscitairpTable.add();
		row.put("CITY", "ACAPULCO      ");
		row.put("COUNTRY", "MX");
		row.put("AIRPORT", "ACA");
		row.put("MASTERCITY", "ACAPULCO");

		row = iscitairpTable.add();
		row.put("CITY", "ALICE SPRINGS ");
		row.put("COUNTRY", "AU");
		row.put("AIRPORT", "ASP");
		row.put("MASTERCITY", "ALICE SPRINGS");

		row = iscitairpTable.add();
		row.put("CITY", "AMSTERDAM     ");
		row.put("COUNTRY", "NL");
		row.put("AIRPORT", "RTM");
		row.put("MASTERCITY", "ROTTERDAM");

		row = iscitairpTable.add();
		row.put("CITY", "ATLANTIC CITY ");
		row.put("COUNTRY", "US");
		row.put("AIRPORT", "AIY");
		row.put("MASTERCITY", "ATLANTIC CITY");
		
		row = iscitairpTable.add();
		row.put("CITY", "BANGKOK       ");
		row.put("COUNTRY", "TH");
		row.put("AIRPORT", "BKK");
		row.put("MASTERCITY", "BANGKOK");

		row = iscitairpTable.add();
		row.put("CITY", "BARCELONA     ");
		row.put("COUNTRY", "ES");
		row.put("AIRPORT", "MAD");
		row.put("MASTERCITY", "MADRID");

		row = iscitairpTable.add();
		row.put("CITY", "BASEL         ");
		row.put("COUNTRY", "CH");
		row.put("AIRPORT", "ZRH");
		row.put("MASTERCITY", "ZURICH");

		row = iscitairpTable.add();
		row.put("CITY", "BERLIN        ");
		row.put("COUNTRY", "DE");
		row.put("AIRPORT", "SXF");
		row.put("MASTERCITY", "BERLIN");

		row = iscitairpTable.add();
		row.put("CITY", "BERLIN        ");
		row.put("COUNTRY", "DE");
		row.put("AIRPORT", "THF");
		row.put("MASTERCITY", "BERLIN");

		row = iscitairpTable.add();
		row.put("CITY", "BERLIN        ");
		row.put("COUNTRY", "DE");
		row.put("AIRPORT", "TXL");
		row.put("MASTERCITY", "BERLIN");

		row = iscitairpTable.add();
		row.put("CITY", "BOSTON        ");
		row.put("COUNTRY", "US");
		row.put("AIRPORT", "BOS");
		row.put("MASTERCITY", "BOSTON");

		row = iscitairpTable.add();
		row.put("CITY", "DENVER        ");
		row.put("COUNTRY", "US");
		row.put("AIRPORT", "DEN");
		row.put("MASTERCITY", "DENVER");

		row = iscitairpTable.add();
		row.put("CITY", "EDMONTON      ");
		row.put("COUNTRY", "CA");
		row.put("AIRPORT", "YEG");
		row.put("MASTERCITY", "EDMONTON");

		row = iscitairpTable.add();
		row.put("CITY", "EL PASO       ");
		row.put("COUNTRY", "US");
		row.put("AIRPORT", "ELP");
		row.put("MASTERCITY", "EL PASO");

		row = iscitairpTable.add();
		row.put("CITY", "FRANKFURT     ");
		row.put("COUNTRY", "DE");
		row.put("AIRPORT", "FRA");
		row.put("MASTERCITY", "FRANKFURT");

		row = iscitairpTable.add();
		row.put("CITY", "HAMBURG       ");
		row.put("COUNTRY", "DE");
		row.put("AIRPORT", "HAM");
		row.put("MASTERCITY", "HAMBURG");

		row = iscitairpTable.add();
		row.put("CITY", "HARARE        ");
		row.put("COUNTRY", "ZW");
		row.put("AIRPORT", "HRE");
		row.put("MASTERCITY", "HARARE");

		row = iscitairpTable.add();
		row.put("CITY", "HIROSHIMA     ");
		row.put("COUNTRY", "JP");
		row.put("AIRPORT", "HIJ");
		row.put("MASTERCITY", "HIROSHIMA");

		row = iscitairpTable.add();
		row.put("CITY", "HONGKONG      ");
		row.put("COUNTRY", "HK");
		row.put("AIRPORT", "HKG");
		row.put("MASTERCITY", "HONGKONG");

		row = iscitairpTable.add();
		row.put("CITY", "HOUSTON       ");
		row.put("COUNTRY", "US");
		row.put("AIRPORT", "HOU");
		row.put("MASTERCITY", "HOUSTON");

		row = iscitairpTable.add();
		row.put("CITY", "JAKARTA       ");
		row.put("COUNTRY", "ID");
		row.put("AIRPORT", "JKT");
		row.put("MASTERCITY", "JAKARTA");

		row = iscitairpTable.add();
		row.put("CITY", "JOHANNESBURG  ");
		row.put("COUNTRY", "ZA");
		row.put("AIRPORT", "GCJ");
		row.put("MASTERCITY", "JOHANNESBURG");

		row = iscitairpTable.add();
		row.put("CITY", "KANSAS CITY   ");
		row.put("COUNTRY", "US");
		row.put("AIRPORT", "MCI");
		row.put("MASTERCITY", "KANSAS CITY");

		row = iscitairpTable.add();
		row.put("CITY", "KUALA LUMPUR  ");
		row.put("COUNTRY", "MY");
		row.put("AIRPORT", "KUL");
		row.put("MASTERCITY", "KUALA LUMPUR");

		row = iscitairpTable.add();
		row.put("CITY", "LANZAROTE     ");
		row.put("COUNTRY", "ES");
		row.put("AIRPORT", "ACE");
		row.put("MASTERCITY", "LANZAROTE");

		row = iscitairpTable.add();
		row.put("CITY", "LAS VEGAS     ");
		row.put("COUNTRY", "US");
		row.put("AIRPORT", "LAS");
		row.put("MASTERCITY", "LAS VEGAS");

		row = iscitairpTable.add();
		row.put("CITY", "LONDON        ");
		row.put("COUNTRY", "GB");
		row.put("AIRPORT", "LCY");
		row.put("MASTERCITY", "LONDON");

		row = iscitairpTable.add();
		row.put("CITY", "LONDON        ");
		row.put("COUNTRY", "GB");
		row.put("AIRPORT", "LGW");
		row.put("MASTERCITY", "LONDON");

		row = iscitairpTable.add();
		row.put("CITY", "LONDON        ");
		row.put("COUNTRY", "GB");
		row.put("AIRPORT", "LHR");
		row.put("MASTERCITY", "LONDON");

		row = iscitairpTable.add();
		row.put("CITY", "LOS ANGELES   ");
		row.put("COUNTRY", "US");
		row.put("AIRPORT", "LAX");
		row.put("MASTERCITY", "LOS ANGELES");

		row = iscitairpTable.add();
		row.put("CITY", "MADRID        ");
		row.put("COUNTRY", "ES");
		row.put("AIRPORT", "MAD");
		row.put("MASTERCITY", "MADRID");

		row = iscitairpTable.add();
		row.put("CITY", "MIAMI         ");
		row.put("COUNTRY", "US");
		row.put("AIRPORT", "MIA");
		row.put("MASTERCITY", "MIAMI");

		row = iscitairpTable.add();
		row.put("CITY", "MOSCOW        ");
		row.put("COUNTRY", "RU");
		row.put("AIRPORT", "SVO");
		row.put("MASTERCITY", "MOSCOW");

		row = iscitairpTable.add();
		row.put("CITY", "MOSCOW        ");
		row.put("COUNTRY", "RU");
		row.put("AIRPORT", "VKO");
		row.put("MASTERCITY", "MOSCOW");

		row = iscitairpTable.add();
		row.put("CITY", "MUNICH        ");
		row.put("COUNTRY", "DE");
		row.put("AIRPORT", "MUC");
		row.put("MASTERCITY", "MUNICH");

		row = iscitairpTable.add();
		row.put("CITY", "NASHVILLE     ");
		row.put("COUNTRY", "US");
		row.put("AIRPORT", "BNA");
		row.put("MASTERCITY", "NASHVILLE");

		row = iscitairpTable.add();
		row.put("CITY", "NASSAU        ");
		row.put("COUNTRY", "BS");
		row.put("AIRPORT", "PID");
		row.put("MASTERCITY", "NASSAU");

		row = iscitairpTable.add();
		row.put("CITY", "NEW YORK      ");
		row.put("COUNTRY", "US");
		row.put("AIRPORT", "EWR");
		row.put("MASTERCITY", "NEW YORK");

		row = iscitairpTable.add();
		row.put("CITY", "NEW YORK      ");
		row.put("COUNTRY", "US");
		row.put("AIRPORT", "JFK");
		row.put("MASTERCITY", "NEW YORK");

		row = iscitairpTable.add();
		row.put("CITY", "OSAKA         ");
		row.put("COUNTRY", "JP");
		row.put("AIRPORT", "ITM");
		row.put("MASTERCITY", "OSAKA");

		row = iscitairpTable.add();
		row.put("CITY", "OSAKA         ");
		row.put("COUNTRY", "JP");
		row.put("AIRPORT", "KIX");
		row.put("MASTERCITY", "OSAKA");

		row = iscitairpTable.add();
		row.put("CITY", "OTTAWA        ");
		row.put("COUNTRY", "CA");
		row.put("AIRPORT", "YOW");
		row.put("MASTERCITY", "OTTAWA");

		row = iscitairpTable.add();
		row.put("CITY", "PARIS         ");
		row.put("COUNTRY", "FR");
		row.put("AIRPORT", "CDG");
		row.put("MASTERCITY", "PARIS");

		row = iscitairpTable.add();
		row.put("CITY", "PARIS         ");
		row.put("COUNTRY", "FR");
		row.put("AIRPORT", "ORY");
		row.put("MASTERCITY", "PARIS");

		row = iscitairpTable.add();
		row.put("CITY", "RIO DE JANEIRO");
		row.put("COUNTRY", "BR");
		row.put("AIRPORT", "GIG");
		row.put("MASTERCITY", "RIO DE JANEIRO");

		row = iscitairpTable.add();
		row.put("CITY", "ROME          ");
		row.put("COUNTRY", "IT");
		row.put("AIRPORT", "FCO");
		row.put("MASTERCITY", "ROME");

		row = iscitairpTable.add();
		row.put("CITY", "ROTTERDAM     ");
		row.put("COUNTRY", "NL");
		row.put("AIRPORT", "RTM");
		row.put("MASTERCITY", "ROTTERDAM");

		row = iscitairpTable.add();
		row.put("CITY", "SAN FRANCISCO ");
		row.put("COUNTRY", "US");
		row.put("AIRPORT", "SFO");
		row.put("MASTERCITY", "SAN FRANCISCO");

		row = iscitairpTable.add();
		row.put("CITY", "SEOUL         ");
		row.put("COUNTRY", "KP");
		row.put("AIRPORT", "SEL");
		row.put("MASTERCITY", "SEOUL");

		row = iscitairpTable.add();
		row.put("CITY", "SINGAPORE     ");
		row.put("COUNTRY", "SG");
		row.put("AIRPORT", "SIN");
		row.put("MASTERCITY", "SINGAPORE");

		row = iscitairpTable.add();
		row.put("CITY", "STOCKHOLM     ");
		row.put("COUNTRY", "SE");
		row.put("AIRPORT", "STO");
		row.put("MASTERCITY", "STOCKHOLM");

		row = iscitairpTable.add();
		row.put("CITY", "TOKYO         ");
		row.put("COUNTRY", "JP");
		row.put("AIRPORT", "NRT");
		row.put("MASTERCITY", "TOKYO");

		row = iscitairpTable.add();
		row.put("CITY", "TOKYO         ");
		row.put("COUNTRY", "JP");
		row.put("AIRPORT", "TYO");
		row.put("MASTERCITY", "TOKYO");

		row = iscitairpTable.add();
		row.put("CITY", "VENICE        ");
		row.put("COUNTRY", "IT");
		row.put("AIRPORT", "VCE");
		row.put("MASTERCITY", "VENICE");

		row = iscitairpTable.add();
		row.put("CITY", "VIENNA        ");
		row.put("COUNTRY", "AT");
		row.put("AIRPORT", "VIE");
		row.put("MASTERCITY", "VIENNA");

		row = iscitairpTable.add();
		row.put("CITY", "WALLDORF      ");
		row.put("COUNTRY", "DE");
		row.put("AIRPORT", "FRA");
		row.put("MASTERCITY", "FRANKFURT");

		row = iscitairpTable.add();
		row.put("CITY", "WALLDORF      ");
		row.put("COUNTRY", "DE");
		row.put("AIRPORT", "MUC");
		row.put("MASTERCITY", "MUNICH");

		
	}
	
	public void addGeocityData() {
		Structure row;

		isgeocityTable.clear();

		row = isgeocityTable.add();
		row.put("CITY", "ACAPULCO      ");
		row.put("COUNTRY", "MX");
		row.put("LATITUDE", latitude(52, 21, "N"));
		row.put("LONGITUDE", longitude(4, 52, "E"));

		row = isgeocityTable.add();
		row.put("CITY", "ALICE SPRINGS ");
		row.put("COUNTRY", "AU");
		row.put("LATITUDE", latitude(52, 21, "N"));
		row.put("LONGITUDE", longitude( 4, 52, "E"));   

		row = isgeocityTable.add();
		row.put("CITY", "AMSTERDAM     ");
		row.put("COUNTRY", "NL");
		row.put("LATITUDE", latitude(52, 21, "N"));
		row.put("LONGITUDE", longitude( 4, 52, "E"));

		row = isgeocityTable.add();
		row.put("CITY", "ANKARA        ");
		row.put("COUNTRY", "TR");
		row.put("LATITUDE", latitude(40, 01, "N"));
		row.put("LONGITUDE", longitude(32, 54, "E"));

		row = isgeocityTable.add();
		row.put("CITY", "ATHEN         ");
		row.put("COUNTRY", "GR");
		row.put("LATITUDE", latitude(38, 01, "N"));
		row.put("LONGITUDE", longitude(23, 44, "E"));

		row = isgeocityTable.add();
		row.put("CITY", "ATLANTA       ");
		row.put("COUNTRY", "US");
		row.put("LATITUDE", latitude(33, 44, "N"));
		row.put("LONGITUDE", longitude(84, 23, "W"));

		row = isgeocityTable.add();
		row.put("CITY", "ATLANTIC  ");
		row.put("COUNTRY", "US");
		row.put("LATITUDE", latitude(33, 44, "N"));
		row.put("LONGITUDE", longitude(84, 23, "W"));   

		row = isgeocityTable.add();
		row.put("CITY", "BANGKOK       ");
		row.put("COUNTRY", "TH");
		row.put("LATITUDE", latitude(13, 50, "N"));
		row.put("LONGITUDE", longitude(100, 29, "E"));

		row = isgeocityTable.add();
		row.put("CITY", "BARCELONA     ");
		row.put("COUNTRY", "ES");
		row.put("LATITUDE", latitude(41, 18, "N"));
		row.put("LONGITUDE", longitude( 2, 05, "E"));

		row = isgeocityTable.add();
		row.put("CITY", "BASEL         ");
		row.put("COUNTRY", "CH");
		row.put("LATITUDE", latitude(47, 40, "N"));
		row.put("LONGITUDE", longitude( 7, 30, "E"));

		row = isgeocityTable.add();
		row.put("CITY", "BERLIN        ");
		row.put("COUNTRY", "DE");
		row.put("LATITUDE", latitude(52, 31, "N"));
		row.put("LONGITUDE", longitude(13, 20, "E"));

		row = isgeocityTable.add();
		row.put("CITY", "BOMBAY        ");
		row.put("COUNTRY", "IN");
		row.put("LATITUDE", latitude(18, 58, "N"));
		row.put("LONGITUDE", longitude(72, 50, "E"));

		row = isgeocityTable.add();
		row.put("CITY", "BOSTON        ");
		row.put("COUNTRY", "US");
		row.put("LATITUDE", latitude(42, 21, "N"));
		row.put("LONGITUDE", longitude(71, 03, "W"));

		row = isgeocityTable.add();
		row.put("CITY", "BRUSSELS      ");
		row.put("COUNTRY", "BE");
		row.put("LATITUDE", latitude(50, 51, "N"));
		row.put("LONGITUDE", longitude( 4, 21, "E"));

		row = isgeocityTable.add();
		row.put("CITY", "BUDAPEST      ");
		row.put("COUNTRY", "HU");
		row.put("LATITUDE", latitude(47, 25, "N"));
		row.put("LONGITUDE", longitude(19, 15, "E"));

		row = isgeocityTable.add();
		row.put("CITY", "BUENOS AIRES  ");
		row.put("COUNTRY", "AR");
		row.put("LATITUDE", latitude(34, 20, "S"));
		row.put("LONGITUDE", longitude(58, 30, "W"));

		row = isgeocityTable.add();
		row.put("CITY", "CHICAGO       ");
		row.put("COUNTRY", "US");
		row.put("LATITUDE", latitude(41, 51, "N"));
		row.put("LONGITUDE", longitude(87, 39, "W"));

		row = isgeocityTable.add();
		row.put("CITY", "DENVER        ");
		row.put("COUNTRY", "US");
		row.put("LATITUDE", latitude(39, 44, "N"));
		row.put("LONGITUDE", longitude(104, 59, "W"));

		row = isgeocityTable.add();
		row.put("CITY", "DETROIT       ");
		row.put("COUNTRY", "US");
		row.put("LATITUDE", latitude(42, 19, "N"));
		row.put("LONGITUDE", longitude(83, 02, "W"));

		row = isgeocityTable.add();
		row.put("CITY", "EDMONTON      ");
		row.put("COUNTRY", "CA");
		row.put("LATITUDE", latitude(42, 19, "N"));
		row.put("LONGITUDE", longitude(83, 02, "W")); 

		row = isgeocityTable.add();
		row.put("CITY", "EL PASO       ");
		row.put("COUNTRY", "US");
		row.put("LATITUDE", latitude(42, 19, "N"));
		row.put("LONGITUDE", longitude(83, 02, "W")); 

		row = isgeocityTable.add();
		row.put("CITY", "FRANKFURT     ");
		row.put("COUNTRY", "DE");
		row.put("LATITUDE", latitude(50, 01, "N"));
		row.put("LONGITUDE", longitude( 8, 34, "E"));

		row = isgeocityTable.add();
		row.put("CITY", "HAMBURG       ");
		row.put("COUNTRY", "DE");
		row.put("LATITUDE", latitude(53, 34, "N"));
		row.put("LONGITUDE", longitude(10, 02, "E"));

		row = isgeocityTable.add();
		row.put("CITY", "HARARE        ");
		row.put("COUNTRY", "ZW");
		row.put("LATITUDE", latitude(53, 34, "N"));
		row.put("LONGITUDE", longitude(10, 02, "E")); 

		row = isgeocityTable.add();
		row.put("CITY", "HIROSHIMA     ");
		row.put("COUNTRY", "JP");
		row.put("LATITUDE", latitude(34, 30, "N"));
		row.put("LONGITUDE", longitude(132, 30, "E"));

		row = isgeocityTable.add();
		row.put("CITY", "HONGKONG      ");
		row.put("COUNTRY", "HK");
		row.put("LATITUDE", latitude(20, 20, "N"));
		row.put("LONGITUDE", longitude(110, 20, "E"));

		row = isgeocityTable.add();
		row.put("CITY", "HOUSTON       ");
		row.put("COUNTRY", "US");
		row.put("LATITUDE", latitude(20, 20, "N"));
		row.put("LONGITUDE", longitude(110, 20, "E")); 

		row = isgeocityTable.add();
		row.put("CITY", "JAKARTA       ");
		row.put("COUNTRY", "ID");
		row.put("LATITUDE", latitude(26, 8, "S"));
		row.put("LONGITUDE", longitude(27, 54, "E")); 

		row = isgeocityTable.add();
		row.put("CITY", "JOHANNESBURG  ");
		row.put("COUNTRY", "ZA");
		row.put("LATITUDE", latitude(26, 8, "S"));
		row.put("LONGITUDE", longitude(27, 54, "E"));

		row = isgeocityTable.add();
		row.put("CITY", "KANSAS    ");
		row.put("COUNTRY", "US");
		row.put("LATITUDE", latitude(55, 43, "N"));
		row.put("LONGITUDE", longitude(12, 27, "E")); 

		row = isgeocityTable.add();
		row.put("CITY", "KUALA LUMPUR  ");
		row.put("COUNTRY", "MY");
		row.put("LATITUDE", latitude(55, 43, "N"));
		row.put("LONGITUDE", longitude(12, 27, "E")); 

		row = isgeocityTable.add();
		row.put("CITY", "KOPENHABEN    ");
		row.put("COUNTRY", "DK");
		row.put("LATITUDE", latitude(55, 43, "N"));
		row.put("LONGITUDE", longitude(12, 27, "E"));

		row = isgeocityTable.add();
		row.put("CITY", "LANZAROTE     ");
		row.put("COUNTRY", "ES");
		row.put("LATITUDE", latitude(51, 30, "N"));
		row.put("LONGITUDE", longitude( 0, 07, "W"));  

		row = isgeocityTable.add();
		row.put("CITY", "LAS VEGAS     ");
		row.put("COUNTRY", "US");
		row.put("LATITUDE", latitude(51, 30, "N"));
		row.put("LONGITUDE", longitude( 0, 07, "W"));  

		row = isgeocityTable.add();
		row.put("CITY", "LONDON        ");
		row.put("COUNTRY", "GB");
		row.put("LATITUDE", latitude(51, 30, "N"));
		row.put("LONGITUDE", longitude( 0, 07, "W"));

		row = isgeocityTable.add();
		row.put("CITY", "LOS ANGELES   ");
		row.put("COUNTRY", "US");
		row.put("LATITUDE", latitude(51, 30, "N"));
		row.put("LONGITUDE", longitude( 0, 07, "W")); 

		row = isgeocityTable.add();
		row.put("CITY", "MADRID        ");
		row.put("COUNTRY", "ES");
		row.put("LATITUDE", latitude(40, 26, "N"));
		row.put("LONGITUDE", longitude( 3, 42, "W"));

		row = isgeocityTable.add();
		row.put("CITY", "MIAMI         ");
		row.put("COUNTRY", "US");
		row.put("LATITUDE", latitude(40, 26, "N"));
		row.put("LONGITUDE", longitude( 3, 42, "W")); 

		row = isgeocityTable.add();
		row.put("CITY", "MELBOURNE     ");
		row.put("COUNTRY", "AU");
		row.put("LATITUDE", latitude(37, 52, "S"));
		row.put("LONGITUDE", longitude(145, 8, "E"));

		row = isgeocityTable.add();
		row.put("CITY", "MOSCOW        ");
		row.put("COUNTRY", "RU");
		row.put("LATITUDE", latitude(55, 45, "N"));
		row.put("LONGITUDE", longitude(37, 37, "E"));

		row = isgeocityTable.add();
		row.put("CITY", "MUNICH        ");
		row.put("COUNTRY", "DE");
		row.put("LATITUDE", latitude(48, 8, "N"));
		row.put("LONGITUDE", longitude(11, 35, "E"));

		row = isgeocityTable.add();
		row.put("CITY", "NASHVILLE     ");
		row.put("COUNTRY", "US");
		row.put("LATITUDE", latitude(40, 42, "N"));
		row.put("LONGITUDE", longitude(74, 00, "W")); 

		row = isgeocityTable.add();
		row.put("CITY", "NASSAU        ");
		row.put("COUNTRY", "BS");
		row.put("LATITUDE", latitude(40, 42, "N"));
		row.put("LONGITUDE", longitude(74, 00, "W")); 

		row = isgeocityTable.add();
		row.put("CITY", "NEW YORK      ");
		row.put("COUNTRY", "US");
		row.put("LATITUDE", latitude(40, 42, "N"));
		row.put("LONGITUDE", longitude(74, 00, "W"));

		row = isgeocityTable.add();
		row.put("CITY", "OSAKA         ");
		row.put("COUNTRY", "JP");
		row.put("LATITUDE", latitude(34, 46, "N"));
		row.put("LONGITUDE", longitude(135, 27, "E"));

		row = isgeocityTable.add();
		row.put("CITY", "OSLO          ");
		row.put("COUNTRY", "NO");
		row.put("LATITUDE", latitude(59, 56, "N"));
		row.put("LONGITUDE", longitude(10, 41, "E"));

		row = isgeocityTable.add();
		row.put("CITY", "OTTAWA        ");
		row.put("COUNTRY", "CA");
		row.put("LATITUDE", latitude(59, 56, "N"));
		row.put("LONGITUDE", longitude(10, 41, "E")); 

		row = isgeocityTable.add();
		row.put("CITY", "PARIS         ");
		row.put("COUNTRY", "FR");
		row.put("LATITUDE", latitude(48, 51, "N"));
		row.put("LONGITUDE", longitude( 2, 20, "E"));

		row = isgeocityTable.add();
		row.put("CITY", "PEKING        ");
		row.put("COUNTRY", "CN");
		row.put("LATITUDE", latitude(39, 55, "N"));
		row.put("LONGITUDE", longitude(116, 23, "E"));

		row = isgeocityTable.add();
		row.put("CITY", "RIO DE JANEIRO");
		row.put("COUNTRY", "BR");
		row.put("LATITUDE", latitude(22, 27, "S"));
		row.put("LONGITUDE", longitude(42, 43, "W"));

		row = isgeocityTable.add();
		row.put("CITY", "ROME          ");
		row.put("COUNTRY", "IT");
		row.put("LATITUDE", latitude(41, 52, "N"));
		row.put("LONGITUDE", longitude(12, 37, "E"));

		row = isgeocityTable.add();
		row.put("CITY", "ROTTERDAM     ");
		row.put("COUNTRY", "NL");
		row.put("LATITUDE", latitude(51, 50, "N"));
		row.put("LONGITUDE", longitude(4, 30, "E"));

		row = isgeocityTable.add();
		row.put("CITY", "SAN FRANCISCO ");
		row.put("COUNTRY", "US");
		row.put("LATITUDE", latitude(37, 46, "N"));
		row.put("LONGITUDE", longitude(122, 25, "W"));

		row = isgeocityTable.add();
		row.put("CITY", "SEOUL         ");
		row.put("COUNTRY", "KP");
		row.put("LATITUDE", latitude(37, 46, "N"));
		row.put("LONGITUDE", longitude(122, 25, "W")); 

		row = isgeocityTable.add();
		row.put("CITY", "SYDNEY        ");
		row.put("COUNTRY", "AU");
		row.put("LATITUDE", latitude(33, 55, "S"));
		row.put("LONGITUDE", longitude(151, 17, "E"));

		row = isgeocityTable.add();
		row.put("CITY", "SINGAPORE     ");
		row.put("COUNTRY", "SG");
		row.put("LATITUDE", latitude( 1, 22, "N"));
		row.put("LONGITUDE", longitude(103, 45, "E"));

		row = isgeocityTable.add();
		row.put("CITY", "STOCKHOLM     ");
		row.put("COUNTRY", "SE");
		row.put("LATITUDE", latitude(59, 23, "N"));
		row.put("LONGITUDE", longitude(18, 00, "E"));

		row = isgeocityTable.add();
		row.put("CITY", "TOKYO         ");
		row.put("COUNTRY", "JP");
		row.put("LATITUDE", latitude(35, 41, "N"));
		row.put("LONGITUDE", longitude(139, 44, "E"));

		row = isgeocityTable.add();
		row.put("CITY", "VENICE        ");
		row.put("COUNTRY", "IT");
		row.put("LATITUDE", latitude(48, 13, "N"));
		row.put("LONGITUDE", longitude(16, 22, "E")); 

		row = isgeocityTable.add();
		row.put("CITY", "VIENNA        ");
		row.put("COUNTRY", "AT");
		row.put("LATITUDE", latitude(48, 13, "N"));
		row.put("LONGITUDE", longitude(16, 22, "E"));

		row = isgeocityTable.add();
		row.put("CITY", "WALLDORF      ");
		row.put("COUNTRY", "DE");
		row.put("LATITUDE", latitude(49, 15, "N"));
		row.put("LONGITUDE", longitude( 8, 40, "E"));

		row = isgeocityTable.add();
		row.put("CITY", "ZURICH        ");
		row.put("COUNTRY", "CH");
		row.put("LATITUDE", latitude(47, 30, "N"));
		row.put("LONGITUDE", longitude( 8, 35, "E"));
		
	}

	public void addAirportData() {
		Structure row;

		isairportTable.clear();

		row = isairportTable.add();
		row.put("ID", "FRA");
		row.put("NAME", "Frankfurt/Main, FRG");
		row.put("TIME_ZONE", "UTC+1");

		row = isairportTable.add();
		row.put("ID", "HAM");
		row.put("NAME", "Hamburg, FRG             ");
		row.put("TIME_ZONE", "UTC+1");

		row = isairportTable.add();
		row.put("ID", "MUC");
		row.put("NAME", "Munich, FRG              ");
		row.put("TIME_ZONE", "UTC+1");

		row = isairportTable.add();
		row.put("ID", "SXF");
		row.put("NAME", "Berlin Schonefeld Apt,FRG");
		row.put("TIME_ZONE", "UTC+1");

		row = isairportTable.add();
		row.put("ID", "THF");
		row.put("NAME", "Berlin Tempelhof Apt, FRG");
		row.put("TIME_ZONE", "UTC+1");

		row = isairportTable.add();
		row.put("ID", "TXL");
		row.put("NAME", "Berlin Tegel Apt, FRG    ");
		row.put("TIME_ZONE", "UTC+1");

		row = isairportTable.add();
		row.put("ID", "CDG");
		row.put("NAME", "Paris Charles de Gaulle,F");
		row.put("TIME_ZONE", "UTC+1");

		row = isairportTable.add();
		row.put("ID", "ORY");
		row.put("NAME", "Paris Orly Apt, France   ");
		row.put("TIME_ZONE", "UTC+1");

		row = isairportTable.add();
		row.put("ID", "VIE");
		row.put("NAME", "Vienna, Austria          ");
		row.put("TIME_ZONE", "UTC+1");

		row = isairportTable.add();
		row.put("ID", "ZRH");
		row.put("NAME", "Zurich, Switzerland      ");
		row.put("TIME_ZONE", "UTC+1");

		row = isairportTable.add();
		row.put("ID", "RTM");
		row.put("NAME", "Rotterdam Apt, NL        ");
		row.put("TIME_ZONE", "UTC+1");

		row = isairportTable.add();
		row.put("ID", "FCO");
		row.put("NAME", "Rome Leonardo Da Vinci, I");
		row.put("TIME_ZONE", "UTC+1");

		row = isairportTable.add();
		row.put("ID", "VCE");
		row.put("NAME", "Venice Marco Polo Apt, I ");
		row.put("TIME_ZONE", "UTC+1");

		row = isairportTable.add();
		row.put("ID", "LCY");
		row.put("NAME", "London City Apt, UK      ");
		row.put("TIME_ZONE", "UTC");

		row = isairportTable.add();
		row.put("ID", "LGW");
		row.put("NAME", "London Gatwick Apt, UK   ");
		row.put("TIME_ZONE", "UTC");

		row = isairportTable.add();
		row.put("ID", "LHR");
		row.put("NAME", "London Heathrow Apt, UK  ");
		row.put("TIME_ZONE", "UTC");

		row = isairportTable.add();
		row.put("ID", "MAD");
		row.put("NAME", "Madrid Barajas Apt, Spain");
		row.put("TIME_ZONE", "UTC+1");

		row = isairportTable.add();
		row.put("ID", "STO");
		row.put("NAME", "Stockholm, Sweden        ");
		row.put("TIME_ZONE", "UTC+1");

		row = isairportTable.add();
		row.put("ID", "VKO");
		row.put("NAME", "Moscow Vnukovo Apt, R    ");
		row.put("TIME_ZONE", "UTC+3");

		row = isairportTable.add();
		row.put("ID", "SVO");
		row.put("NAME", "Moscow Sheremetyevo Apt,R");
		row.put("TIME_ZONE", "UTC+3");

		row = isairportTable.add();
		row.put("ID", "JFK");
		row.put("NAME", "New York JF Kennedy, USA ");
		row.put("TIME_ZONE", "UTC-5");

		row = isairportTable.add();
		row.put("ID", "AIY");
		row.put("NAME", "Atlantic City, USA       ");
		row.put("TIME_ZONE", "UTC-5");

		row = isairportTable.add();
		row.put("ID", "BNA");
		row.put("NAME", "Nashville, USA           ");
		row.put("TIME_ZONE", "UTC-5");

		row = isairportTable.add();
		row.put("ID", "BOS");
		row.put("NAME", "Boston Logan Int, USA    ");
		row.put("TIME_ZONE", "UTC-5");

		row = isairportTable.add();
		row.put("ID", "ELP");
		row.put("NAME", "El Paso Int., USA        ");
		row.put("TIME_ZONE", "UTC-7");

		row = isairportTable.add();
		row.put("ID", "DEN");
		row.put("NAME", "Denver Int., USA         ");
		row.put("TIME_ZONE", "UTC-7");

		row = isairportTable.add();
		row.put("ID", "HOU");
		row.put("NAME", "Houston Hobby Apt, USA   ");
		row.put("TIME_ZONE", "UTC-6");

		row = isairportTable.add();
		row.put("ID", "LAS");
		row.put("NAME", "Las Vegas Mc Carran, USA ");
		row.put("TIME_ZONE", "UTC-8");

		row = isairportTable.add();
		row.put("ID", "LAX");
		row.put("NAME", "Los Angeles Int Apt, USA ");
		row.put("TIME_ZONE", "UTC-8");

		row = isairportTable.add();
		row.put("ID", "MCI");
		row.put("NAME", "Kansas City Int Apt, USA ");
		row.put("TIME_ZONE", "UTC-6");

		row = isairportTable.add();
		row.put("ID", "MIA");
		row.put("NAME", "Miami Int Apt, USA       ");
		row.put("TIME_ZONE", "UTC-5");

		row = isairportTable.add();
		row.put("ID", "SFO");
		row.put("NAME", "San Francisco Int Apt,USA");
		row.put("TIME_ZONE", "UTC-8");

		row = isairportTable.add();
		row.put("ID", "PID");
		row.put("NAME", "Nassau Paradise IS,Bahama");
		row.put("TIME_ZONE", "UTC-5");

		row = isairportTable.add();
		row.put("ID", "EWR");
		row.put("NAME", "New York Newark Int., USA");
		row.put("TIME_ZONE", "UTC-5");

		row = isairportTable.add();
		row.put("ID", "YEG");
		row.put("NAME", "Edmonton Int Apt, CDN    ");
		row.put("TIME_ZONE", "UTC-7");

		row = isairportTable.add();
		row.put("ID", "YOW");
		row.put("NAME", "Ottawa Uplands Int., CDN ");
		row.put("TIME_ZONE", "UTC-5");

		row = isairportTable.add();
		row.put("ID", "ACA");
		row.put("NAME", "Acapulco, Mexico         ");
		row.put("TIME_ZONE", "UTC-6");

		row = isairportTable.add();
		row.put("ID", "GIG");
		row.put("NAME", "Rio De Janeiro Int., BRA ");
		row.put("TIME_ZONE", "UTC-3");

		row = isairportTable.add();
		row.put("ID", "ASP");
		row.put("NAME", "Alice Springs, AUS       ");
		row.put("TIME_ZONE", "UTC+9");

		row = isairportTable.add();
		row.put("ID", "ACE");
		row.put("NAME", "Lanzarote, Canary IS     ");
		row.put("TIME_ZONE", "UTC");

		row = isairportTable.add();
		row.put("ID", "HRE");
		row.put("NAME", "Harare, Zimbabwe         ");
		row.put("TIME_ZONE", "UTC+2");

		row = isairportTable.add();
		row.put("ID", "GCJ");
		row.put("NAME", "Johannesburg Grand C., SA");
		row.put("TIME_ZONE", "UTC+2");

		row = isairportTable.add();
		row.put("ID", "SEL");
		row.put("NAME", "Seoul Kimpo Int, ROK     ");
		row.put("TIME_ZONE", "UTC+9");

		row = isairportTable.add();
		row.put("ID", "TYO");
		row.put("NAME", "Tokyo, JAPAN             ");
		row.put("TIME_ZONE", "UTC+9");

		row = isairportTable.add();
		row.put("ID", "NRT");
		row.put("NAME", "Tokyo Narita, Japan      ");
		row.put("TIME_ZONE", "UTC+9");

		row = isairportTable.add();
		row.put("ID", "ITM");
		row.put("NAME", "Osaka Itami Apt, Japan   ");
		row.put("TIME_ZONE", "UTC+9");

		row = isairportTable.add();
		row.put("ID", "KIX");
		row.put("NAME", "Osaka Kansai Int., Japan ");
		row.put("TIME_ZONE", "UTC+9");

		row = isairportTable.add();
		row.put("ID", "HIJ");
		row.put("NAME", "Hiroshima, Japan         ");
		row.put("TIME_ZONE", "UTC+9");

		row = isairportTable.add();
		row.put("ID", "JKT");
		row.put("NAME", "Jakarta, Indonesia       ");
		row.put("TIME_ZONE", "UTC+7");

		row = isairportTable.add();
		row.put("ID", "SIN");
		row.put("NAME", "Singapore                ");
		row.put("TIME_ZONE", "UTC+8");

		row = isairportTable.add();
		row.put("ID", "KUL");
		row.put("NAME", "Kuala Lumpur, Malaysia   ");
		row.put("TIME_ZONE", "UTC+8");

		row = isairportTable.add();
		row.put("ID", "HKG");
		row.put("NAME", "Hongkong                 ");
		row.put("TIME_ZONE", "UTC+8");

		row = isairportTable.add();
		row.put("ID", "BKK");
		row.put("NAME", "Bangkok, Thailand        ");
		row.put("TIME_ZONE", "UTC+7");

	}

	public void addCurrencyData() {
		Structure row;

		iscurxTable.clear();

		row = iscurxTable.add();
		row.put("CURRKEY", "EUR");
		row.put("CURRDEC", 2);

		row = iscurxTable.add();
		row.put("CURRKEY", "CHF");
		row.put("CURRDEC", 2);

		row = iscurxTable.add();
		row.put("CURRKEY", "GBP");
		row.put("CURRDEC", 2);

		row = iscurxTable.add();
		row.put("CURRKEY", "SEK");
		row.put("CURRDEC", 2);

		row = iscurxTable.add();
		row.put("CURRKEY", "USD");
		row.put("CURRDEC", 2);

		row = iscurxTable.add();
		row.put("CURRKEY", "CAD");
		row.put("CURRDEC", 2);

		row = iscurxTable.add();
		row.put("CURRKEY", "JPY");
		row.put("CURRDEC", 0);

		row = iscurxTable.add();
		row.put("CURRKEY", "SGD");
		row.put("CURRDEC", 2);

		row = iscurxTable.add();
		row.put("CURRKEY", "AUD");
		row.put("CURRDEC", 2);

		row = iscurxTable.add();
		row.put("CURRKEY", "ZAR");
		row.put("CURRDEC", 2);

	}

	public void addCurrencyConversionData() {
		Structure row;

		iscurrTable.clear();
		Table<Structure> hscurrTable = EcoreUtil.copy(iscurrTable);

		row = hscurrTable.add();
		row.put("GDATU", ISCURR_GDATU);
		row.put("KURST", ISCURR_KURST);
		row.put("TCURR", ISCURR_TCURR);
		row.put("FCURR", "EUR");
		row.put("UKURS", new BigDecimal(1.0));
		row.put("FFACT", new BigDecimal(1));
		row.put("TFACT", new BigDecimal(1));

		row = hscurrTable.add();
		row.put("GDATU", ISCURR_GDATU);
		row.put("KURST", ISCURR_KURST);
		row.put("TCURR", ISCURR_TCURR);
		row.put("FCURR", "CHF");
		row.put("UKURS", new BigDecimal(0.6725));
		row.put("FFACT", new BigDecimal(1));
		row.put("TFACT", new BigDecimal(1));

		row = hscurrTable.add();
		row.put("GDATU", ISCURR_GDATU);
		row.put("KURST", ISCURR_KURST);
		row.put("TCURR", ISCURR_TCURR);
		row.put("FCURR", "GBP");
		row.put("UKURS", new BigDecimal(1.612));
		row.put("FFACT", new BigDecimal(1));
		row.put("TFACT", new BigDecimal(1));

		row = hscurrTable.add();
		row.put("GDATU", ISCURR_GDATU);
		row.put("KURST", ISCURR_KURST);
		row.put("TCURR", ISCURR_TCURR);
		row.put("FCURR", "SEK");
		row.put("UKURS", new BigDecimal(0.1038));
		row.put("FFACT", new BigDecimal(1));
		row.put("TFACT", new BigDecimal(1));

		row = hscurrTable.add();
		row.put("GDATU", ISCURR_GDATU);
		row.put("KURST", ISCURR_KURST);
		row.put("TCURR", ISCURR_TCURR);
		row.put("FCURR", "USD");
		row.put("UKURS", new BigDecimal(1.0900));
		row.put("FFACT", new BigDecimal(1));
		row.put("TFACT", new BigDecimal(1));

		row = hscurrTable.add();
		row.put("GDATU", ISCURR_GDATU);
		row.put("KURST", ISCURR_KURST);
		row.put("TCURR", ISCURR_TCURR);
		row.put("FCURR", "CAD");
		row.put("UKURS", new BigDecimal(0.6951));
		row.put("FFACT", new BigDecimal(1));
		row.put("TFACT", new BigDecimal(1));

		row = hscurrTable.add();
		row.put("GDATU", ISCURR_GDATU);
		row.put("KURST", ISCURR_KURST);
		row.put("TCURR", ISCURR_TCURR);
		row.put("FCURR", "JPY");
		row.put("UKURS", new BigDecimal(0.9045));
		row.put("FFACT", new BigDecimal(100));
		row.put("TFACT", new BigDecimal(1));

		row = hscurrTable.add();
		row.put("GDATU", ISCURR_GDATU);
		row.put("KURST", ISCURR_KURST);
		row.put("TCURR", ISCURR_TCURR);
		row.put("FCURR", "SGD");
		row.put("UKURS", new BigDecimal(0.6043));
		row.put("FFACT", new BigDecimal(1));
		row.put("TFACT", new BigDecimal(1));

		row = hscurrTable.add();
		row.put("GDATU", ISCURR_GDATU);
		row.put("KURST", ISCURR_KURST);
		row.put("TCURR", ISCURR_TCURR);
		row.put("FCURR", "AUD");
		row.put("UKURS", new BigDecimal(1.3314));
		row.put("FFACT", new BigDecimal(1));
		row.put("TFACT", new BigDecimal(1));

		row = hscurrTable.add();
		row.put("GDATU", ISCURR_GDATU);
		row.put("KURST", ISCURR_KURST);
		row.put("TCURR", ISCURR_TCURR);
		row.put("FCURR", "ZAR");
		row.put("UKURS", new BigDecimal(0.1174));
		row.put("FFACT", new BigDecimal(1));
		row.put("TFACT", new BigDecimal(1));

		Table<Structure> hcurrTable1 = EcoreUtil.copy(iscurxTable);
		Table<Structure> hcurrTable2 = EcoreUtil.copy(iscurxTable);

		for (Structure hcurr1Row : hcurrTable1.getRows()) {
			Structure hscurrRow = getCurrencyByFromCurrencyKey(hscurrTable, hcurr1Row.get("CURRKEY", String.class));
			for (Structure hcurr2Row : hcurrTable2.getRows()) {
				Structure iscurrRow = iscurrTable.add();
				iscurrRow.put("GDATU", hscurrRow.get("GDATU"));
				iscurrRow.put("KURST", ISCURR_KURST);

				iscurrRow.put("FCURR", hcurr1Row.get("CURRKEY"));
				iscurrRow.put("FFACT", hscurrRow.get("FFACT"));
				BigDecimal help_UKURS = hscurrRow.get("UKURS", BigDecimal.class);

				Structure hscurrRow2 = getCurrencyByFromCurrencyKey(hscurrTable, hcurr2Row.get("CURRKEY", String.class));
				iscurrRow.put("TCURR", hcurr2Row.get("CURRKEY"));
				iscurrRow.put("TFACT", hscurrRow2.get("FFACT"));

				BigDecimal hscurrRows2_UKURS = hscurrRow2.get("UKURS", BigDecimal.class);
				iscurrRow.put("UKURS", help_UKURS.divide(hscurrRows2_UKURS, 5, RoundingMode.HALF_UP));
			}
		}
	}

	private Structure getCurrencyByFromCurrencyKey(Table<Structure> hscurrTable, String fcurr) {
		for (Structure scurrRow : hscurrTable) {
			if (scurrRow.get("FCURR").equals(fcurr)) {
				return scurrRow;
			}
		}
		throw new RuntimeException("Couldn't find currency " + fcurr + "'");
	}

	private double longitude(double degrees, double minutes, String orientation) {
		double longitude = degrees + minutes / 60.0;
		if (orientation.equals("E")) {
			longitude = -longitude;
		}
		return longitude;
	}

	private double latitude(double degrees, double minutes, String orientation) {
		double latitude = degrees + minutes / 60.0;
		if (orientation.equals("S")) {
			latitude = -latitude;
		}
		return latitude;
	}

}
