package edu.uiowa.icts.hector;

import java.util.HashMap;
import java.util.List;

import me.prettyprint.cassandra.model.CqlQuery;
import me.prettyprint.cassandra.model.CqlRows;
import me.prettyprint.cassandra.model.IndexedSlicesQuery;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.template.ColumnFamilyResult;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.cassandra.service.template.ThriftColumnFamilyTemplate;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.OrderedRows;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.QueryResult;

import org.apache.log4j.Logger;

public class Client {

	
    private static final Logger LOG = Logger.getLogger(Client.class);

	private Cluster myCluster = HFactory.getOrCreateCluster("ICTS Cluster","localhost:9160");
	
	private final String  keyspaceName = "MEDLINE";
	private final String  columnFamily = "MedlineCitation";
	ColumnFamilyTemplate<String, String> template;
	
	private Keyspace ksp; 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Client c = new Client();
		String key = c.run1("Attitude");
		if (key != null)
			LOG.info("Got Key: " + key);
	}

	public Client() {

		ksp = HFactory.createKeyspace(keyspaceName, myCluster);

		
		template = new ThriftColumnFamilyTemplate<String, String>
							(ksp, columnFamily, StringSerializer.get(), StringSerializer.get());
	}
	
	
	public Client(String keyspaceName, String cassandraUrl, String clusterName) {

		Cluster myCluster = HFactory.getOrCreateCluster(clusterName,cassandraUrl);
		ksp = HFactory.createKeyspace(keyspaceName, myCluster);

		
		template = new ThriftColumnFamilyTemplate<String, String>
							(ksp, columnFamily, StringSerializer.get(), StringSerializer.get());
	}
	
	public ColumnFamilyTemplate<String, String> getTemplate() {
		return template;
	}
	
	public Keyspace getKeyspace() {
		return ksp;
	}
	
	
	private void run() {
		try {
		    ColumnFamilyResult<String, String> res = template.queryColumns("8110285");
		    String value = res.getString("RAW_XML");
		    LOG.info("Value: " + value);
		    // value should be "www.datastax.com" as per our previous insertion.
		} catch (HectorException e) {
		    // do something ...
		}
	}
	
	private String run1(String term) {
		CqlQuery<String,String,String> cqlQuery = new CqlQuery<String,String,String>(ksp, StringSerializer.get(), StringSerializer.get(), StringSerializer.get());
		cqlQuery.setQuery("SELECT * FROM Mesh WHERE term = '" + term + "' limit 1");
	
		QueryResult<CqlRows<String,String,String>> result = cqlQuery.execute();
		CqlRows<String, String, String> rowList = result.get();
		if (rowList != null) {
			List<Row<String, String, String>> rowList1 = rowList.getList();
			for (Row<String, String, String> row1 : rowList1) {
				return row1.getKey();
			}
		}
		return null;
	}
	
	
	private void run2() {
		String t1 = "Attitude";
		IndexedSlicesQuery<String, String, Long> indexedSlicesQuery =
				HFactory.createIndexedSlicesQuery(ksp, StringSerializer.get(), StringSerializer.get(), LongSerializer.get());
				indexedSlicesQuery.addEqualsExpression("exits", 1L);
				
				indexedSlicesQuery.setRange("", "", false, 10);
				//indexedSlicesQuery.setColumnNames("RAW_XML","articleTitle");
				indexedSlicesQuery.setColumnFamily("Mesh");
				
		QueryResult<OrderedRows<String, String, Long>> result = indexedSlicesQuery.execute();
		
		OrderedRows<String, String, Long> cs = result.get();
		List<Row<String, String, Long>> cl = cs.getList();
		
		LOG.debug("Starting Loop: " + cl.size());
		
		for (Row<String, String, Long> c : cl) {
			String pmid = c.getKey();
			ColumnSlice<String, Long> colSlice = c.getColumnSlice();
			 List<HColumn<String, Long>> cols = colSlice.getColumns();
			 for (HColumn<String, Long> col : cols ) {
				 LOG.debug("name: " + col.getName() + " value: " + col.getValue());
			
			 }
			
		}
		LOG.debug("Done Loop");
	}
	
}
