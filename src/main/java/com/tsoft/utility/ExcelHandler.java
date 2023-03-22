package com.tsoft.utility;

import java.util.*;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class ExcelHandler 
{
	public List<Map<String, String>> getTestData(String file, String sheet)
	{
		List<Map<String, String>> mydata = new ArrayList<>();
		//Map<String,String> TestDataInMap = new TreeMap<String,String>();
		String query = null;
		query = String.format("SELECT * FROM %s ", sheet);

		Fillo fillo = new Fillo();
		Connection conn = null;
		Recordset recordset = null;

		try {
			String ruta = System.getProperty("user.dir") + "/src/main/resources/" + file;
		    conn = fillo.getConnection(ruta);
			recordset = conn.executeQuery(query);

			while(recordset.next()) {
				Map<String,String> TestDataInMap = new TreeMap<String,String>();
				for(String field:recordset.getFieldNames()) {
					TestDataInMap.put(field, recordset.getField(field));
				}
				mydata.add(TestDataInMap);
			}
		} catch (FilloException e) {}
		conn.close();
		//return TestDataInMap;
		return mydata;
	}
	
	public void setTestData(String file, String sheet, String colum, String value, String id)
	{
		Connection conn = null;
		Fillo fillo = new Fillo();
		try{
			String ruta = System.getProperty("user.dir") + "/src/main/resources/" + file;
			conn = fillo.getConnection(ruta);
			String query = String.format("UPDATE %s SET `%s`='%s' WHERE Id='%s'", sheet, colum, value, id);
			conn.executeUpdate(query);
		} catch(FilloException e){
			e.printStackTrace();
		}		
	}

}
