package org.jboss.fuse.sap.flightbookingdemo;

import org.fusesource.camel.component.sap.model.rfc.DestinationData;
import org.fusesource.camel.component.sap.model.rfc.DestinationDataStore;
import org.fusesource.camel.component.sap.model.rfc.Request;
import org.fusesource.camel.component.sap.model.rfc.RfcFactory;
import org.fusesource.camel.component.sap.model.rfc.Structure;
import org.fusesource.camel.component.sap.model.rfc.Table;
import org.fusesource.camel.component.sap.util.ComponentDestinationDataProvider;
import org.fusesource.camel.component.sap.util.RfcUtil;
import org.jboss.fuse.sap.flightbookingdemo.processor.CreateZLoadFlightDataModelDataRequest;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoRepository;

public class TestCreateZLoadFlightDataModelDataRequest {

	@SuppressWarnings("unchecked")
//	@Test
	public void testAddCurrencyConversionData() throws Exception {
		DestinationData destinationData = RfcFactory.eINSTANCE.createDestinationData();
		destinationData.setAshost("nplhost");
		destinationData.setSysnr("42");
		destinationData.setClient("001");
		destinationData.setUser("developer");
		destinationData.setPasswd("ch4ngeme");
		destinationData.setLang("en");

		DestinationDataStore destinationDataStore = RfcFactory.eINSTANCE.createDestinationDataStore();
		destinationDataStore.getEntries().put("quickstartDest", destinationData);

		ComponentDestinationDataProvider.INSTANCE.addDestinationDataStore(destinationDataStore);

		JCoDestination jcoDestination = JCoDestinationManager.getDestination("quickstartDest");
		JCoRepository repository = jcoDestination.getRepository();
		
		Request request = RfcUtil.getRequest(repository, "ZLOAD_FLIGHT_DATA_MODEL_DATA");
		
		CreateZLoadFlightDataModelDataRequest loadRequest = new CreateZLoadFlightDataModelDataRequest();
		loadRequest.iscurxTable = request.get("TP_SCURX", Table.class);
		loadRequest.iscurrTable = request.get("TP_SCURR", Table.class);
		
		loadRequest.addCurrencyData();
		loadRequest.addCurrencyConversionData();
		
		
		Table<Structure> iscurrTable = loadRequest.iscurrTable;
		for (Structure row: iscurrTable) {
			for(String key: row.keySet()) {
				System.out.print(key + " = " + row.get(key) + ",\t\t");
			}
			System.out.println();
		}
	}

}
